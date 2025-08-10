package com.shynieke.statues.client.screen.widget;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class EnumCycleButton<T extends Enum<T>> extends Button {

	private final String translationPrefix;
	private T value;

	private final T[] options;

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
		for (T option : options) {
			if (option.name().equals(name)) {
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
		for (T option : options) {
			if (option.ordinal() == value.ordinal() + 1) {
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
			for (T option : options) {
				if (option.name().equals(name)) {
					return option;
				}
			}
			return options[0];
		}

		public Builder<T> pos(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public Builder<T> width(int width) {
			this.width = width;
			return this;
		}

		public Builder<T> size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public Builder<T> bounds(int p_254166_, int p_253872_, int p_254522_, int p_253985_) {
			return this.pos(p_254166_, p_253872_).size(p_254522_, p_253985_);
		}

		public Builder<T> tooltip(@Nullable Tooltip tooltip) {
			this.tooltip = tooltip;
			return this;
		}

		public Builder<T> createNarration(CreateNarration createNarration) {
			this.createNarration = createNarration;
			return this;
		}

		public EnumCycleButton<T> build() {
			EnumCycleButton<T> button = new EnumCycleButton<T>(this.x, this.y, this.width, this.height, translationPrefix, this.defaultValue, this.options, this.onPress, this.createNarration);
			button.setTooltip(this.tooltip);
			return button;
		}
	}
}
