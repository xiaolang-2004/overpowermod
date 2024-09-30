package com.xiaolang233.overpowermod.item.custom;

import com.google.common.collect.ImmutableMap;
import com.xiaolang233.overpowermod.item.ModArmorMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, StatusEffectInstance> MATERIAL_STATUS_EFFECT_INSTANCE_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, StatusEffectInstance>())
                    .put(ModArmorMaterial.REIKI_ENTITY, new StatusEffectInstance(StatusEffects.SPEED, 1000, 1,
                            false, false, true))
                    .build();

    public ModArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            if (entity instanceof PlayerEntity player && hasFullSuitOfArmor(player)) {
                evaluateArmorEffects(player);
            }
        }
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, StatusEffectInstance> entry : MATERIAL_STATUS_EFFECT_INSTANCE_MAP.entrySet()) {
            ArmorMaterial armorMaterial = entry.getKey();
            StatusEffectInstance statusEffectInstance = entry.getValue();

            if (hasCorrectArmorOn(armorMaterial, player)) {
                addStatusEffectForMaterial(player, armorMaterial, statusEffectInstance);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, ArmorMaterial armorMaterial, StatusEffectInstance statusEffectInstance) {
        boolean hasEffects = player.hasStatusEffect(statusEffectInstance.getEffectType());

        if (hasCorrectArmorOn(armorMaterial,player) && !hasEffects){
            player.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
        }
    }

    private boolean hasCorrectArmorOn(ArmorMaterial armorMaterial, PlayerEntity player) {
        for (ItemStack armorStack:player.getInventory().armor){  //不写for循环会崩溃
            if (!(armorStack.getItem() instanceof ArmorItem)){
                return false;
            }
        }
        ArmorItem boots = ((ArmorItem)player.getInventory().getArmorStack(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmorStack(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());

        //判断是否为同一整套盔甲
        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }


    //检测是否全部装备
    private boolean hasFullSuitOfArmor(PlayerEntity player) {
        ItemStack boots = player
                .getInventory()
                .getArmorStack(0);
        ItemStack leggins = player
                .getInventory()
                .getArmorStack(1);
        ItemStack breastplate = player
                .getInventory()
                .getArmorStack(2);
        ItemStack helmet = player
                .getInventory()
                .getArmorStack(3);

        return !helmet.isEmpty() && !breastplate.isEmpty() && !leggins.isEmpty() && !boots.isEmpty();
    }
}
