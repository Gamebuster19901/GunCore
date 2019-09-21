/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.item.abstracts;

import java.util.Random;

import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;

import net.minecraft.nbt.CompoundNBT;

public final class HandImpl implements Shootable{

	public static final HandImpl INSTANCE = new HandImpl();
	
	private Random random = new Random();
	
	private HandImpl() {}

	@Override
	public void setBloom(float bloom) {
	}
	
	public void addBloom(float bloom) {
	}
	
	@Override
	public float getBloom() {
		return 0;
	}

	@Override
	public float getMaxBloom() {
		return 0;
	}

	@Override
	public float getBloomIncreasePerShot() {
		return 0;
	}

	@Override
	public float getBloomDecreasePerTick() {
		return 0;
	}

	@Override
	public float getMuzzleVelocity() {
		return 0;
	}

	@Override
	public float getMinVerticalRecoil() {
		return 0;
	}

	@Override
	public float getMaxVerticalRecoil() {
		return 0;
	}

	@Override
	public float getMinHorizontalRecoil() {
		return 0;
	}

	@Override
	public float getMaxHorizontalRecoil() {
		return 0;
	}

	@Override
	public CompoundNBT getProjectile() {
		return null;
	}

	@Override
	public void onTick(Object... data) {
	}
	
	@Override
	public void update(Object... data) {
	}

	@Override
	public Random getRandom() {
		return random;
	}

	@Override
	public void setRandom(Random random) {
		this.random = random;
	}

	@Override
	public void setProjectile(CompoundNBT projectile) {}

	@Override
	public void setMaxBloom(float maxBloom) {
	}

	@Override
	public void setBloomIncreasePerShot(float bloomIncrease) {
	}

	@Override
	public void setBloomDecreasePerTick(float bloomDecrease) {
	}

	@Override
	public void setMuzzleVelocity(float velocity) {
	}

	@Override
	public void setMinVerticalRecoil(float minVertical) {
	}

	@Override
	public void setMaxVerticalRecoil(float maxVertical) {
	}

	@Override
	public void setMinHorizontalRecoil(float minHorizontal) {
	}

	@Override
	public void setMaxHorizontalRecoil(float maxHorizontal) {
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putFloat("maxBloom", 0);
		nbt.putFloat("bloomI", 0);
		nbt.putFloat("bloomD", 0);
		nbt.putFloat("muzzleVelocity", 0);
		nbt.putFloat("minRecoilX", 0);
		nbt.putFloat("minRecoilY", 0);
		nbt.putFloat("maxRecoilX", 0);
		nbt.putFloat("maxRecoilY", 0);
		nbt.put("projectile", new CompoundNBT());
		nbt.putFloat("bloom", 0);
		return nbt;
	}


	@Override
	public void deserializeNBT(CompoundNBT base) {}

	@Override
	public float getBaseDamage() {
		return 0;
	}

	@Override
	public void setBaseDamage(float damage) {
	}

}
