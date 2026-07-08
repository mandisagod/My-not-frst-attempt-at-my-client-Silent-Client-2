package com.silentclient.client.module.impl;

import com.silentclient.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

/**
 * Shows a frames-per-second counter, e.g. "FPS: 144".
 *
 * This counts its own render calls rather than reading Minecraft's internal
 * FPS field, since our HUD element's render method is invoked exactly once
 * per rendered frame - that makes it a reliable, mapping-independent way to
 * measure FPS without needing a mixin into private client internals.
 */
public class FpsDisplayModule extends Module {

	private int frameCount = 0;
	private int lastFps = 0;
	private long windowStartMs = System.currentTimeMillis();

	public FpsDisplayModule() {
		super("FPS Display", "Shows your current frames per second.", Category.HUD, false);
	}

	public int render(GuiGraphics context, LocalPlayer player, int x, int y, int textColor) {
		frameCount++;
		long now = System.currentTimeMillis();
		long elapsed = now - windowStartMs;
		if (elapsed >= 1000) {
			lastFps = (int) Math.round(frameCount * 1000.0 / elapsed);
			frameCount = 0;
			windowStartMs = now;
		}

		String label = "FPS: " + lastFps;
		context.drawString(Minecraft.getInstance().font, label, x, y, textColor, true);
		return Minecraft.getInstance().font.lineHeight + 2;
	}
}
