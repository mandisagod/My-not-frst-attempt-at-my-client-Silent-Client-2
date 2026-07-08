package com.silentclient.client.module;

import com.silentclient.client.module.impl.ArmorDurabilityModule;
import com.silentclient.client.module.impl.CoordinatesModule;
import com.silentclient.client.module.impl.FpsDisplayModule;
import com.silentclient.client.module.impl.PotionTimersModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central list of every module Silent Client knows about. Add a new module
 * class under {@code com.silentclient.client.module.impl} and register an
 * instance here to make it show up in the menu automatically.
 */
public final class ModuleRegistry {

	private static final List<Module> MODULES = new ArrayList<>();

	// Individually held references so other classes (e.g. the HUD renderer)
	// can call module-specific logic without searching the list every frame.
	public static final ArmorDurabilityModule ARMOR_DURABILITY = register(new ArmorDurabilityModule());
	public static final CoordinatesModule COORDINATES = register(new CoordinatesModule());
	public static final FpsDisplayModule FPS_DISPLAY = register(new FpsDisplayModule());
	public static final PotionTimersModule POTION_TIMERS = register(new PotionTimersModule());

	private ModuleRegistry() {
	}

	private static <T extends Module> T register(T module) {
		MODULES.add(module);
		return module;
	}

	public static List<Module> getModules() {
		return Collections.unmodifiableList(MODULES);
	}
}
