package com.shynieke.statues.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.entity.PlayerStatueEntity;
import com.shynieke.statues.packets.PlayerStatueSyncMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class PlayerPoseScreen extends Screen {
    private final PlayerStatueEntity playerStatueEntity;
    private final PlayerStatueData playerStatueData;

    private final String[] buttonLabels = new String[] { "small", "rotation", "y_offset", "locked", "name_visible", "gravity" };
    private final String[] sliderLabels = new String[] { "head", "body", "left_leg", "right_leg", "left_arm", "right_arm" };

    private NumberFieldWidget rotationTextField;
    private DecimalNumberFieldWidget YOffsetTextField;
    private ToggleButton smallButton;
    private ToggleButton lockButton;
    private ToggleButton nameVisibleButton;
    private ToggleButton noGravityButton;
    private final NumberFieldWidget[] poseTextFields = new NumberFieldWidget[18];

    private Button doneButton;
    private Button cancelButton;

    public PlayerPoseScreen(PlayerStatueEntity playerStatue) {
        super(NarratorChatListener.NO_TITLE);

        this.playerStatueEntity = playerStatue;

        this.playerStatueData = new PlayerStatueData();
        this.playerStatueData.readNBT(playerStatueEntity.saveWithoutId(new CompoundNBT()));

        for (int i = 0; i < this.buttonLabels.length; i++)
            this.buttonLabels[i] = I18n.get(String.format("%s.playerstatue.gui.label." + this.buttonLabels[i], Reference.MOD_ID));
        for (int i = 0; i < this.sliderLabels.length; i++)
            this.sliderLabels[i] = I18n.get(String.format("%s.playerstatue.gui.label." + this.sliderLabels[i], Reference.MOD_ID));
    }

    public static void openScreen(PlayerStatueEntity playerStatue) {
        Minecraft.getInstance().setScreen(new PlayerPoseScreen(playerStatue));
    }

    @Override
    protected void init() {
        super.init();

        int offsetX = 110;
        int offsetY = 50;

        this.addButton(this.smallButton = new ToggleButton(offsetX, offsetY, 40, 20, this.playerStatueData.isSmall(), (button) -> {
            ToggleButton toggleButton = ((ToggleButton)button);
            toggleButton.setValue(!toggleButton.getValue());
            this.textFieldUpdated();
        }));
        this.addButton(this.lockButton = new ToggleButton(offsetX, offsetY + 66, 40, 20, this.playerStatueData.isLocked(), (button) -> {
            ToggleButton toggleButton = ((ToggleButton)button);
            toggleButton.setValue(!toggleButton.getValue());
            this.textFieldUpdated();
        }));
        this.addButton(this.nameVisibleButton = new ToggleButton(offsetX, offsetY + 89, 40, 20, this.playerStatueData.getNameVisible(), (button) -> {
            ToggleButton toggleButton = ((ToggleButton)button);
            toggleButton.setValue(!toggleButton.getValue());
            this.textFieldUpdated();
        }));
        this.addButton(this.noGravityButton = new ToggleButton(offsetX, offsetY + 112, 40, 20, this.playerStatueData.hasNoGravity(), (button) -> {
            ToggleButton toggleButton = ((ToggleButton)button);
            toggleButton.setValue(!toggleButton.getValue());
            this.textFieldUpdated();
        }));

        // rotation textbox
        this.rotationTextField = new NumberFieldWidget(this.font, 1 + offsetX, 1 + offsetY + (22), 38, 17, new StringTextComponent("field.rotation"));
        this.rotationTextField.setValue(String.valueOf((int)this.playerStatueData.rotation));
        this.rotationTextField.setMaxLength(3);
        this.addWidget(this.rotationTextField);

        // Y Offset textbox
        this.YOffsetTextField = new DecimalNumberFieldWidget(this.font, 1 + offsetX, 1 + offsetY + (44), 38, 17, new StringTextComponent("field.yOffset"));
        this.YOffsetTextField.setValue(String.valueOf((float) MathHelper.clamp(this.playerStatueData.yOffset, -1, 1)));
        this.YOffsetTextField.setMaxLength(5);
        this.addWidget(this.YOffsetTextField);

        // pose textboxes
        offsetX = this.width - 20 - 100;
        for (int i = 0; i < this.poseTextFields.length; i++) {
            int x = 1 + offsetX + ((i % 3) * 35);
            int y = 1 + offsetY + ((i / 3) * 22);
            int width = 28;
            int height = 17;
            String value = String.valueOf((int)this.playerStatueData.pose[i]);

            this.poseTextFields[i] = new NumberFieldWidget(this.font, x, y, width, height, new StringTextComponent(String.format("field.%s", i)));
            this.poseTextFields[i].setValue(value);
            this.poseTextFields[i].setMaxLength(3);
            this.addWidget(this.poseTextFields[i]);
        }

        offsetY = this.height / 4 + 120 + 12;

        // done & cancel buttons
        offsetX = this.width - 20;
        this.addButton(this.doneButton = new Button(offsetX - ((2 * 96) + 2), offsetY, 96, 20, new TranslationTextComponent("gui.done"), (button) -> {
            this.updateEntity(this.writeFieldsToNBT());
            this.minecraft.setScreen((Screen) null);
        }));
        this.addButton(this.cancelButton = new Button(offsetX - 96, offsetY, 96, 20, new TranslationTextComponent("gui.cancel"), (button) -> {
            this.updateEntity(this.playerStatueData.writeNBT());
            this.minecraft.setScreen((Screen) null);
        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        // gui title
        drawCenteredString(matrixStack, this.font, I18n.get(String.format("%s.playerstatue.gui.title", Reference.MOD_ID)), this.width / 2, 20, 0xFFFFFF);

        // textboxes
        this.rotationTextField.render(matrixStack, mouseX, mouseY, partialTicks);
        this.YOffsetTextField.render(matrixStack, mouseX, mouseY, partialTicks);
        for (NumberFieldWidget textField : this.poseTextFields)
            textField.render(matrixStack, mouseX, mouseY, partialTicks);

        int offsetY = 50;

        // left column labels
        int offsetX = 20;
        for (int i = 0; i < this.buttonLabels.length; i++) {
            int x = offsetX;
            int y = offsetY + (i * 22) + (10 - (this.font.lineHeight / 2));
            drawString(matrixStack, this.font, this.buttonLabels[i], x, y, 0xA0A0A0);
        }

        // right column labels
        offsetX = this.width - 20 - 100;
        // x, y, z
        drawString(matrixStack, this.font, "X", offsetX, 37, 0xA0A0A0);
        drawString(matrixStack, this.font, "Y", offsetX + (35), 37, 0xA0A0A0);
        drawString(matrixStack, this.font, "Z", offsetX + (2 * 35), 37, 0xA0A0A0);
        // pose textboxes
        for (int i = 0; i < this.sliderLabels.length; i++) {
            int x = offsetX - this.font.width(this.sliderLabels[i]) - 10;
            int y = offsetY + (i * 22) + (10 - (this.font.lineHeight / 2));
            drawString(matrixStack, this.font, this.sliderLabels[i], x, y, 0xA0A0A0);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        this.rotationTextField.tick();
        this.YOffsetTextField.tick();
        for (NumberFieldWidget textField : this.poseTextFields)
            textField.tick();
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        boolean typed = super.charTyped(codePoint, modifiers);
        if(typed) {
            this.textFieldUpdated();
        }
        return typed;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 15) { //Tab
            for (int i = 0; i < this.poseTextFields.length; i++) {
                if (this.poseTextFields[i].isFocused()) {
                    this.textFieldUpdated();
                    this.poseTextFields[i].moveCursorToEnd();
                    this.poseTextFields[i].setFocused(false);

                    int j = (!Screen.hasShiftDown() ? (i == this.poseTextFields.length - 1 ? 0 : i + 1) : (i == 0 ? this.poseTextFields.length - 1 : i - 1));
                    this.poseTextFields[j].setFocused(true);
                    this.poseTextFields[j].moveCursorTo(0);
                    this.poseTextFields[j].setHighlightPos(this.poseTextFields[j].getValue().length());
                }
            }
        } else {
            if (this.rotationTextField.keyPressed(keyCode, scanCode, modifiers)) {
                this.textFieldUpdated();
                return true;
            } else if (this.YOffsetTextField.keyPressed(keyCode, scanCode, modifiers)) {
                this.textFieldUpdated();
                return true;
            } else {
                for (NumberFieldWidget textField : this.poseTextFields) {
                    if (textField.keyPressed(keyCode, scanCode, modifiers)) {
                        this.textFieldUpdated();
                        return true;
                    }
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.rotationTextField.mouseClicked(mouseX, mouseY, button);
        this.YOffsetTextField.mouseClicked(mouseX, mouseY, button);
        for (NumberFieldWidget textField : this.poseTextFields) {
            textField.mouseClicked(mouseX, mouseY, button);
            this.textFieldUpdated();
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    protected void textFieldUpdated() {
        this.updateEntity(this.writeFieldsToNBT());
    }

    private CompoundNBT writeFieldsToNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.putBoolean("Small", this.smallButton.getValue());
        compound.putBoolean("Locked", this.lockButton.getValue());
        compound.putBoolean("CustomNameVisible", this.nameVisibleButton.getValue());
        compound.putBoolean("NoGravity", this.noGravityButton.getValue());
        compound.putDouble("yOffset", this.YOffsetTextField.getFloat());

        ListNBT rotationTag = new ListNBT();
        rotationTag.add(FloatNBT.valueOf(this.rotationTextField.getFloat()));
        compound.put("Rotation", rotationTag);

        CompoundNBT poseTag = new CompoundNBT();

        ListNBT poseHeadTag = new ListNBT();
        poseHeadTag.add(FloatNBT.valueOf(this.poseTextFields[0].getFloat()));
        poseHeadTag.add(FloatNBT.valueOf(this.poseTextFields[1].getFloat()));
        poseHeadTag.add(FloatNBT.valueOf(this.poseTextFields[2].getFloat()));
        poseTag.put("Head", poseHeadTag);

        ListNBT poseBodyTag = new ListNBT();
        poseBodyTag.add(FloatNBT.valueOf(this.poseTextFields[3].getFloat()));
        poseBodyTag.add(FloatNBT.valueOf(this.poseTextFields[4].getFloat()));
        poseBodyTag.add(FloatNBT.valueOf(this.poseTextFields[5].getFloat()));
        poseTag.put("Body", poseBodyTag);

        ListNBT poseLeftLegTag = new ListNBT();
        poseLeftLegTag.add(FloatNBT.valueOf(this.poseTextFields[6].getFloat()));
        poseLeftLegTag.add(FloatNBT.valueOf(this.poseTextFields[7].getFloat()));
        poseLeftLegTag.add(FloatNBT.valueOf(this.poseTextFields[8].getFloat()));
        poseTag.put("LeftLeg", poseLeftLegTag);

        ListNBT poseRightLegTag = new ListNBT();
        poseRightLegTag.add(FloatNBT.valueOf(this.poseTextFields[9].getFloat()));
        poseRightLegTag.add(FloatNBT.valueOf(this.poseTextFields[10].getFloat()));
        poseRightLegTag.add(FloatNBT.valueOf(this.poseTextFields[11].getFloat()));
        poseTag.put("RightLeg", poseRightLegTag);

        ListNBT poseLeftArmTag = new ListNBT();
        poseLeftArmTag.add(FloatNBT.valueOf(this.poseTextFields[12].getFloat()));
        poseLeftArmTag.add(FloatNBT.valueOf(this.poseTextFields[13].getFloat()));
        poseLeftArmTag.add(FloatNBT.valueOf(this.poseTextFields[14].getFloat()));
        poseTag.put("LeftArm", poseLeftArmTag);

        ListNBT poseRightArmTag = new ListNBT();
        poseRightArmTag.add(FloatNBT.valueOf(this.poseTextFields[15].getFloat()));
        poseRightArmTag.add(FloatNBT.valueOf(this.poseTextFields[16].getFloat()));
        poseRightArmTag.add(FloatNBT.valueOf(this.poseTextFields[17].getFloat()));
        poseTag.put("RightArm", poseRightArmTag);

        compound.put("Pose", poseTag);
        return compound;
    }
    
    private void updateEntity(CompoundNBT compound) {
        CompoundNBT CompoundNBT = this.playerStatueEntity.saveWithoutId(new CompoundNBT()).copy();
        CompoundNBT.merge(compound);
        this.playerStatueEntity.load(CompoundNBT);

        Statues.CHANNEL.send(PacketDistributor.SERVER.noArg(), new PlayerStatueSyncMessage(playerStatueEntity.getUUID(), compound));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
