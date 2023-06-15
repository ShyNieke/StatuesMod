package com.shynieke.statues.client.screen;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class PlayerStatueData {
	public boolean small = false;
	public boolean locked = false;
	public boolean nameVisible = true;
	public boolean noGravity = false;
	public float yOffset = 0F;

	public float rotation = 0F;
	public String modelType = "AUTO";


	public enum MODEL_TYPE {
		AUTO,
		DEFAULT,
		SLIM
	}

	public final float[] pose = new float[3 * 7];

	public boolean isSmall() {
		return this.small;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean getNameVisible() {
		return nameVisible;
	}

	public boolean hasNoGravity() {
		return noGravity;
	}

	public void readFromNBT(CompoundTag compound) {
		this.small = compound.getBoolean("Small");
		this.locked = compound.getBoolean("Locked");
		this.nameVisible = compound.getBoolean("CustomNameVisible");
		this.noGravity = compound.getBoolean("NoGravity");
		this.yOffset = compound.getFloat("yOffset");
		this.modelType = compound.getString("Model");

		if (compound.contains("Rotation")) {
			this.rotation = compound.getList("Rotation", Tag.TAG_FLOAT).getFloat(0);
		}
		if (compound.contains("Pose")) {
			CompoundTag poseTag = (CompoundTag) compound.get("Pose");

			String[] keys = new String[]{"Head", "Body", "LeftLeg", "RightLeg", "LeftArm", "RightArm"};
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				if (poseTag.contains(key)) {
					ListTag tagList = poseTag.getList(key, Tag.TAG_FLOAT);
					for (int j = 0; j <= 2; j++) {
						int k = (i * 3) + j;
						this.pose[k] = tagList.getFloat(j);
					}
				}
			}
		}
	}

	public CompoundTag writeToNBT() {
		CompoundTag compound = new CompoundTag();
		compound.putBoolean("Small", this.small);
		compound.putBoolean("Locked", this.locked);
		compound.putBoolean("CustomNameVisible", this.nameVisible);
		compound.putBoolean("NoGravity", this.noGravity);
		compound.putFloat("yOffset", this.yOffset);
		compound.putString("Model", this.modelType);

		ListTag rotationTag = new ListTag();
		rotationTag.add(FloatTag.valueOf(this.rotation));
		compound.put("Rotation", rotationTag);

		CompoundTag poseTag = new CompoundTag();

		ListTag poseHeadTag = new ListTag();
		poseHeadTag.add(FloatTag.valueOf(this.pose[0]));
		poseHeadTag.add(FloatTag.valueOf(this.pose[1]));
		poseHeadTag.add(FloatTag.valueOf(this.pose[2]));
		poseTag.put("Head", poseHeadTag);

		ListTag poseBodyTag = new ListTag();
		poseBodyTag.add(FloatTag.valueOf(this.pose[3]));
		poseBodyTag.add(FloatTag.valueOf(this.pose[4]));
		poseBodyTag.add(FloatTag.valueOf(this.pose[5]));
		poseTag.put("Body", poseBodyTag);

		ListTag poseLeftLegTag = new ListTag();
		poseLeftLegTag.add(FloatTag.valueOf(this.pose[6]));
		poseLeftLegTag.add(FloatTag.valueOf(this.pose[7]));
		poseLeftLegTag.add(FloatTag.valueOf(this.pose[8]));
		poseTag.put("LeftLeg", poseLeftLegTag);

		ListTag poseRightLegTag = new ListTag();
		poseRightLegTag.add(FloatTag.valueOf(this.pose[9]));
		poseRightLegTag.add(FloatTag.valueOf(this.pose[10]));
		poseRightLegTag.add(FloatTag.valueOf(this.pose[11]));
		poseTag.put("RightLeg", poseRightLegTag);

		ListTag poseLeftArmTag = new ListTag();
		poseLeftArmTag.add(FloatTag.valueOf(this.pose[12]));
		poseLeftArmTag.add(FloatTag.valueOf(this.pose[13]));
		poseLeftArmTag.add(FloatTag.valueOf(this.pose[14]));
		poseTag.put("LeftArm", poseLeftArmTag);

		ListTag poseRightArmTag = new ListTag();
		poseRightArmTag.add(FloatTag.valueOf(this.pose[15]));
		poseRightArmTag.add(FloatTag.valueOf(this.pose[16]));
		poseRightArmTag.add(FloatTag.valueOf(this.pose[17]));
		poseTag.put("RightArm", poseRightArmTag);

		compound.put("Pose", poseTag);
		return compound;
	}
}
