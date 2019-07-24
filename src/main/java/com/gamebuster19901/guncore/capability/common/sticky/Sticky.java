/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.sticky;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface Sticky extends Updateable, INBTSerializable<CompoundNBT>{

	public boolean canStick(Object o);
	
	public boolean stick(Object o);
	
	public void unStick(Object o);
	
	public void unStick();
	
	public Object getObjectStuckTo();
	
}
