/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TrackerStorage implements IStorage<Tracker>{

	@Override
	public INBT writeNBT(Capability<Tracker> capability, Tracker instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Tracker> capability, Tracker instance, Direction side, INBT nbt) {
		instance.deserializeNBT((CompoundNBT) nbt);
	}

}
