package com.xiaolang233.overpowermod.item;

import com.xiaolang233.overpowermod.Overpowermod;
import com.xiaolang233.overpowermod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class ModItemGroup {

    public static final ItemGroup OVERPOWERMOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Overpowermod.MOD_ID,"overpowermod_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.overpowermod_group")).icon(()-> new ItemStack(ModItems.REIKI_ENTITY)).entries((displayContext, entries) -> {
                entries.add(ModItems.REIKI_ENTITY);
                entries.add(Items.BOW);
                entries.add(ModBlocks.REIKI_ENTITY_BLOCK);
                entries.add(ModBlocks.REIKI_ENTITY_ORE);
                entries.add(ModItems.test005);
                entries.add(ModItems.FIRE_ETHER);
                entries.add(ModBlocks.FIRE_ETHER_BLOCK);
                entries.add(ModItems.SATIETY_PILL);
                entries.add(ModItems.ANTHRACITE);
                entries.add(ModItems.REIKI_ENTITY_SWORD);
                entries.add(ModItems.REIKI_ENTITY_PICKAXE);
                entries.add(ModItems.REIKI_ENTITY_AXE);
                entries.add(ModItems.REIKI_ENTITY_SHOVE);
                entries.add(ModItems.REIKI_ENTITY_HOE);
                entries.add(ModItems.REIKI_ENTITY_HELMET);
                entries.add(ModItems.REIKI_ENTITY_CHESTPLATE);
                entries.add(ModItems.REIKI_ENTITY_LEGGINGS);
                entries.add(ModItems.REIKI_ENTITY_BOOTS);
                entries.add(ModItems.STRAWBERRY);
                entries.add(ModItems.STRAWBERRYSEEDS);
                entries.add(ModItems.CORN);
                entries.add(ModItems.CORNSEEDS);
                entries.add(ModItems.HAPPY_NEW_YEAR_MUSIC_DISC);
                entries.add(ModBlocks.POLISHING_MACHINE);
            }).build());

    public static void registerModItemGroup(){

    }
}
