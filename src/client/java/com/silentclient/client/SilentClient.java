package com.silentclient.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.silentclient.client.config.SilentConfig;
import com.silentclient.client.gui.SilentHudRenderer;
import com.silentclient.client.gui.SilentMenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * Silent Client - a lightweight, client-side mod menu for Minecraft 1.21.11.
 *
 * Press the keybind (default RIGHT_SHIFT) at any time - in-game or on most
 * menus - to open the Silent menu and toggle individual modules on or off.
 * Everything here is purely cosmetic/informational: HUD readouts and visual
 * tweaks. There is no combat automation, world-state manipulation, or
 * anything that reads as "cheating" to anti-cheat systems, by design.
 */
public class SilentClient implements ClientModInitializer {

	public static final String MOD_ID = "silentclient";

	private static final KeyMapping.Category KEY_CATEGORY =
			KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

	private static SilentConfig config;
	private static KeyMapping openMenuKey;

	@Override
	public void onInitializeClient() {
		config = SilentConfig.load();

		openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.silentclient.open_menu",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_RIGHT_SHIFT,
				KEY_CATEGORY
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openMenuKey.consumeClick()) {
				if (client.screen == null) {
					client.setScreen(new SilentMenuScreen(null));
				}
			}
		});

		HudElementRegistry.addLast(
				Identifier.fromNamespaceAndPath(MOD_ID, "silent_hud"),
				SilentHudRenderer::render);
	}

	public static SilentConfig getConfig() {
		return config;
	}
}
