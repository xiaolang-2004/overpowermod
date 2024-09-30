package com.xiaolang233.overpowermod.item;

import com.xiaolang233.overpowermod.Overpowermod;
import com.xiaolang233.overpowermod.block.ModBlocks;
import com.xiaolang233.overpowermod.item.custom.*;
import com.xiaolang233.overpowermod.sounds.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModItems {
    public static final Item REIKI_ENTITY = registerItem("reiki_entity", new Item(new FabricItemSettings()));
    //这个registerItem后面跟的第一个name 必须是[a-z0-9/._-]不包括大写字母

    public static final Item test002 = registerItem("test002", new Item(new FabricItemSettings()));
    //无用：未使用

    public static final Item test005 = registerItem("test005", new Prospector(new FabricItemSettings().maxDamage(64)));
    //new后面跟custom里的java类以实现功能

    public static final Item FIRE_ETHER = registerItem("fire_ether", new FireEther(new FabricItemSettings().maxDamage(64)));

    //食物
    public static final Item SATIETY_PILL = registerItem("satiety_pill",
            new ModFoodPill(new FabricItemSettings().food(ModFoodComponents.SATIETY_PILL)));
    public static final Item STRAWBERRY = registerItem("strawberry",
            new Item(new FabricItemSettings().food(ModFoodComponents.STRAWBERRY)));
    public static final Item STRAWBERRYSEEDS = registerItem("strawberry_seeds",
            new AliasedBlockItem(ModBlocks.STRAWBERRY_CROP, new FabricItemSettings()));
    public static final Item CORN = registerItem("corn", new Item(new FabricItemSettings().food(ModFoodComponents.CORN)));
    public static final Item CORNSEEDS = registerItem("corn_seeds",
            new AliasedBlockItem(ModBlocks.CORN_CROP, new FabricItemSettings()));

    public static final Item ANTHRACITE = registerItem("anthracite", new Item(new FabricItemSettings()));
    //无烟煤

    public static final Item REIKI_ENTITY_PICKAXE = registerItem("reiki_entity_pickaxe",
            new PickaxeItem(ModToolMaterial.REIKI_ENTITY, 2, 2f, new FabricItemSettings()));
    public static final Item REIKI_ENTITY_AXE = registerItem("reiki_entity_axe",
            new AxeItem(ModToolMaterial.REIKI_ENTITY, 2, 2f, new FabricItemSettings()));
    public static final Item REIKI_ENTITY_SWORD = registerItem("reiki_entity_sword",
            new SwordItem(ModToolMaterial.REIKI_ENTITY, 2, 2f, new FabricItemSettings()));
    public static final Item REIKI_ENTITY_HOE = registerItem("reiki_entity_hoe",
            new HoeItem(ModToolMaterial.REIKI_ENTITY, 2, 2f, new FabricItemSettings()));
    public static final Item REIKI_ENTITY_SHOVE = registerItem("reiki_entity_shove",
            new ShovelItem(ModToolMaterial.REIKI_ENTITY, 2, 2f, new FabricItemSettings()));

    public static final Item REIKI_ENTITY_HELMET = registerItem("reiki_entity_helmet",
            new ModArmorItem(ModArmorMaterial.REIKI_ENTITY, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item REIKI_ENTITY_CHESTPLATE = registerItem("reiki_entity_chestplate",
            new ModArmorItem(ModArmorMaterial.REIKI_ENTITY, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item REIKI_ENTITY_LEGGINGS = registerItem("reiki_entity_leggings",
            new ModArmorItem(ModArmorMaterial.REIKI_ENTITY, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item REIKI_ENTITY_BOOTS = registerItem("reiki_entity_boots",
            new ModArmorItem(ModArmorMaterial.REIKI_ENTITY, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item HAPPY_NEW_YEAR_MUSIC_DISC = registerItem("happy_new_year_music_disc",
            new MusicDiscItem(7, ModSounds.HAPPY_NEW_YEAR,new FabricItemSettings().maxCount(1),144));

    private static void addItemToIG(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.add(REIKI_ENTITY);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Overpowermod.MOD_ID, name), item);
    }

    public static void registerItems() {
        ItemGroupEvents
                .modifyEntriesEvent(ItemGroups.TOOLS)
                .register(ModItems::addItemToIG);
    }
}
