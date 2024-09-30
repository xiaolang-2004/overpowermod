package com.xiaolang233.overpowermod.datagen;

import com.xiaolang233.overpowermod.block.ModBlocks;
import com.xiaolang233.overpowermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipesProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> reikientity_list = List.of(ModBlocks.REIKI_ENTITY_ORE);

    public ModRecipesProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.REIKI_ENTITY,
                RecipeCategory.BUILDING_BLOCKS, ModBlocks.REIKI_ENTITY_BLOCK);
        offerSmelting(exporter, reikientity_list, RecipeCategory.MISC, ModItems.REIKI_ENTITY, 1.0f, 200, "reikientity");
        offerBlasting(exporter, reikientity_list, RecipeCategory.MISC, ModItems.REIKI_ENTITY, 1.0f, 100, "reikientity");
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, Items.SUGAR, 3)
                .pattern("###")
                .input('#', Items.BEETROOT)
                .criterion(hasItem(Items.BEETROOT), conditionsFromItem(Items.BEETROOT))
                .offerTo(exporter, new Identifier("sugar_from_beetroot"))
        ;
    }
}
