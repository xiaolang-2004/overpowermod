package com.xiaolang233.overpowermod;

import com.xiaolang233.overpowermod.block.entity.ModBlockEntities;
import com.xiaolang233.overpowermod.item.ModItemGroup;
import com.xiaolang233.overpowermod.item.ModItems;
import com.xiaolang233.overpowermod.block.ModBlocks;
import com.xiaolang233.overpowermod.recipe.ModRecipes;
import com.xiaolang233.overpowermod.screen.ModScreenHandlers;
import com.xiaolang233.overpowermod.sounds.ModSounds;
import com.xiaolang233.overpowermod.util.ModLootTableModifiers;
import com.xiaolang233.overpowermod.util.ModTrades;
import com.xiaolang233.overpowermod.villager.ModVillagers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Overpowermod implements ModInitializer {
	public static final String MOD_ID = "overpowermod";

	// 此记录器用于将文本写入控制台和日志文件.
	// 使用您的 mod id 作为 logger 的名称被认为是最佳实践.
	// 这样，就可以清楚地知道哪个 Mod 编写了信息、警告和错误.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ModItems.registerItems();
		ModItemGroup.registerModItemGroup();
		ModBlocks.registerMobBlocks();

		ModLootTableModifiers.modifierLootTables();
		ModTrades.registerTrades();

		ModVillagers.registerVillagers();
		ModSounds.registerSounds();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();

		FuelRegistry.INSTANCE.add(ModItems.ANTHRACITE,200);
	}
}