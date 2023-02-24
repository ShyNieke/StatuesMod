package com.shynieke.statues.client.screen.widget;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;

public class EnumCycleButton<T extends Enum<T>> extends Button {

	private final String translationPrefix;
	private T value;

	private T[] options;

	public EnumCycleButton(int x, int y, int width, int height, String translationPrefix, T defaultValue, T[] options, OnPress onPress) {
		super(x, y, width, height, new TranslatableComponent("statues." + translationPrefix + "." + defaultValue), onPress);
		this.translationPrefix = translationPrefix;
		this.options = options;
		this.value = defaultValue;
	}

	public EnumCycleButton(int x, int y, int width, int height, String translationPrefix, String defaultValue, T[] options, OnPress onPress) {
		super(x, y, width, height, new TranslatableComponent("statues." + translationPrefix + "." + defaultValue), onPress);
		this.translationPrefix = translationPrefix;
		this.options = options;
		this.value = findValue(defaultValue);
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
		this.setMessage(new TranslatableComponent("statues." + translationPrefix + "." + value));
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
}
