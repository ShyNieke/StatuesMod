package com.shynieke.statues.client.screen.widget;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;

public class ToggleButton extends Button {

	private boolean value;

	public ToggleButton(int x, int y, int width, int height, boolean defaultValue, OnPress onPress, CreateNarration createNarration) {
		super(x, y, width, height, defaultValue ? Component.translatable("gui.yes") : Component.translatable("gui.no"), onPress, createNarration);
		this.value = defaultValue;
	}

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		this.value = value;
		this.setMessage(value ? Component.translatable("gui.yes") : Component.translatable("gui.no"));
	}

	public static class Builder {
		private final boolean defaultValue;
		private final OnPress onPress;
		@Nullable
		private Tooltip tooltip;
		private int x;
		private int y;
		private int width = 150;
		private int height = 20;
		private CreateNarration createNarration = Button.DEFAULT_NARRATION;

		public Builder(boolean defaultValue, OnPress onPress) {
			this.defaultValue = defaultValue;
			this.onPress = onPress;
		}

		public Builder pos(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public Builder width(int width) {
			this.width = width;
			return this;
		}

		public Builder size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public Builder bounds(int p_254166_, int p_253872_, int p_254522_, int p_253985_) {
			return this.pos(p_254166_, p_253872_).size(p_254522_, p_253985_);
		}

		public Builder tooltip(@Nullable Tooltip tooltip) {
			this.tooltip = tooltip;
			return this;
		}

		public Builder createNarration(CreateNarration createNarration) {
			this.createNarration = createNarration;
			return this;
		}

		public ToggleButton build() {
			ToggleButton button = new ToggleButton(this.x, this.y, this.width, this.height, this.defaultValue, this.onPress, this.createNarration);
			button.setTooltip(this.tooltip);
			return button;
		}
	}
}
