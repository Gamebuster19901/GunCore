/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.shootable;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ShootableStorage implements IStorage<Shootable>{

	public static final ShootableStorage INSTANCE = new ShootableStorage();
	
	@Override
	public INBT writeNBT(Capability<Shootable> capability, Shootable instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Shootable> capability, Shootable shootable, Direction side, INBT tag) {
		shootable.deserializeNBT((CompoundNBT) tag);
	}

}
