/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.overlay;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class OverlayStorage implements IStorage<Overlay>{

	@Override
	public INBT writeNBT(Capability<Overlay> capability, Overlay instance, Direction side) {
		return null;
	}

	@Override
	public void readNBT(Capability<Overlay> capability, Overlay instance, Direction side, INBT nbt) {}

}
