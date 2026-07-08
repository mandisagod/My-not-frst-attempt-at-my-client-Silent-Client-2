package com.silentclient.client.module.impl;

import com.silentclient.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

/**
 * Lists your active potion effects with their remaining time, e.g.
 * "Speed II - 1:24". Vanilla already shows small icons in the corner of
 * the inventory screen; this puts readable text on the main HUD too.
 */
public class PotionTimersModule extends Module {

	public PotionTimersModule() {
		super("Potion Timers", "Lists active potion effects and time remaining.", Category.HUD, false);
	}

	/**
	 * Draws one line per active effect starting at (x, y) and returns the
	 * total height used, so the HUD renderer can stack whatever comes next.
	 */
	public int render(GuiGraphics context, LocalPlayer player, int x, int y, int textColor) {
		int lineHeight = Minecraft.getInstance().font.lineHeight + 2;
		int drawnLines = 0;

		for (MobEffectInstance effect : player.getActiveEffects()) {
			Component name = effect.getEffect().value().getDisplayName();
			String amplifier = effect.getAmplifier() > 0 ? " " + toRoman(effect.getAmplifier() + 1) : "";
			String time = formatTicks(effect.getDuration());
			String label = name.getString() + amplifier + " - " + time;

			context.drawString(Minecraft.getInstance().font, label, x, y + drawnLines * lineHeight, textColor, true);
			drawnLines++;
		}

		return drawnLines * lineHeight;
	}

	private String formatTicks(int ticks) {
		int totalSeconds = ticks / 20;
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}

	private String toRoman(int number) {
		return switch (number) {
			case 1 -> "I";
			case 2 -> "II";
			case 3 -> "III";
			case 4 -> "IV";
			case 5 -> "V";
			default -> String.valueOf(number);
		};
	}
}
