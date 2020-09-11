/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import com.gamebuster19901.guncore.capability.common.tracker.impl.TrackerBaseImpl;
import com.gamebuster19901.guncore.capability.common.tracker.impl.TrackerEntityImpl;
import com.gamebuster19901.guncore.capability.common.tracker.impl.TrackerTileEntityImpl;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TrackerDefaultProvider implements ICapabilitySerializable<CompoundNBT>{
	
	public Tracker impl;
			
	public TrackerDefaultProvider(Entity tracker) {
		impl = new TrackerEntityImpl(tracker);
	}
	
	public TrackerDefaultProvider(TileEntity tracker) {
		impl = new TrackerTileEntityImpl(tracker);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == TrackerBaseImpl.CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(this::getImpl);
		}
		return LazyOptional.empty();
	}
	
	public Tracker getImpl() {
		return impl;
	}

	@Override
	public CompoundNBT serializeNBT() {
		return impl.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		impl.deserializeNBT(nbt);
	}

}
