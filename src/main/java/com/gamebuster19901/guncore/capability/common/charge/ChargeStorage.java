/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.charge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ChargeStorage implements IStorage<Charge>{

	@Override
	public INBT writeNBT(Capability<Charge> capability, Charge instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Charge> capability, Charge instance, Direction side, INBT nbt) {
		instance.deserializeNBT((CompoundNBT) instance);
	}

}
