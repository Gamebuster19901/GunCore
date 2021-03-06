/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.heat;

import com.gamebuster19901.guncore.Main;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class OverheatDefaultImpl implements Overheat{

	@CapabilityInject(Overheat.class)
	public static Capability<Overheat> CAPABILITY;
	
	public static final String OVERHEATING = "overheating";
	public static final String TEMP = "temp";
	public static final String MIN_TEMP = "minTemp";
	public static final String MAX_TEMP = "maxTemp";
	public static final String TEMP_DECREASE = "tempDecrease";
	public static final String TEMP_INCREASE = "tempIncrease";
	
	private boolean overheating;
	private double temp;
	private double minTemp;
	private double maxTemp;
	private double tempDecrease;
	private double tempIncrease;
	
	public OverheatDefaultImpl(double minTemp, double maxTemp, double tempDecrease, double tempIncrease) {
		setMaxTemp(maxTemp);
		setMinTemp(minTemp);
		setTempDecrease(tempDecrease);
		setTempIncrease(tempIncrease);
	}
	
	@Override
	public void onTick(Object... data) {
		if(!overheating) {
			if(getTemp() == getMaxTemp()) {
				overheat();
				return;
			}
		}
		else if(overheating) {
			if(getTemp() == getMinTemp()) {
				stopOverheat();
				return;
			}
		}
		addTemp(-tempDecrease);
	}
	
	@Override
	public void update(Object... data) {

	}

	@Override
	public double getTemp() {
		return temp;
	}

	@Override
	public void setTemp(double temp) {
		this.temp = MathHelper.clamp(temp, getMinTemp(), getMaxTemp());
	}

	@Override
	public double getMinTemp() {
		return minTemp;
	}

	@Override
	public void setMinTemp(double minTemp) {
		if(minTemp > getMaxTemp()) {
			throw new IndexOutOfBoundsException("Temperature range less than 0");
		}
		this.minTemp = minTemp;
		if(getTemp() < getMinTemp()) {
			setTemp(minTemp);
			Main.LOGGER.warn("Temperature set to minimum value because the minimum temperature exceeded the actual temperature");
		}
	}

	@Override
	public double getMaxTemp() {
		return maxTemp;
	}

	@Override
	public void setMaxTemp(double maxTemp) {
		if(maxTemp < getMinTemp()) {
			throw new IndexOutOfBoundsException("Temperature range less than 0");
		}
		this.maxTemp = maxTemp;
		if(getTemp() > getMaxTemp()) {
			setTemp(getMaxTemp());
			Main.LOGGER.warn("Temperature set to maximum value vecause the maximum temperature fell below the actual temperature");
		}
	}

	@Override
	public void overheat() {
		overheating = true;
	}

	@Override
	public void stopOverheat() {
		overheating = false;
	}

	@Override
	public boolean isOverheating() {
		return overheating;
	}

	@Override
	public double getTempDecrease() {
		return tempDecrease;
	}
	
	@Override
	public void setTempDecrease(double tempDecrease) {
		this.tempDecrease = tempDecrease;
	}

	@Override
	public double getTempIncrease() {
		return tempIncrease;
	}

	@Override
	public void setTempIncrease(double tempIncrease) {
		this.tempIncrease = tempIncrease;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.putBoolean(OVERHEATING, overheating);
		nbt.putDouble(TEMP, temp);
		nbt.putDouble(MIN_TEMP, minTemp);
		nbt.putDouble(MAX_TEMP, maxTemp);
		nbt.putDouble(TEMP_DECREASE, tempDecrease);
		nbt.putDouble(TEMP_INCREASE, tempIncrease);
		
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		overheating = nbt.getBoolean(OVERHEATING);
		temp = nbt.getDouble(TEMP);
		minTemp = nbt.getDouble(MIN_TEMP);
		maxTemp = nbt.getDouble(MAX_TEMP);
		tempDecrease = nbt.getDouble(TEMP_DECREASE);
		tempIncrease = nbt.getDouble(TEMP_INCREASE);
		
	}

}
