package com.xiaolang233.overpowermod.datagen;

import com.xiaolang233.overpowermod.block.ModBlocks;
import com.xiaolang233.overpowermod.block.custom.CornCropBlock;
import com.xiaolang233.overpowermod.block.custom.StrawberryCropBlock;
import com.xiaolang233.overpowermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

public class ModLootTablesProvider extends FabricBlockLootTableProvider {
    public ModLootTablesProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.REIKI_ENTITY_BLOCK);//掉落本身

        addDrop(ModBlocks.REIKI_ENTITY_ORE, copperOreDrops(ModBlocks.REIKI_ENTITY_ORE, ModItems.REIKI_ENTITY));

        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition
                .builder(ModBlocks.STRAWBERRY_CROP)
                .properties(StatePredicate.Builder
                        .create()
                        .exactMatch(StrawberryCropBlock.AGE, 5));
        addDrop(ModBlocks.STRAWBERRY_CROP, cropDrops(ModBlocks.STRAWBERRY_CROP, ModItems.STRAWBERRY,
                ModItems.STRAWBERRYSEEDS, builder));

        // 定义一个区块状态属性战利品条件的构建器，用于指定玉米作物块的状态属性
        BlockStatePropertyLootCondition.Builder CORN_CROP_AGE_8 = BlockStatePropertyLootCondition
                .builder(ModBlocks.CORN_CROP)  // 设置适用的作物块为自定义的玉米作物块
                .properties(StatePredicate.Builder
                        .create()
                        .exactMatch(CornCropBlock.AGE, 8));
        addDrop(ModBlocks.CORN_CROP, cropDrops(ModBlocks.CORN_CROP, ModItems.CORN, ModItems.CORNSEEDS, CORN_CROP_AGE_8));
    }

    public LootTable.Builder copperOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>) this.applyExplosionDecay(
                        drop,
                        ItemEntry
                                .builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );
    }
}
