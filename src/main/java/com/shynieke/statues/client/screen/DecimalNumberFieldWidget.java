package com.shynieke.statues.client.screen;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.math.NumberUtils;

public class DecimalNumberFieldWidget extends NumberFieldWidget {

    public DecimalNumberFieldWidget(FontRenderer font, int x, int y, int width, int height, ITextComponent defaultValue) {
        super(font, x, y, width, height, defaultValue);
    }

    @Override
    protected boolean isNumeric(String value) {
        return value.equals(".") || super.isNumeric(value);
    }
}
