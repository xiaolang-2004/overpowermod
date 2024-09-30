package com.xiaolang233.overpowermod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

/**
 * 定义了一个名为PolishingMachineRecipe的类，实现了Recipe<SimpleInventory>接口，
 * 用于表示和处理磨光机的制作配方。
 */
public class PolishingMachineRecipe implements Recipe<SimpleInventory> {
    // 定义了配方的唯一标识符
    private final Identifier id;
    // 定义了配方的输出物品
    private final ItemStack output;
    // 定义了配方的所需材料列表
    private final List<Ingredient> recipeItems;
    private final int count;

    /**
     * 构造函数，初始化了PolishingMachineRecipe实例。
     *
     * @param id          配方的唯一标识符
     * @param output      配方的输出物品
     * @param recipeItems 配方的所需材料列表
     */
    public PolishingMachineRecipe(Identifier id, ItemStack output, List<Ingredient> recipeItems,int count) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.count = count;
    }

    /**
     * 检查给定的库存是否符合配方的匹配条件。
     *
     * @param inventory 存储原料的SimpleInventory对象
     * @param world     世界对象，用于检查是否是客户端世界
     * @return 如果匹配则返回true，否则返回false
     */
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        // 如果是客户端世界，则返回false，不允许进行配方匹配
        if (world.isClient()){
            return false;
        }
        // 检查库存的第一个槽位是否包含配方所需的材料
        return recipeItems.get(0).test(inventory.getStack(0));
    }

    /**
     * 根据给定的库存和注册表管理器制作配方的输出物品。
     *
     * @param inventory      存储原料的SimpleInventory对象
     * @param registryManager 注册表管理器，用于动态注册内容
     * @return 配方的输出物品
     */
    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        // 返回配方的输出物品
        return output;
    }

    /**
     * 检查配方是否能够在给定的格子宽度和高度下放置。
     *
     * @param width  格子的宽度
     * @param height 格子的高度
     * @return 如果能够放置则返回true，否则返回false
     */
    @Override
    public boolean fits(int width, int height) {
        // 由于配方只占用一个格子，所以总是可以放置
        return true;
    }

    /**
     * 获取配方的输出物品。
     *
     * @param registryManager 注册表管理器，用于动态注册内容
     * @return 配方的输出物品
     */
    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        // 返回配方的输出物品
        return output;
    }

    /**
     * 获取配方的唯一标识符。
     *
     * @return 配方的唯一标识符
     */
    @Override
    public Identifier getId() {
        // 返回配方的唯一标识符
        return id;
    }

    /**
     * 获取配方的序列化器。
     *
     * @return 配方的序列化器实例
     */
    @Override
    public RecipeSerializer<?> getSerializer() {
        // 返回配方的序列化器实例
        return Serializer.INSTANCE;
    }

    /**
     * 获取配方的类型。
     *
     * @return 配方的类型实例
     */
    @Override
    public RecipeType<?> getType() {
        // 返回配方的类型实例
        return Type.INSTANCE;
    }

    /**
     * 定义了PolishingMachineRecipe的类型类，实现了RecipeType<PolishingMachineRecipe>接口，
     * 用于表示磨光机配方的类型。
     */
    public static class Type implements RecipeType<PolishingMachineRecipe>{
        // 定义了磨光机配方类型的实例
        public static final Type INSTANCE = new Type();
        // 定义了磨光机配方类型的ID
        public static final String ID = "polishing_machine";
    }

    /**
     * 定义了PolishingMachineRecipe的序列化器类，实现了RecipeSerializer<PolishingMachineRecipe>接口，
     * 用于序列化和反序列化磨光机的配方数据。
     */
    public static class Serializer implements RecipeSerializer<PolishingMachineRecipe>{
        // 定义了序列化器的实例
        public static final Serializer INSTANCE = new Serializer();
        // 定义了序列化器的ID
        public static final String ID = "polishing_machine";

        /**
         * 从JSON对象中读取并创建抛光机配方
         *
         * @param id 抛光机配方的唯一标识符
         * @param json 包含配方信息的JSON对象
         * @return 返回创建的PolishingMachineRecipe对象
         */
        public PolishingMachineRecipe read(Identifier id, JsonObject json) {
            // 从JSON对象中读取出产出物品
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));

            int count = JsonHelper.getInt(json, "count",1);

            // 获取原料列表的JSON数组
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            // 初始化一个长度为1的原料列表，并默认填充为空原料
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1,Ingredient.EMPTY);
            // 遍历原料数组，将每个原料转换为Ingredient对象后填充到原料列表中
            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            // 使用读取到的产出物品和原料列表创建并返回抛光机配方对象
            return new PolishingMachineRecipe(id,output,inputs,count);
        }

        /**
         * 从PacketByteBuf中读取并创建磨光机配方。
         *
         * @param id 配方的唯一标识符
         * @param buf 包含配方数据的PacketByteBuf对象
         * @return 创建的磨光机配方实例
         */
        @Override
        public PolishingMachineRecipe read(Identifier id, PacketByteBuf buf) {
            // 从缓冲中读取配方的原料和输出物品
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1,Ingredient.EMPTY);


            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i,Ingredient.fromPacket(buf));
            }
            ItemStack output = buf.readItemStack();
            int count = buf.readInt();
            // 创建并返回磨光机配方实例
            return new PolishingMachineRecipe(id,output,inputs,count);
        }

        /**
         * 将磨光机配方的数据写入PacketByteBuf中。
         *
         * @param buf      用于存储配方数据的PacketByteBuf对象
         * @param recipe 将要序列化的磨光机配方实例
         */
        @Override
        public void write(PacketByteBuf buf, PolishingMachineRecipe recipe) {
            // 将配方的原料和输出物品写入缓冲中
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient: recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.getOutput(null));
        }
    }

}
