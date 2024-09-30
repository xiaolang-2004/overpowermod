package com.xiaolang233.overpowermod.villager;

import com.xiaolang233.overpowermod.Overpowermod;
import com.google.common.collect.ImmutableSet;
import com.xiaolang233.overpowermod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> REIKI_ENTITY_BLOCK_POI_KEY = point("reiki_entity_block_poi");
    public static final PointOfInterestType REIKI_ENTITY_BLOCK_POI = registerPointOfInterestType("reiki_entity_block_poi", ModBlocks.REIKI_ENTITY_BLOCK);
    public static final VillagerProfession REIKI_ENTITY_BLOCK_MASTER = registerVillagerProfession("reiki_entity_block_master",REIKI_ENTITY_BLOCK_POI_KEY);
    private static VillagerProfession registerVillagerProfession(String name,RegistryKey<PointOfInterestType> type){
        return Registry.register(Registries.VILLAGER_PROFESSION,new Identifier(Overpowermod.MOD_ID,name),
                new VillagerProfession(name,entry -> entry.matchesKey(type),entry -> entry.matchesKey(type),
                        ImmutableSet.of(),ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN));
    }
    private static PointOfInterestType registerPointOfInterestType(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(Overpowermod.MOD_ID,name), 1 //一个方块能转职几个
                , 1 //村民搜索范围
        ,block);
    }

    private static RegistryKey<PointOfInterestType> point(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(Overpowermod.MOD_ID, name));
    }

    public static void registerVillagers() {

    }
}
