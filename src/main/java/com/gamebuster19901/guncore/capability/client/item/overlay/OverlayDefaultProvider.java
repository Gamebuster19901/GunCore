/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.overlay;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class OverlayDefaultProvider implements ICapabilityProvider{

	public final OverlayDefaultImpl impl = (OverlayDefaultImpl) getCapability(OverlayDefaultImpl.CAPABILITY, null).orElseThrow(AssertionError::new);

	@Override
	@SuppressWarnings("unchecked")
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == OverlayDefaultImpl.CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(this::getImpl);
		}
		return LazyOptional.empty();
	}
	
	private OverlayDefaultImpl getImpl() {
		if(impl != null) {
			return impl;
		}
		return new OverlayDefaultImpl();
	}
	
}
