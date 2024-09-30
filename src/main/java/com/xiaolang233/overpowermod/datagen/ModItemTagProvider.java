package com.xiaolang233.overpowermod.datagen;

import com.xiaolang233.overpowermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider{

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);

    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(ModItems.REIKI_ENTITY_BOOTS,ModItems.REIKI_ENTITY_CHESTPLATE,
                ModItems.REIKI_ENTITY_HELMET,ModItems.REIKI_ENTITY_LEGGINGS,ModItems.REIKI_ENTITY_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
                .add(ModItems.HAPPY_NEW_YEAR_MUSIC_DISC);
    }
}
