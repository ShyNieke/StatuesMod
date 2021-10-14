package com.shynieke.statues.client.screen;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

public class DecimalNumberFieldWidget extends NumberFieldWidget {

    public DecimalNumberFieldWidget(FontRenderer font, int x, int y, int width, int height, ITextComponent defaultValue) {
        super(font, x, y, width, height, defaultValue);
    }

    @Override
    protected boolean isNumeric(String value) {
        return value.equals(".") || super.isNumeric(value);
    }
}
