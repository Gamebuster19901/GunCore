/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.sticky;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StickyStorage implements IStorage<Sticky>{

	@Override
	public INBT writeNBT(Capability<Sticky> capability, Sticky instance, Direction side) {return new CompoundNBT();}

	@Override
	public void readNBT(Capability<Sticky> capability, Sticky instance, Direction side, INBT nbt) {}

}
