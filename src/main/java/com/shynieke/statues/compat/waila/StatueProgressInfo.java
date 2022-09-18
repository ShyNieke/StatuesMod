package com.shynieke.statues.compat.waila;

import net.minecraft.util.math.BlockPos;

public class StatueProgressInfo {

	private int cooldown;
	private int cooldownMax;
	private boolean able;
	private BlockPos position;

	public StatueProgressInfo(int c, int cm, boolean a, BlockPos p) {
		this.cooldown = c;
		this.cooldownMax = cm;
		this.able = a;
		this.position = p;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldownMax(int cooldownMax) {
		this.cooldownMax = cooldownMax;
	}

	public int getCooldownMax() {
		return cooldownMax;
	}

	public void setAble(boolean able) {
		this.able = able;
	}

	public boolean isAble() {
		return able;
	}

	public BlockPos getPosition() {
		return position;
	}
}
