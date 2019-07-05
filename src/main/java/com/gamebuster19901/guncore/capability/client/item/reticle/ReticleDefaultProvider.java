/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.reticle;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class ReticleDefaultProvider implements ICapabilityProvider{

	public final ReticleDefaultImpl impl = (ReticleDefaultImpl) getCapability(ReticleDefaultImpl.CAPABILITY, null).orElseThrow(AssertionError::new);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, EnumFacing side) {
		if(cap == ReticleDefaultImpl.CAPABILITY) {
			return (LazyOptional<T>) LazyOptional.of(this::getImpl);
		}
		return LazyOptional.empty();
	}
	
	private ReticleDefaultImpl getImpl() {
		if(impl != null) {
			return impl;
		}
		return new ReticleDefaultImpl();
	}

}
