/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.shooterOwner;

import java.util.UUID;

import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public interface ShooterOwner {
	
	public default void setShooter(Entity entity) {
		if(entity != null) {
			setShooter(entity.getUniqueID());
		}
	}
	
	public UUID getShooter();
	
	public void setShooter(UUID uuid);
	
	public void setGun(Shootable shootable);
	
	public NBTTagCompound getGun();
	
}
