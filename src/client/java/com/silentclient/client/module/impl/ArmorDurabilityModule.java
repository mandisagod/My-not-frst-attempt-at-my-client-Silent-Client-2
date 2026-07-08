package com.silentclient.client.module.impl;

import com.silentclient.client.module.Module;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/**
 * Shows the average remaining durability of your currently worn armor,
 * e.g. "Armor: 75%". This is the same logic as the standalone Armor Avg
 * HUD mod, folded in here as a Silent Client module.
 */
public class ArmorDurabilityModule extends Module {

	private static final EquipmentSlot[] ARMOR_SLOTS = {
			EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
	};

	private boolean showAsPercent = true;

	public ArmorDurabilityModule() {
		super("Armor Durability", "Average durability of your worn armor.", Category.HUD, true);
	}

	public boolean isShowAsPercent() {
		return showAsPercent;
	}

	public void setShowAsPercent(boolean showAsPercent) {
		this.showAsPercent = showAsPercent;
	}

	/**
	 * Draws the "Armor: x%" line at the given position and returns the
	 * height it used, so the HUD renderer can stack the next line below it.
	 */
	public int render(GuiGraphics context, LocalPlayer player, int x, int y, int textColor) {
		Result result = computeAverage(player);
		if (result.piecesWorn == 0) {
			return 0;
		}

		String label;
		if (showAsPercent) {
			label = "Armor: " + Math.round(result.averagePercent) + "%";
		} else {
			label = "Armor: " + Math.round(result.averagePoints);
		}

		int color = pickColor(result);
		context.drawString(net.minecraft.client.Minecraft.getInstance().font, label, x, y, color, true);
		return net.minecraft.client.Minecraft.getInstance().font.lineHeight + 2;
	}

	private int pickColor(Result result) {
		double pct = result.averagePercent;
		if (pct >= 60) return 0xFF55FF55; // green
		if (pct >= 25) return 0xFFFFFF55; // yellow
		return 0xFFFF5555;                // red
	}

	private Result computeAverage(LocalPlayer player) {
		int pieces = 0;
		double totalPoints = 0;
		double totalPercent = 0;

		for (EquipmentSlot slot : ARMOR_SLOTS) {
			ItemStack stack = player.getItemBySlot(slot);
			if (stack.isEmpty() || stack.getMaxDamage() <= 0) {
				continue;
			}
			int max = stack.getMaxDamage();
			int remaining = max - stack.getDamageValue();
			pieces++;
			totalPoints += remaining;
			totalPercent += (remaining * 100.0) / max;
		}

		Result result = new Result();
		result.piecesWorn = pieces;
		if (pieces > 0) {
			result.averagePoints = totalPoints / pieces;
			result.averagePercent = totalPercent / pieces;
		}
		return result;
	}

	private static class Result {
		int piecesWorn = 0;
		double averagePoints = 0;
		double averagePercent = 0;
	}
}
