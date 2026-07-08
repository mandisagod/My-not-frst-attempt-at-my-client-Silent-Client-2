package com.silentclient.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silentclient.client.module.Module;
import com.silentclient.client.module.ModuleRegistry;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Persists which modules are enabled to config/silentclient.json, so your
 * setup is remembered between sessions. Anchored on module name, so adding
 * or removing modules in future versions doesn't corrupt old configs.
 */
public final class SilentConfig {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	/** HUD anchor corner, and other simple prefs live here as plain fields. */
	public String hudPosition = "TOP_LEFT";
	public int hudXOffset = 4;
	public int hudYOffset = 4;

	/** module display name -> enabled */
	public Map<String, Boolean> enabledModules = new HashMap<>();

	private static Path configPath() {
		return FabricLoader.getInstance().getConfigDir().resolve("silentclient.json");
	}

	public static SilentConfig load() {
		Path path = configPath();
		if (Files.exists(path)) {
			try {
				String json = Files.readString(path);
				SilentConfig cfg = GSON.fromJson(json, SilentConfig.class);
				if (cfg != null) {
					cfg.applyTo(ModuleRegistry.getModules());
					return cfg;
				}
			} catch (IOException | RuntimeException e) {
				System.err.println("[SilentClient] Failed to read config, using defaults: " + e.getMessage());
			}
		}
		SilentConfig cfg = new SilentConfig();
		cfg.captureFrom(ModuleRegistry.getModules());
		cfg.save();
		return cfg;
	}

	/** Copies enabled/disabled state from the live modules into this config object. */
	public void captureFrom(Iterable<Module> modules) {
		for (Module module : modules) {
			enabledModules.put(module.getName(), module.isEnabled());
		}
	}

	/** Applies this config's saved state onto the live modules. */
	public void applyTo(Iterable<Module> modules) {
		for (Module module : modules) {
			Boolean saved = enabledModules.get(module.getName());
			if (saved != null) {
				module.setEnabled(saved);
			}
		}
	}

	public void save() {
		captureFrom(ModuleRegistry.getModules());
		try {
			Files.writeString(configPath(), GSON.toJson(this));
		} catch (IOException e) {
			System.err.println("[SilentClient] Failed to save config: " + e.getMessage());
		}
	}
}
