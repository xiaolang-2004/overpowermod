package com.xiaolang233.overpowermod.datagen;

import com.xiaolang233.overpowermod.block.ModBlocks;
import com.xiaolang233.overpowermod.block.custom.CornCropBlock;
import com.xiaolang233.overpowermod.block.custom.StrawberryCropBlock;
import com.xiaolang233.overpowermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.REIKI_ENTITY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.REIKI_ENTITY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FIRE_ETHER_BLOCK);

        blockStateModelGenerator.registerCrop(ModBlocks.STRAWBERRY_CROP, StrawberryCropBlock.AGE,0,1,2,3,4,5);
        blockStateModelGenerator.registerCrop(ModBlocks.CORN_CROP, CornCropBlock.AGE,0,1,2,3,4,5,6,7,8);

        blockStateModelGenerator.registerSimpleState(ModBlocks.POLISHING_MACHINE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ANTHRACITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.REIKI_ENTITY, Models.GENERATED);
        itemModelGenerator.register(ModItems.test005, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_ETHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.SATIETY_PILL, Models.GENERATED);
        itemModelGenerator.register(ModItems.STRAWBERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN,Models.GENERATED);
        itemModelGenerator.register(ModItems.HAPPY_NEW_YEAR_MUSIC_DISC,Models.GENERATED);

        itemModelGenerator.register(ModItems.REIKI_ENTITY_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.REIKI_ENTITY_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.REIKI_ENTITY_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.REIKI_ENTITY_SHOVE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.REIKI_ENTITY_SWORD, Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.REIKI_ENTITY_BOOTS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.REIKI_ENTITY_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.REIKI_ENTITY_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.REIKI_ENTITY_HELMET);

    }
}
