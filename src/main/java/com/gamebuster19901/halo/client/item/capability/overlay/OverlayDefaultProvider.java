package com.gamebuster19901.halo.client.item.capability.overlay;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class OverlayDefaultProvider implements ICapabilityProvider{

	public final OverlayDefaultImpl impl = (OverlayDefaultImpl) getCapability(OverlayDefaultImpl.CAPABILITY, null).orElseThrow(AssertionError::new);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, EnumFacing side) {
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
