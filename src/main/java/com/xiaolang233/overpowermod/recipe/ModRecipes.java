package com.xiaolang233.overpowermod.recipe;

import com.xiaolang233.overpowermod.Overpowermod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER,new Identifier(Overpowermod.MOD_ID,PolishingMachineRecipe.Serializer.ID),
                PolishingMachineRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE,new Identifier(Overpowermod.MOD_ID,PolishingMachineRecipe.Type.ID),
                PolishingMachineRecipe.Type.INSTANCE);
    }
}