/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class EnergyDefaultImpl implements Energy{

	@CapabilityInject(Energy.class)
	public static Capability<Energy> CAPABILITY = null;
	
	public static final String ENERGY = "energy";
	public static final String CAPACITY = "capacity";
	public static final String MAX_RECEIVE = "maxReceive";
	public static final String MAX_EXTRACT = "maxExtract";
	public static final String CAN_RECEIVE = "canReceive";
	public static final String CAN_EXTRACT = "canExtract";
	
	private int energy;
	private int capacity;
	private int maxReceive;
	private int maxExtract;
	private boolean canReceive;
	private boolean canExtract;
	
	public EnergyDefaultImpl(int capacity) {
		this(capacity, capacity);
	}
	
	public EnergyDefaultImpl(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer);
	}
	
	public EnergyDefaultImpl(int capacity, int maxReceive, int maxExtract) {
		this(capacity, maxReceive, maxExtract, 0);
	}
	
	public EnergyDefaultImpl(int capacity, int maxReceive, int maxExtract, int energy) {
		this(capacity, maxReceive, maxExtract, energy, maxReceive != 0, maxExtract != 0);
	}
	
	public EnergyDefaultImpl(int capacity, int maxReceive, int maxExtract, int energy, boolean canReceive, boolean canExtract) {
		setCapacity(capacity);
		setMaxReceive(maxReceive);
		setMaxExtract(maxExtract);
		setEnergy(energy);
		setCanReceive(canReceive);
		setCanExtract(canExtract);
	}
	
	@Override
	public int receiveEnergy(int receive, boolean simulate) {
		int energy = MathHelper.clamp(Math.min(this.energy + receive, this.energy + maxReceive), 0, getMaxEnergyStored());
		if(!simulate) {
			this.energy = energy;
		}
		return this.energy;
	}

	@Override
	public int extractEnergy(int extract, boolean simulate) {
		int energy = MathHelper.clamp(Math.min(this.energy - extract, this.energy - maxExtract), 0, getMaxEnergyStored());
		if(!simulate) {
			this.energy = energy;
		}
		return this.energy;
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public boolean canExtract() {
		return canExtract;
	}

	@Override
	public boolean canReceive() {
		return canReceive;
	}

	@Override
	public int setEnergy(int energy) {
		this.energy = MathHelper.clamp(energy, 0, capacity);
		return energy;
	}

	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0) {
			capacity = 0;
		}
		this.capacity = capacity;
	}

	@Override
	public void setMaxReceive(int maxReceive) {
		if(maxReceive < 0) {
			maxReceive = 0;
		}
		this.maxReceive = maxReceive;
	}
	
	@Override
	public int getMaxReceive() {
		return maxReceive;
	}

	@Override
	public void setMaxExtract(int maxExtract) {
		if(maxExtract < 0) {
			maxExtract = 0;
		}
		this.maxExtract = 0;
	}
	
	@Override
	public int getMaxExtract() {
		return this.maxExtract;
	}

	@Override
	public void setCanReceive(boolean canReceive) {
		this.canReceive = canReceive;
	}

	@Override
	public void setCanExtract(boolean canExtract) {
		this.canExtract = canExtract;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt(ENERGY, energy);
		nbt.putInt(CAPACITY, capacity);
		nbt.putInt(MAX_RECEIVE, maxReceive);
		nbt.putInt(MAX_EXTRACT, maxExtract);
		nbt.putBoolean(CAN_RECEIVE, canReceive);
		nbt.putBoolean(CAN_EXTRACT, canExtract);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		energy = nbt.getInt(ENERGY);
		capacity = nbt.getInt(CAPACITY);
		maxReceive = nbt.getInt(MAX_RECEIVE);
		maxExtract = nbt.getInt(MAX_EXTRACT);
		canReceive = nbt.getBoolean(CAN_RECEIVE);
		canExtract = nbt.getBoolean(CAN_EXTRACT);
	}

	@Override
	public void onTick(Object... data) {

	}
	
	@Override
	public void update(Object... data) {
		
	}

}
