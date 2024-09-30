package com.xiaolang233.overpowermod.block.entity;

import com.xiaolang233.overpowermod.item.ModItems;
import com.xiaolang233.overpowermod.recipe.PolishingMachineRecipe;
import com.xiaolang233.overpowermod.screen.PolishingMachineScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PolishingMachineBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    //创建两个格子
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    //进度 tick
    private int progress = 0;
    private int maxProgress = 36;
    public int count;

    public PolishingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POLISHING_MACHINE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> PolishingMachineBlockEntity.this.progress;
                    case 1 -> PolishingMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PolishingMachineBlockEntity.this.progress = value;
                    case 1 -> PolishingMachineBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;  //几个参数
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    //服务器端客户端同步
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    //gui名字
    @Override
    public Text getDisplayName() {
        return Text.literal("Polishing Machine");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PolishingMachineScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    //nbt标签保存,重进游戏作用
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("polishing_machine", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("polishing_machine");
    }



    /**
     * 处理炉子的运行逻辑
     * 包括检查客户端或服务器、输出槽位状态、配方存在性，并根据情况更新制作进度或重置进度
     *
     * @param world1 世界对象，用于检查是否是客户端和标记方块状态变化
     * @param pos 方块位置，用于标记方块状态变化
     * @param state1 方块状态，用于检查和更新方块状态
     */
    public void tick(World world1, BlockPos pos, BlockState state1) {
        // 如果是客户端，则直接返回，不执行后续逻辑
        if (world1.isClient()) {
            return;
        }

        // 检查输出槽位是否可用，若不可用则重置制作进度
        if (isOutputSlotAvailable()) {
            if (this.hasRecipe()) {
                if (this.hasCount()){
                    // 检查是否有有效配方，如果有，则增加制作进度
                    this.increaseCraftProgress();
                    markDirty(world1, pos, state1);

                    // 检查制作是否完成，如果完成，则执行制作并重置制作进度
                    if (hasCraftingFinished()) {
                        this.craftItem();
                        this.resetProgress();
                    }

                }

            } else {
                // 如果没有有效配方，则重置制作进度
                this.resetProgress();
            }
        } else {
            // 如果输出槽位不可用，则重置制作进度并标记方块状态变化
            this.resetProgress();
            markDirty(world1, pos, state1);
        }
    }

    //检测当前配方是否有足够数量
    private boolean hasCount(){
        Optional<PolishingMachineRecipe> recipe = getCurrentRecipe();
        return recipe.map(r -> r.getIngredients().stream()
                                .allMatch(ingredient -> ingredient.test(getStack(INPUT_SLOT))))
                     .orElse(false);
    }
    /**
     * 重置进度
     *
     * 此方法将当前进度重置为0，用于在某些操作开始前初始化进度值
     */
    private void resetProgress() {
        this.progress = 0;
    }

    /**
     * 根据当前设置的配方制作物品
     * 该方法会检查当前的制作配方，如果存在，则根据配方制作物品并更新输出槽中的物品数量
     * 同时，该方法还会从输入槽中移除一个单位的制作材料
     */
    private void craftItem() {

        // 获取当前正在使用的制作配方
        Optional<PolishingMachineRecipe> recipe = getCurrentRecipe();

        // 安全地获取 count 值
        int count = recipe.isPresent() ? recipe.get().getCount() : 0;
        // 更新输出槽中的物品数量根据当前配方的制作结果
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe
                .get()
                .getOutput(null)
                .getItem(), getStack(OUTPUT_SLOT).getCount() + recipe
                .get()
                .getOutput(null)
                .getCount()));

        // 从输入槽中移除一个单位的制作材料，因为物品已经成功制作
        this.removeStack(INPUT_SLOT, count);
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<PolishingMachineRecipe> recipe = getCurrentRecipe();

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe
                .get()
                .getOutput(null)) && canInsertItemIntoOutputSlot(recipe
                .get()
                .getOutput(null)
                .getItem());
    }

    /**
     * 获取当前正在使用的抛光机配方
     * 该方法通过模拟一个简单的库存并填充当前抛光机内的物品，
     * 然后与世界中的配方管理器进行匹配，以找到一个符合条件的抛光机配方
     *
     * @return Optional<PolishingMachineRecipe> 包含匹配到的抛光机配方的Optional对象，
     *         如果没有匹配到任何配方，则返回空的Optional
     */
    private Optional<PolishingMachineRecipe> getCurrentRecipe() {
        // 创建一个简单的库存对象，用于模拟抛光机的物品栏
        SimpleInventory inv = new SimpleInventory(this.size());
        // 遍历抛光机的每个物品栏，将物品复制到模拟的库存中
        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        // 从世界中的配方管理器获取第一个匹配的抛光机配方
        return getWorld()
                .getRecipeManager()
                .getFirstMatch(PolishingMachineRecipe.Type.INSTANCE, inv, getWorld());
    }

    //检测是否达到输出最大数量
    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this
                .getStack(OUTPUT_SLOT)
                .getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    //检测输出栏位是否为输出物品
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this
                .getStack(OUTPUT_SLOT)
                .getItem() == item || this
                .getStack(OUTPUT_SLOT)
                .isEmpty();
    }

    private boolean isOutputSlotAvailable() {
        return this
                .getStack(OUTPUT_SLOT)
                .isEmpty() || this
                .getStack(OUTPUT_SLOT)
                .getCount() < this
                .getStack(OUTPUT_SLOT)
                .getMaxCount();
    }
}