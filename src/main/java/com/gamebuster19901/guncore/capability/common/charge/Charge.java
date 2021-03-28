/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.charge;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface Charge extends Updateable, INBTSerializable<CompoundNBT>{

	public boolean canCharge();
	
	public boolean isCharging();
	
	public void beginCharging();
	
	public void stopCharging();
	
	public void onStopCharging();
	
	public int getChargeTime();
	
	public int getMaxChargeTime();
	
	public default float getChargeProgress() {
		return (float)getChargeTime() / (float)getMaxChargeTime();
	}
	
}
