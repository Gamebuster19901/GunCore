/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.reticle;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ReticleStorage implements IStorage<Reticle>{

	@Override
	public INBT writeNBT(Capability<Reticle> capability, Reticle instance, Direction side) {
		return null;
	}

	@Override
	public void readNBT(Capability<Reticle> capability, Reticle instance, Direction side, INBT nbt) {}

}
