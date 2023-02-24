package com.shynieke.statues.client.screen.widget;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class EnumCycleButton<T extends Enum<T>> extends Button {

	private final String translationPrefix;
	private T value;

	private T[] options;

	public EnumCycleButton(int x, int y, int width, int height, String translationPrefix, T defaultValue, T[] options, OnPress onPress, CreateNarration createNarration) {
		super(x, y, width, height, Component.translatable("statues." + translationPrefix + "." + defaultValue), onPress, createNarration);
		this.value = defaultValue;
		this.options = options;
		this.translationPrefix = translationPrefix;
	}

	public T getValue() {
		return this.value;
	}

	public T findValue(String name) {
		for(T option : options) {
			if(option.name().equals(name)) {
				return option;
			}
		}
		return options[0];
	}

	public void setValue(T value) {
		this.value = value;
		this.setMessage(Component.translatable("statues." + translationPrefix + "." + value));
	}

	private T nextOption(T value) {
		for(T option : options) {
			if(option.ordinal() == value.ordinal() + 1) {
				return option;
			}
		}
		return options[0];
	}

	public void cycleValue() {
		this.setValue(nextOption(this.value));
	}

	public static class Builder<T extends Enum<T>> {
		private final T defaultValue;
		private final String translationPrefix;
		private final T[] options;
		private final OnPress onPress;
		@Nullable
		private Tooltip tooltip;
		private int x;
		private int y;
		private int width = 150;
		private int height = 20;
		private CreateNarration createNarration = Button.DEFAULT_NARRATION;

		public Builder(String defaultValue, String translationPrefix, T[] options, OnPress onPress) {
			this.translationPrefix = translationPrefix;
			this.onPress = onPress;
			this.options = options;
			this.defaultValue = findValue(defaultValue);
		}

		public T findValue(String name) {
			for(T option : options) {
				if(option.name().equals(name)) {
					return option;
				}
			}
			return options[0];
		}

		public EnumCycleButton.Builder pos(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public EnumCycleButton.Builder width(int width) {
			this.width = width;
			return this;
		}

		public EnumCycleButton.Builder size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public EnumCycleButton.Builder bounds(int p_254166_, int p_253872_, int p_254522_, int p_253985_) {
			return this.pos(p_254166_, p_253872_).size(p_254522_, p_253985_);
		}

		public EnumCycleButton.Builder tooltip(@Nullable Tooltip tooltip) {
			this.tooltip = tooltip;
			return this;
		}

		public EnumCycleButton.Builder createNarration(CreateNarration createNarration) {
			this.createNarration = createNarration;
			return this;
		}

		public EnumCycleButton build() {
			EnumCycleButton button = new EnumCycleButton(this.x, this.y, this.width, this.height, translationPrefix, this.defaultValue, this.options, this.onPress, this.createNarration);
			button.setTooltip(this.tooltip);
			return button;
		}
	}
}
