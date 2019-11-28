/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.charge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ChargeDefaultImpl implements Charge{

	public static final String CHARGING = "charging";
	public static final String CHARGE_TIME = "chargeTime";
	public static final String MAX_CHARGE_TIME = "maxChargeTime";
	
	@CapabilityInject(Charge.class)
	public static Capability<Charge> CAPABILITY;
	
	private boolean charging;
	private int chargeTime;
	private int maxChargeTime;
	
	public ChargeDefaultImpl() {
		this(Integer.MAX_VALUE);
	}
	
	public ChargeDefaultImpl(int maxChargeTime) {
		this.maxChargeTime = maxChargeTime;
	}
	
	@Override
	public boolean canCharge() {
		return !isCharging();
	}

	@Override
	public boolean isCharging() {
		return charging;
	}

	@Override
	public void beginCharging() {
		charging = true;
	}

	@Override
	public void stopCharging() {
		charging = false;
	}
	
	@Override
	public void onStopCharging() {
		
	}

	@Override
	public int getChargeTime() {
		return chargeTime;
	}

	@Override
	public int getMaxChargeTime() {
		return maxChargeTime;
	}
	
	@Override
	public void onTick(Object...data) {
		if(isCharging()) {
			if(getChargeTime() < getMaxChargeTime()) {
				chargeTime++;
			}
			else {
				stopCharging();
			}
		}
	}
	
	@Override
	public void update(Object... data) {

	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.putBoolean(CHARGING, charging);
		nbt.putInt(CHARGE_TIME, chargeTime);
		nbt.putInt(MAX_CHARGE_TIME, maxChargeTime);
		
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		charging = nbt.getBoolean(CHARGING);
		chargeTime = nbt.getInt(CHARGE_TIME);
		maxChargeTime = nbt.getInt(MAX_CHARGE_TIME);
	}

}
