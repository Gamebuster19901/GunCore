/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.stickable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class StickableDefaultProvider implements ICapabilitySerializable<CompoundNBT>{
	
	public Stickable impl = getCapability(StickableDefaultImpl.CAPABILITY, null).orElseThrow(AssertionError::new);

	public StickableDefaultProvider(Entity e) {
		impl.setEntity(e);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
		if(cap == StickableDefaultImpl.CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(this::getImpl);
		}
		return LazyOptional.empty();
	}
	
	private Stickable getImpl() {
		if(impl != null) {
			return impl;
		}
		return new StickableDefaultImpl();
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
