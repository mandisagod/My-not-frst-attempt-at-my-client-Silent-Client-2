package com.silentclient.client.module;

/**
 * A single toggleable feature inside the Silent Client menu (e.g. a HUD
 * element or a cosmetic tweak). Modules are intentionally simple: they know
 * how to turn themselves on/off and persist that choice. All actual
 * rendering/behavior hooks are registered once, in {@code SilentClient},
 * and check {@link #isEnabled()} before doing anything - this keeps every
 * module's runtime footprint at "read one boolean" when it's off.
 */
public abstract class Module {

	private final String name;
	private final String description;
	private final Category category;
	private boolean enabled;

	protected Module(String name, String description, Category category, boolean enabledByDefault) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.enabled = enabledByDefault;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void toggle() {
		setEnabled(!enabled);
	}

	/**
	 * Broad grouping used to organize the Silent menu into tabs/sections.
	 */
	public enum Category {
		HUD,
		VISUAL,
		QOL
	}
}
