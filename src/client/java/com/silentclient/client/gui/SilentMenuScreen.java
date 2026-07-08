package com.silentclient.client.gui;

import com.silentclient.client.SilentClient;
import com.silentclient.client.module.Module;
import com.silentclient.client.module.ModuleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The "Silent" mod menu. Opens over whatever screen (or in-game HUD) you
 * were looking at, lists every registered module with a toggle button, and
 * saves your choices back to disk when closed.
 */
public class SilentMenuScreen extends Screen {

	private static final int PANEL_WIDTH = 260;
	private static final int ROW_HEIGHT = 24;
	private static final int TOP_PADDING = 40;

	private final Screen previousScreen;
	private final List<Button> moduleButtons = new ArrayList<>();

	public SilentMenuScreen(Screen previousScreen) {
		super(Component.literal("Silent"));
		this.previousScreen = previousScreen;
	}

	@Override
	protected void init() {
		moduleButtons.clear();

		int panelX = (this.width - PANEL_WIDTH) / 2;
		int rowY = TOP_PADDING;

		for (Module module : ModuleRegistry.getModules()) {
			Button button = this.addRenderableWidget(Button.builder(buttonLabel(module), b -> onModuleToggled(module, b))
					.pos(panelX, rowY)
					.size(PANEL_WIDTH, ROW_HEIGHT - 4)
					.tooltip(Tooltip.create(Component.literal(module.getDescription())))
					.build());
			moduleButtons.add(button);
			rowY += ROW_HEIGHT;
		}

		this.addRenderableWidget(Button.builder(Component.literal("Done"), b -> this.onClose())
				.pos(panelX, rowY + 8)
				.size(PANEL_WIDTH, 20)
				.build());
	}

	private void onModuleToggled(Module module, Button button) {
		module.toggle();
		button.setMessage(buttonLabel(module));
	}

	private Component buttonLabel(Module module) {
		ChatFormatting stateColor = module.isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED;
		String state = module.isEnabled() ? "ON" : "OFF";
		return Component.literal(module.getName() + "  ")
				.append(Component.literal("[" + state + "]").withStyle(stateColor));
	}

	@Override
	public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, mouseX, mouseY, delta);

		Component title = Component.literal("Silent").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD);
		context.drawCenteredString(this.font, title, this.width / 2, 14, 0xFFFFFFFF);

		super.render(context, mouseX, mouseY, delta);
	}

	@Override
	public void onClose() {
		// Persist the toggles the moment the menu closes.
		SilentClient.getConfig().save();
		if (this.minecraft != null) {
			this.minecraft.setScreen(previousScreen);
		}
	}

	@Override
	public boolean isPauseScreen() {
		// Don't freeze single-player worlds just for opening the menu -
		// matches how Lunar Client's menu behaves.
		return false;
	}
}
