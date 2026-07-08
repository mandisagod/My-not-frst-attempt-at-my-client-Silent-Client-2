package com.silentclient.client.gui;

import com.silentclient.client.config.SilentConfig;
import com.silentclient.client.module.ModuleRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

/**
 * Draws every enabled HUD-category module, stacked vertically in the
 * configured corner of the screen. Individual modules don't know or care
 * where they're being drawn - they just report how tall the line(s) they
 * drew were, and this class handles stacking + corner math.
 */
public final class SilentHudRenderer {

	private SilentHudRenderer() {
	}

	public static void render(GuiGraphics context, DeltaTracker tickCounter) {
		Minecraft client = Minecraft.getInstance();
		LocalPlayer player = client.player;
		if (player == null || client.options.hideGui) {
			return;
		}

		SilentConfig config = com.silentclient.client.SilentClient.getConfig();
		boolean topAnchor = config.hudPosition.startsWith("TOP");
		boolean leftAnchor = config.hudPosition.endsWith("LEFT");

		int x = leftAnchor ? config.hudXOffset : context.guiWidth() - config.hudXOffset;
		int y = topAnchor ? config.hudYOffset : context.guiHeight() - config.hudYOffset;
		int textColor = 0xFFFFFFFF;

		// When anchored to the bottom, we draw upward, so line order is reversed.
		int direction = topAnchor ? 1 : -1;
		int cursorY = y;

		if (ModuleRegistry.ARMOR_DURABILITY.isEnabled()) {
			int used = ModuleRegistry.ARMOR_DURABILITY.render(context, player, drawX(x, leftAnchor), cursorY, textColor);
			cursorY += direction * used;
		}
		if (ModuleRegistry.COORDINATES.isEnabled()) {
			int used = ModuleRegistry.COORDINATES.render(context, player, drawX(x, leftAnchor), cursorY, textColor);
			cursorY += direction * used;
		}
		if (ModuleRegistry.FPS_DISPLAY.isEnabled()) {
			int used = ModuleRegistry.FPS_DISPLAY.render(context, player, drawX(x, leftAnchor), cursorY, textColor);
			cursorY += direction * used;
		}
		if (ModuleRegistry.POTION_TIMERS.isEnabled()) {
			int used = ModuleRegistry.POTION_TIMERS.render(context, player, drawX(x, leftAnchor), cursorY, textColor);
			cursorY += direction * used;
		}
	}

	private static int drawX(int anchorX, boolean leftAnchor) {
		// All our modules currently draw left-aligned text, so a right anchor
		// just means "keep some margin from the right edge" - good enough for
		// short single-line HUD labels. Left-anchored is pixel-exact.
		return leftAnchor ? anchorX : Math.max(4, anchorX - 160);
	}
}
