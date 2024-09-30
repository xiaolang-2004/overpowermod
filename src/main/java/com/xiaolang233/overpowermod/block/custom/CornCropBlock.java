package com.xiaolang233.overpowermod.block.custom;

import com.xiaolang233.overpowermod.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;


public class CornCropBlock extends CropBlock {
    public static final int FIRST_STAGE_AGE = 7;
    public static final int SECOND_STAGE_AGE = 1;
    public static final IntProperty AGE = IntProperty.of("age",0,8);
    // 定义一个表示作物生长阶段的常量数组，元素类型为VoxelShape
    // 该数组用于根据作物的生长年龄改变其形状，模拟作物的生长过程
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    // 构造函数，用于初始化CornCropBlock类的对象
    // 参数settings用于配置作物块的各种属性，如硬度、抗爆性等
    public CornCropBlock(Settings settings) {
        super(settings); // 调用父类的构造函数，传递settings参数，设置作物块的基础属性
    }

    /**
     * 重写getOutlineShape方法以获取当前区块形状
     * 此方法根据区块的状态返回一个三维形状，用于渲染和碰撞检测
     *
     * @param state 区块的当前状态
     * @param world 区块视图，用于访问区块信息
     * @param pos 区块的位置
     * @param context 形状上下文，提供额外的上下文信息
     * @return 返回根据区块生长年龄决定的VoxelShape
     *
     * 该方法通过AGE_TO_SHAPE数组根据区块的生长年龄来选择合适的形状进行返回
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_AGE + SECOND_STAGE_AGE;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.CORNSEEDS;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos) || (world.getBlockState(pos.down(1)).isOf(this) &&
                world.getBlockState(pos.down(1)).get(AGE) == 7);
    }

    /**
     * 重写applyGrowth方法以促进植物生长
     * 根据当前状态和世界环境，调整植物的年龄值，模拟生长过程
     * 在特定条件下，可能使植物向上生长或在其当前位置生长
     *
     * @param world 代表当前游戏世界的World对象
     * @param pos 植物所在的位置
     * @param state 植物当前的状态
     */
    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        // 计算下个生长周期的年龄值
        int nextAge = this.getAge(state) + this.getGrowthAmount(world);
        // 获取植物成熟时的最大年龄
        int maxAge = this.getMaxAge();
        // 确保年龄值不超过最大成熟年龄
        if (nextAge > maxAge){
            nextAge = maxAge;
        }
        // 检查植物是否处于第一阶段并且头顶是空气
        if (this.getAge(state) == FIRST_STAGE_AGE && world.getBlockState(pos.up(1)).isOf(Blocks.AIR)){
            // 在植物头顶的位置种植并设置新的年龄值
            world.setBlockState(pos.up(1),this.withAge(nextAge),2);
        } else {
            // 否则，在当前位置种植并设置调整后的年龄值
            world.setBlockState(pos,this.withAge(nextAge - 1),2);
        }
    }


    /**
     * 重写随机更新逻辑
     * 当光线水平足够时，作物可能会生长
     *
     * @param state 当前方块的状态
     * @param world 服务器世界对象
     * @param pos 当前方块的位置
     * @param random 随机数生成器
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // 检查当前位置的基底光照水平
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            // 获取当前作物的生长阶段
            int currentAge = this.getAge(state);
            // 检查作物是否还未完全成熟
            if (currentAge < this.getMaxAge()) {
                // 计算周围环境的湿度因子
                float f = getAvailableMoisture(this, world, pos);
                // 根据湿度因子决定是否让作物生长
                if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                    // 如果作物处于第一阶段，并且上方是空气，尝试在上方生成下一阶段
                    if (currentAge == FIRST_STAGE_AGE) {
                        if (world.getBlockState(pos.up(1)).isOf(Blocks.AIR)) {
                            world.setBlockState(pos.up(1), this.withAge(currentAge + 1), 2);
                        }
                    } else {
                        // 否则，在当前位置设置作物为下一阶段
                        world.setBlockState(pos, this.withAge(currentAge + 1), 2);
                    }
                }
            }
        }
    }
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            // 检查下方是否有作物
            BlockPos bottomPos = pos.down();
            BlockState bottomState = world.getBlockState(bottomPos);
            if (bottomState.getBlock() instanceof CornCropBlock) {
                // 破坏下方作物
                world.breakBlock(bottomPos, true, player);
                world.setBlockState(bottomPos, this.withAge(FIRST_STAGE_AGE));
            }

            // 破坏当前作物
            super.onBreak(world, pos, state, player);
        }
    }
}