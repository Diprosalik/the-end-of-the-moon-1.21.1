package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheEndOfTheMoon implements ModInitializer {
	public static final String MOD_ID = "the-end-of-the-moon";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		ChorusFlower.ClickOnFlowerToHarvest();
	}
}