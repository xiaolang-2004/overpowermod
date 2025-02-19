package com.xiaolang233.overpowermod.block.entity;

import com.xiaolang233.overpowermod.Overpowermod;
import com.xiaolang233.overpowermod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<PolishingMachineBlockEntity> POLISHING_MACHINE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,new Identifier(Overpowermod.MOD_ID,"polishing_machine_block_entity"),
                    FabricBlockEntityTypeBuilder.create(PolishingMachineBlockEntity::new,
                            ModBlocks.POLISHING_MACHINE).build());
    public static void registerBlockEntities(){

    }
}