package com.shynieke.statues.client.screen;

import com.shynieke.statues.Statues;
import net.minecraft.core.Rotations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.phys.Vec2;

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
		this.small = compound.getBooleanOr("Small", false);
		this.locked = compound.getBooleanOr("Locked", false);
		this.nameVisible = compound.getBooleanOr("CustomNameVisible", false);
		this.noGravity = compound.getBooleanOr("NoGravity", false);
		this.yOffset = compound.getFloatOr("yOffset", 0.0F);
		this.modelType = compound.getStringOr("Model", "AUTO");

		if (compound.contains("Rotation")) {
			compound.store("Rotation", Vec2.CODEC, new Vec2(this.rotation, 0));
		}
		if (compound.contains("Pose")) {
			CompoundTag poseTag = compound.getCompoundOrEmpty("Pose");
			if (poseTag.isEmpty()) {
				Statues.LOGGER.warn("Pose tag is empty, skipping pose data");
				return;
			}

			String[] keys = new String[]{"Head", "Body", "LeftLeg", "RightLeg", "LeftArm", "RightArm"};
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				if (poseTag.contains(key)) {
					Rotations rotations = poseTag.read(key, Rotations.CODEC).orElse(new Rotations(0, 0, 0));
					this.pose[i * 3] = rotations.x();
					this.pose[(i * 3) + 1] = rotations.y();
					this.pose[(i * 3) + 2] = rotations.z();
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
