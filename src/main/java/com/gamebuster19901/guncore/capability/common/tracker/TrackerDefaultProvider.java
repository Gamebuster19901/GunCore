/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class TrackerDefaultProvider implements ICapabilityProvider{

	public Tracker impl = getCapability(TrackerDefaultImpl.CAPABILITY, null).orElseThrow(AssertionError::new);
	
	public TrackerDefaultProvider(CapabilityProvider provider, double x, double y) {
		if(provider instanceof Entity) {
			((TrackerDefaultImpl) impl).setImpl(new TrackerEntityImpl((Entity) provider, x, y));
		}
		else if (provider instanceof TileEntity) {
			((TrackerDefaultImpl) impl).setImpl(new TrackerTileEntityImpl((TileEntity)provider, x, y));
		}
		else {
			throw new IllegalArgumentException(provider.toString());
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == TrackerDefaultImpl.CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(this::getImpl);
		}
		return LazyOptional.empty();
	}
	
	private Tracker getImpl() {
		if(impl != null) {
			return impl;
		}
		return new TrackerDefaultImpl();
	}

}
