/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.energy;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EnergyStorage implements IStorage<Energy>{

	@Override
	public INBTBase writeNBT(Capability<Energy> capability, Energy instance, EnumFacing side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Energy> capability, Energy instance, EnumFacing side, INBTBase nbt) {
		instance.deserializeNBT((NBTTagCompound) nbt);
	}

}
