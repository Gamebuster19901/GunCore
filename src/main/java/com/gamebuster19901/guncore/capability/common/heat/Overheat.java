/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.heat;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface Overheat extends Updateable, INBTSerializable<NBTTagCompound>{

	public double getTemp();
	
	public void setTemp(double temp);
	
	public default void addTemp(double temp) {
		setTemp(getTemp() + temp);
	}
	
	public double getMinTemp();
	
	public void setMinTemp(double minTemp);
	
	public double getMaxTemp();
	
	public void setMaxTemp(double maxTemp);
	
	public void overheat();
	
	public void stopOverheat();
	
	public boolean isOverheating();
	
	public double getTempDecrease();
	
	public void setTempDecrease(double tempDecrease);
	
	public double getTempIncrease();
	
	public void setTempIncrease(double tempIncrease);
	
	public default double getTempProgress() {
		return (double)getTemp() / (double)getMaxTemp();
	}
}
