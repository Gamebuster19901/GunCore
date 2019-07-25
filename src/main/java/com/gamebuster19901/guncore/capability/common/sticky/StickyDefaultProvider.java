/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.sticky;

import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class StickyDefaultProvider implements ICapabilityProvider{

	public Sticky impl = getCapability(StickyDefaultImpl.CAPABILITY, null).orElseThrow(AssertionError::new);
	
	public StickyDefaultProvider(Entity e) {
		impl.setEntity(e);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
		if(cap == StickyDefaultImpl.CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(this::getImpl);
		}
		return LazyOptional.empty();
	}
	
	private Sticky getImpl() {
		if(impl != null) {
			return impl;
		}
		return new StickyDefaultImpl();
	}

}
