/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class WeaponDefaultImpl implements Weapon{
	@CapabilityInject(Weapon.class)
	public static Capability<Weapon> CAPABILITY = null;
	
	public static final String MELEE_DAMAGE = "meleeDamage";
	public static final String FIRE_RATE = "fireRate";
	public static final String AUTOMATIC = "isAutomatic";
	public static final String NEXT_FIRE = "nextFire";
	
	protected float meleeDamage;
	protected int fireRate;
	protected boolean isAutomatic;
	
	byte nextFire = 0;
	
	public WeaponDefaultImpl(float meleeDamage, int baseFireRate, boolean isAutomatic) {
		this.meleeDamage = meleeDamage;
		this.fireRate = baseFireRate;
		this.isAutomatic = isAutomatic;
	}
	
	@Override
	public float getMeleeDamage() {
		return meleeDamage;
	}
	
	@Override
	public void setMeleeDamage(float meleeDamage) {
		this.meleeDamage = meleeDamage;
	}
	
	@Override
	public boolean canFire(Entity shooter) {
		return this.getTimeUntilNextFire() <= 0 && !shooter.isSprinting();
	}
	
	@Override
	public void fire(Entity shooter) {
		nextFire = getTimeUntilNextFire();
	}

	@Override
	public int getFireRate() {
		return fireRate;
	}
	
	@Override
	public void setFireRate(int rate) {
		this.fireRate = rate;
	}

	@Override
	public byte getTimeUntilNextFire() {
		return nextFire;
	}
	
	@Override
	public void setTimeUntilNextFire(byte nextFire) {
		this.nextFire = nextFire;
	}

	@Override
	public boolean isAutomatic() {
		return isAutomatic;
	}
	
	@Override
	public void setAutomatic(boolean isAutomatic) {
		this.isAutomatic = isAutomatic;
	}

	@Override
	public void onTick(Object... data) {
		if(nextFire > 0) {
			nextFire--;
		}
	}

	@Override
	public void update(Object... data) {

	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putFloat(MELEE_DAMAGE, meleeDamage);
		nbt.putInt(FIRE_RATE, fireRate);
		nbt.putBoolean(AUTOMATIC, isAutomatic);
		nbt.putByte(NEXT_FIRE, nextFire);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT tag) {
		CompoundNBT nbt = (CompoundNBT) tag;
		meleeDamage = nbt.getFloat(MELEE_DAMAGE);
		fireRate = nbt.getInt(FIRE_RATE);
		isAutomatic = nbt.getBoolean(AUTOMATIC);
		nextFire = nbt.getByte(NEXT_FIRE);
	}
}
