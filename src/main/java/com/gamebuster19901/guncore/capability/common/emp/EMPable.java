/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.emp;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.nbt.IntNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface EMPable extends Updateable, INBTSerializable<IntNBT>{

	public void emp(int duration);
	
	public int getDuration();
	
	public boolean isEMPed();
	
}
