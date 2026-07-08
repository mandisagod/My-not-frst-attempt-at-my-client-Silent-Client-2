package com.silentclient.client.module.impl;

import com.silentclient.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;

/**
 * Shows the player's current block coordinates, e.g. "XYZ: 123, 64, -78".
 * Purely informational - identical to what F3 already shows, just without
 * needing the full debug screen open.
 */
public class CoordinatesModule extends Module {

	public CoordinatesModule() {
		super("Coordinates", "Shows your current X/Y/Z position.", Category.HUD, false);
	}

	public int render(GuiGraphics context, LocalPlayer player, int x, int y, int textColor) {
		String label = "XYZ: " + Math.floor(player.getX()) + ", " + Math.floor(player.getY()) + ", " + Math.floor(player.getZ());
		context.drawString(Minecraft.getInstance().font, label, x, y, textColor, true);
		return Minecraft.getInstance().font.lineHeight + 2;
	}
}
