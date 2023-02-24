package com.shynieke.statues.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shynieke.statues.Reference;
import com.shynieke.statues.client.screen.widget.EnumCycleButton;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.packets.PlayerStatueSyncMessage;
import com.shynieke.statues.packets.StatuesNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class PlayerPoseScreen extends Screen {
	private final PlayerStatue playerStatueEntity;
	private final PlayerStatueData playerStatueData;

	private final String[] buttonLabels = new String[]{"small", "rotation", "y_offset", "locked", "name_visible", "gravity", "model_type"};
	private final String[] sliderLabels = new String[]{"head", "body", "left_leg", "right_leg", "left_arm", "right_arm", "position"};

	private NumberFieldBox rotationTextField;
	private DecimalNumberFieldBox YOffsetTextField;
	private ToggleButton smallButton;
	private ToggleButton lockButton;
	private ToggleButton nameVisibleButton;
	private ToggleButton noGravityButton;
	private EnumCycleButton forceModelType;
	private final NumberFieldBox[] poseTextFields = new NumberFieldBox[3 * 7];
	private final boolean allowScrolling;

	private Vec3 lastSendOffset = new Vec3(0, 0, 0);

	public PlayerPoseScreen(PlayerStatue playerStatue) {
		super(NarratorChatListener.NO_TITLE);

		this.playerStatueEntity = playerStatue;

		this.playerStatueData = new PlayerStatueData();
		this.playerStatueData.readFromNBT(playerStatueEntity.saveWithoutId(new CompoundTag()));

		this.allowScrolling = StatuesConfig.CLIENT.allowScrolling.get();

		for (int i = 0; i < this.buttonLabels.length; i++)
			this.buttonLabels[i] = I18n.get(String.format("%s.playerstatue.gui.label." + this.buttonLabels[i], Reference.MOD_ID));
		for (int i = 0; i < this.sliderLabels.length; i++)
			this.sliderLabels[i] = I18n.get(String.format("%s.playerstatue.gui.label." + this.sliderLabels[i], Reference.MOD_ID));
	}

	public static void openScreen(PlayerStatue playerStatue) {
		Minecraft.getInstance().setScreen(new PlayerPoseScreen(playerStatue));
	}

	@Override
	protected void init() {
		super.init();

		int offsetX = 110;
		int offsetY = 50;

		int rowOffset = 22;

		this.addRenderableWidget(this.smallButton = new ToggleButton(offsetX, offsetY, 40, 20, this.playerStatueData.isSmall(), (button) -> {
			ToggleButton toggleButton = ((ToggleButton) button);
			toggleButton.setValue(!toggleButton.getValue());
			this.textFieldUpdated();
		}));

		// rotation textbox
		this.rotationTextField = new NumberFieldBox(this.font, 1 + offsetX, 1 + offsetY + rowOffset, 38, 17, new TranslatableComponent("statues.playerstatue.gui.label.rotation"));
		this.rotationTextField.setValue(String.valueOf((int) this.playerStatueData.rotation));
		this.rotationTextField.setMaxLength(4);
		this.addWidget(this.rotationTextField);

		// Y Offset textbox
		this.YOffsetTextField = new DecimalNumberFieldBox(this.font, 1 + offsetX, 1 + offsetY + rowOffset * 2, 38, 17, new TranslatableComponent("statues.playerstatue.gui.label.y_offset"));
		this.YOffsetTextField.setValue(String.valueOf((float) Mth.clamp(this.playerStatueData.yOffset, -1, 1)));
		this.YOffsetTextField.setMaxLength(5);
		this.addWidget(this.YOffsetTextField);

		this.addRenderableWidget(this.lockButton = this.addRenderableWidget(new ToggleButton(offsetX, offsetY + rowOffset * 3, 40, 20, this.playerStatueData.isLocked(), (button) -> {
			ToggleButton toggleButton = ((ToggleButton) button);
			toggleButton.setValue(!toggleButton.getValue());
			this.textFieldUpdated();
		})));
		this.addRenderableWidget(this.nameVisibleButton = new ToggleButton(offsetX, offsetY + rowOffset * 4, 40, 20, this.playerStatueData.getNameVisible(), (button) -> {
			ToggleButton toggleButton = ((ToggleButton) button);
			toggleButton.setValue(!toggleButton.getValue());
			this.textFieldUpdated();
		}));
		this.addRenderableWidget(this.noGravityButton = new ToggleButton(offsetX, offsetY + rowOffset * 5, 40, 20, this.playerStatueData.hasNoGravity(), (button) -> {
			ToggleButton toggleButton = ((ToggleButton) button);
			toggleButton.setValue(!toggleButton.getValue());
			this.textFieldUpdated();
		}));
		this.addRenderableWidget(this.forceModelType = new EnumCycleButton(offsetX, offsetY + rowOffset * 6, 40, 20, "modeltype", this.playerStatueData.modelType, PlayerStatueData.MODEL_TYPE.values(), (button) -> {
			EnumCycleButton optionCycleButton = (EnumCycleButton) button;
			optionCycleButton.cycleValue();
			this.textFieldUpdated();
		}));

		// pose textboxes
		offsetX = this.width - 20 - 100;
		for (int i = 0; i < this.poseTextFields.length; i++) {
			int x = 1 + offsetX + ((i % 3) * 35);
			int y = 1 + offsetY + ((i / 3) * 22);
			int width = 28;
			int height = 17;
			String value = String.valueOf((int) this.playerStatueData.pose[i]);

			this.poseTextFields[i] = new NumberFieldBox(this.font, x, y, width, height, new TranslatableComponent(String.format("statues.playerstatue.gui.label.field.%s", i)));
			this.poseTextFields[i].setValue(value);
			this.poseTextFields[i].setMaxLength(4);
			if (i >= 3 * 6 && i < 3 * 7) {
				this.poseTextFields[i].scrollMultiplier = 0.01f;
				this.poseTextFields[i].modValue = Integer.MAX_VALUE;
				this.poseTextFields[i].decimalPoints = 2;
				this.poseTextFields[i].setMaxLength(6);
			}
			this.addWidget(this.poseTextFields[i]);
		}

		// copy & paste buttons
		offsetX = 20;
		offsetY = this.height / 4 + 134 + 12;

		this.addRenderableWidget(new Button(offsetX, offsetY, 64, 20, new TranslatableComponent("statues.playerstatue.gui.label.copy"), (button) -> {
			CompoundTag compound = this.writeFieldsToNBT();
			String clipboardData = compound.toString();
			if (this.minecraft != null) {
				this.minecraft.keyboardHandler.setClipboard(clipboardData);
			}
		}));

		this.addRenderableWidget(new Button(offsetX + 66, offsetY, 64, 20, new TranslatableComponent("statues.playerstatue.gui.label.paste"), (button) -> {
			try {
				String clipboardData = null;
				if (this.minecraft != null) {
					clipboardData = this.minecraft.keyboardHandler.getClipboard();
				}
				CompoundTag compound = TagParser.parseTag(clipboardData);
				this.readFieldsFromNBT(compound);
				this.updateEntity(compound);
			} catch (Exception e) {
				//Nope
			}
		}));

		// done & cancel buttons
		offsetX = this.width - 20;
		this.addRenderableWidget(new Button(offsetX - ((2 * 96) + 2), offsetY, 96, 20, new TranslatableComponent("gui.done"), (button) -> {
			this.updateEntity(this.writeFieldsToNBT());
			this.minecraft.setScreen((Screen) null);
		}));
		this.addRenderableWidget(new Button(offsetX - 96, offsetY, 96, 20, new TranslatableComponent("gui.cancel"), (button) -> {
			this.poseTextFields[18].setValue("0");
			this.poseTextFields[19].setValue("0");
			this.poseTextFields[20].setValue("0");
			this.textFieldUpdated();
			this.updateEntity(this.playerStatueData.writeToNBT());
			this.minecraft.setScreen((Screen) null);
		}));
	}

	@Override
	public void onClose() {
		super.onClose();
		this.playerStatueEntity.clientLock = 0;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);

		// Draw gui title
		drawCenteredString(poseStack, this.font, I18n.get(String.format("%s.playerstatue.gui.title", Reference.MOD_ID)), this.width / 2, 20, 0xFFFFFF);

		// Draw textboxes
		this.rotationTextField.render(poseStack, mouseX, mouseY, partialTicks);
		this.YOffsetTextField.render(poseStack, mouseX, mouseY, partialTicks);
		for (NumberFieldBox textField : this.poseTextFields)
			if (textField != null) {
				textField.render(poseStack, mouseX, mouseY, partialTicks);
			}

		int offsetY = 50;

		// left column labels
		int offsetX = 20;
		for (int i = 0; i < this.buttonLabels.length; i++) {
			int x = offsetX;
			int y = offsetY + (i * 22) + (11 - (this.font.lineHeight / 2));
			drawString(poseStack, this.font, this.buttonLabels[i], x, y, 0xA0A0A0);
		}

		// right column labels
		offsetX = this.width - 20 - 100;
		// x, y, z
		drawString(poseStack, this.font, "X", offsetX, 37, 0xA0A0A0);
		drawString(poseStack, this.font, "Y", offsetX + (35), 37, 0xA0A0A0);
		drawString(poseStack, this.font, "Z", offsetX + (2 * 35), 37, 0xA0A0A0);
		// pose textboxes
		for (int i = 0; i < this.sliderLabels.length; i++) {
			int x = offsetX - this.font.width(this.sliderLabels[i]) - 10;
			int y = offsetY + (i * 22) + (10 - (this.font.lineHeight / 2));
			drawString(poseStack, this.font, this.sliderLabels[i], x, y, 0xA0A0A0);
		}

		super.render(poseStack, mouseX, mouseY, partialTicks);
	}

	@Override
	public void tick() {
		super.tick();
		this.playerStatueEntity.clientLock = 5;
		this.rotationTextField.tick();
		this.YOffsetTextField.tick();
		for (NumberFieldBox textField : this.poseTextFields)
			if (textField != null) {
				textField.tick();
			}
	}

	@Override
	public boolean charTyped(char codePoint, int modifiers) {
		boolean typed = super.charTyped(codePoint, modifiers);
		if (typed) {
			this.textFieldUpdated();
		}
		return typed;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		var multiplier = Screen.hasShiftDown() ? 10.0f : 1.0f;
		if (allowScrolling && delta > 0) {
			//Add 1 to the value
			if (rotationTextField.isFocused()) {
				float nextValue = (rotationTextField.getFloat() + multiplier * rotationTextField.scrollMultiplier) % rotationTextField.modValue;
				rotationTextField.setValue(String.valueOf(nextValue));
				rotationTextField.setCursorPosition(0);
				rotationTextField.setHighlightPos(0);
				this.textFieldUpdated();
				return true;
			}
			for (NumberFieldBox textField : this.poseTextFields) {
				if (textField.isHoveredOrFocused()) {
					float nextValue = (textField.getFloat() + multiplier * textField.scrollMultiplier) % textField.modValue;
					textField.setValue(String.valueOf(nextValue));
					textField.setCursorPosition(0);
					textField.setHighlightPos(0);
					this.textFieldUpdated();
					return true;
				}
			}
		} else if (allowScrolling && delta < 0) {
			//Remove 1 to the value
			if (rotationTextField.isFocused()) {
				float previousValue = (rotationTextField.getFloat() - multiplier * rotationTextField.scrollMultiplier) % rotationTextField.modValue;
				rotationTextField.setValue(String.valueOf(previousValue));
				rotationTextField.setCursorPosition(0);
				rotationTextField.setHighlightPos(0);
				this.textFieldUpdated();
				return true;
			}
			for (NumberFieldBox textField : this.poseTextFields) {
				if (textField.isHoveredOrFocused()) {
					float previousValue = (textField.getFloat() - multiplier * textField.scrollMultiplier) % textField.modValue;
					textField.setValue(String.valueOf(previousValue));
					textField.setCursorPosition(0);
					textField.setHighlightPos(0);
					this.textFieldUpdated();
					return true;
				}
			}
		}
		return super.mouseScrolled(mouseX, mouseY, delta);
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
				for (NumberFieldBox textField : this.poseTextFields) {
					if (textField.keyPressed(keyCode, scanCode, modifiers)) {
						return true;
					}
				}
				this.textFieldUpdated();
			}
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.rotationTextField.mouseClicked(mouseX, mouseY, button);
		this.YOffsetTextField.mouseClicked(mouseX, mouseY, button);
		for (NumberFieldBox textField : this.poseTextFields) {
			textField.mouseClicked(mouseX, mouseY, button);
		}
		this.textFieldUpdated();

		return super.mouseClicked(mouseX, mouseY, button);
	}

	protected void textFieldUpdated() {
		this.updateEntity(this.writeFieldsToNBT());
	}

	private CompoundTag writeFieldsToNBT() {
		CompoundTag compound = new CompoundTag();
		compound.putBoolean("Small", this.smallButton.getValue());
		compound.putBoolean("Locked", this.lockButton.getValue());
		compound.putBoolean("CustomNameVisible", this.nameVisibleButton.getValue());
		compound.putBoolean("NoGravity", this.noGravityButton.getValue());
		compound.putDouble("yOffset", this.YOffsetTextField.getFloat());
		compound.putString("Model", this.forceModelType.getValue().name());

		ListTag rotationTag = new ListTag();
		rotationTag.add(FloatTag.valueOf(this.rotationTextField.getFloat()));
		compound.put("Rotation", rotationTag);

		CompoundTag poseTag = new CompoundTag();

		ListTag poseHeadTag = new ListTag();
		poseHeadTag.add(FloatTag.valueOf(this.poseTextFields[0].getFloat()));
		poseHeadTag.add(FloatTag.valueOf(this.poseTextFields[1].getFloat()));
		poseHeadTag.add(FloatTag.valueOf(this.poseTextFields[2].getFloat()));
		poseTag.put("Head", poseHeadTag);

		ListTag poseBodyTag = new ListTag();
		poseBodyTag.add(FloatTag.valueOf(this.poseTextFields[3].getFloat()));
		poseBodyTag.add(FloatTag.valueOf(this.poseTextFields[4].getFloat()));
		poseBodyTag.add(FloatTag.valueOf(this.poseTextFields[5].getFloat()));
		poseTag.put("Body", poseBodyTag);

		ListTag poseLeftLegTag = new ListTag();
		poseLeftLegTag.add(FloatTag.valueOf(this.poseTextFields[6].getFloat()));
		poseLeftLegTag.add(FloatTag.valueOf(this.poseTextFields[7].getFloat()));
		poseLeftLegTag.add(FloatTag.valueOf(this.poseTextFields[8].getFloat()));
		poseTag.put("LeftLeg", poseLeftLegTag);

		ListTag poseRightLegTag = new ListTag();
		poseRightLegTag.add(FloatTag.valueOf(this.poseTextFields[9].getFloat()));
		poseRightLegTag.add(FloatTag.valueOf(this.poseTextFields[10].getFloat()));
		poseRightLegTag.add(FloatTag.valueOf(this.poseTextFields[11].getFloat()));
		poseTag.put("RightLeg", poseRightLegTag);

		ListTag poseLeftArmTag = new ListTag();
		poseLeftArmTag.add(FloatTag.valueOf(this.poseTextFields[12].getFloat()));
		poseLeftArmTag.add(FloatTag.valueOf(this.poseTextFields[13].getFloat()));
		poseLeftArmTag.add(FloatTag.valueOf(this.poseTextFields[14].getFloat()));
		poseTag.put("LeftArm", poseLeftArmTag);

		ListTag poseRightArmTag = new ListTag();
		poseRightArmTag.add(FloatTag.valueOf(this.poseTextFields[15].getFloat()));
		poseRightArmTag.add(FloatTag.valueOf(this.poseTextFields[16].getFloat()));
		poseRightArmTag.add(FloatTag.valueOf(this.poseTextFields[17].getFloat()));
		poseTag.put("RightArm", poseRightArmTag);

		var offsetX = this.poseTextFields[18].getFloat();
		var offsetY = this.poseTextFields[19].getFloat();
		var offsetZ = this.poseTextFields[20].getFloat();
		var offsetXDiff = offsetX - this.lastSendOffset.x;
		var offsetYDiff = offsetY - this.lastSendOffset.y;
		var offsetZDiff = offsetZ - this.lastSendOffset.z;
		ListTag positionOffset = new ListTag();
		positionOffset.add(DoubleTag.valueOf(offsetXDiff));
		positionOffset.add(DoubleTag.valueOf(offsetYDiff));
		positionOffset.add(DoubleTag.valueOf(offsetZDiff));
		compound.put("Move", positionOffset);
		this.lastSendOffset = new Vec3(offsetX, offsetY, offsetZ);

		compound.put("Pose", poseTag);
		return compound;
	}

	private void readFieldsFromNBT(CompoundTag compound) {
		PlayerStatueData statueData = new PlayerStatueData();
		statueData.readFromNBT(compound);

		this.smallButton.setValue(statueData.small);
		this.lockButton.setValue(statueData.locked);
		this.nameVisibleButton.setValue(statueData.nameVisible);
		this.noGravityButton.setValue(statueData.noGravity);

		this.YOffsetTextField.setValue(String.valueOf((double) statueData.yOffset));
		this.rotationTextField.setValue(String.valueOf((int) statueData.rotation));
		this.forceModelType.setValue(this.forceModelType.findValue(statueData.modelType));

		for (int i = 0; i < this.poseTextFields.length; i++) {
			this.poseTextFields[i].setValue(String.valueOf((int) statueData.pose[i]));
		}
	}

	private void updateEntity(CompoundTag compound) {
		CompoundTag CompoundTag = this.playerStatueEntity.saveWithoutId(new CompoundTag()).copy();
		CompoundTag.merge(compound);
		this.playerStatueEntity.clientLock = 0;
		this.playerStatueEntity.load(CompoundTag);
		this.playerStatueEntity.clientLock = 5;

		StatuesNetworking.CHANNEL.send(PacketDistributor.SERVER.noArg(), new PlayerStatueSyncMessage(playerStatueEntity.getUUID(), compound));
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
