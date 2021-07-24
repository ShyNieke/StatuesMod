package com.shynieke.statues.client.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class DecimalNumberFieldBox extends NumberFieldBox {

    public DecimalNumberFieldBox(Font font, int x, int y, int width, int height, Component defaultValue) {
        super(font, x, y, width, height, defaultValue);
    }

    @Override
    protected boolean isNumeric(String value) {
        return value.equals(".") || super.isNumeric(value);
    }
}
