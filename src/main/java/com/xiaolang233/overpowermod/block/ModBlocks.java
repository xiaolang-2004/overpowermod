package com.xiaolang233.overpowermod.block;

import com.xiaolang233.overpowermod.Overpowermod;
import com.xiaolang233.overpowermod.block.custom.CornCropBlock;
import com.xiaolang233.overpowermod.block.custom.FireEtherBlock;
import com.xiaolang233.overpowermod.block.custom.PolishingMachine;
import com.xiaolang233.overpowermod.block.custom.StrawberryCropBlock;
import com.xiaolang233.overpowermod.sounds.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static final Block REIKI_ENTITY_BLOCK = registerBlocks("reiki_entity_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    
    public static final Block REIKI_ENTITY_ORE = registerBlocks("reiki_entity_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(5f),
                    UniformIntProvider.create(2,5)));
    public static final Block FIRE_ETHER_BLOCK = registerBlocks("fire_ether_block",
            new FireEtherBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(ModSounds.BLOCK_SOUND_GROUP)));
    public static final Block STRAWBERRY_CROP = Registry.register(Registries.BLOCK,new Identifier(Overpowermod.MOD_ID
            ,"strawberry_crop"),new StrawberryCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block CORN_CROP = Registry.register(Registries.BLOCK,new Identifier(Overpowermod.MOD_ID
            ,"corn_crop"),new CornCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));
    public static final Block POLISHING_MACHINE = registerBlocks("polishing_machine",
            new PolishingMachine(FabricBlockSettings.copyOf(Blocks.STONE)));

    private static Block registerBlocks(String name, Block block) {
        registerBlockItems(name,block);
        return Registry.register(Registries.BLOCK,new Identifier(Overpowermod.MOD_ID,name),block);
    }
    private static Item registerBlockItems(String name, Block block) {
        return Registry.register(Registries.ITEM,new Identifier(Overpowermod.MOD_ID,name),new BlockItem(block,
                new FabricItemSettings()));
    }
    public static void registerMobBlocks() {

    }
}
