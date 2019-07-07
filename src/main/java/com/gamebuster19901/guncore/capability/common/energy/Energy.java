/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.energy;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public interface Energy extends IEnergyStorage, Updateable, INBTSerializable<CompoundNBT>{
	
	/**
	 * sets the amount of energy in this to the specified amount, or
	 * the maximum capacity if the amount specified exceeds the capacity
	 * 
	 * ignores maxReceive and maxExtract restrictions
	 * 
	 * @param energy
	 * @return the amount of energy now stored in this
	 */
	
	int setEnergy(int energy);
	
	void setCapacity(int capacity);
	
	void setCanReceive(boolean canReceive);
	
	void setMaxReceive(int maxReceive);
	
	int getMaxReceive();
	
	void setMaxExtract(int maxExtract);
	
	int getMaxExtract();
	
	void setCanExtract(boolean canExtract);
	
	default double getPercentageRemaining() {
		if(this.getEnergyStored() == 0) {
			return (double)0;
		}
		return (double)this.getMaxEnergyStored() / (double)this.getEnergyStored();
	}
	
}
