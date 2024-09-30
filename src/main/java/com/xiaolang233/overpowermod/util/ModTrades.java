package com.xiaolang233.overpowermod.util;

import com.xiaolang233.overpowermod.block.ModBlocks;
import com.xiaolang233.overpowermod.item.ModItems;
import com.xiaolang233.overpowermod.villager.ModVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;


public class ModTrades {
    public static void registerTrades(){
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD,2),
                            new ItemStack(ModItems.STRAWBERRY,6),
                            6,5,0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD,6),
                            new ItemStack(ModItems.CORNSEEDS,3),
                            5,7,0.075f
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.REIKI_ENTITY,16),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(Enchantments.SHARPNESS,2)),
                            3,12,0.05f
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.REIKI_ENTITY_BLOCK_MASTER,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.REIKI_ENTITY,16),
                            new ItemStack(ModBlocks.REIKI_ENTITY_BLOCK,2),
                            3,12,0.05f
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.REIKI_ENTITY_BLOCK_MASTER,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.REIKI_ENTITY,32),
                            new ItemStack(ModItems.test005,1),
                            3,24,0.05f
                    ));
                });
    }
}