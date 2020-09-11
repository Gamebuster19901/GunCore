/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.reloadable;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ReloadableStorage implements IStorage<Reloadable>{

	public static final ReloadableStorage INSTANCE = new ReloadableStorage();
	
	@Override
	public INBT writeNBT(Capability<Reloadable> capability, Reloadable instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Reloadable> capability, Reloadable reloadable, Direction side, INBT tag) {
		reloadable.deserializeNBT((CompoundNBT) tag);
	}

}
