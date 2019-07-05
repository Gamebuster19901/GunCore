/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.reticle;

import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ReticleStorage implements IStorage<Reticle>{

	@Override
	public INBTBase writeNBT(Capability<Reticle> capability, Reticle instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<Reticle> capability, Reticle instance, EnumFacing side, INBTBase nbt) {}

}
