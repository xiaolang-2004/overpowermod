package com.xiaolang233.overpowermod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModFoodPill extends Item {
    private int useTick;
    public ModFoodPill(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        if (stack.getItem().isFood()) {
            return this.getFoodComponent().isSnack() ? 6 : 16;
        } else {
            return 0;
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        return super.isFood() ? user.eatFood(world, stack) : stack;
    }
}


