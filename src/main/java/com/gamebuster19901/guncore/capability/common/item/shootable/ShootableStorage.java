/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.shootable;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ShootableStorage implements IStorage<Shootable>{

	public static final ShootableStorage INSTANCE = new ShootableStorage();
	
	@Override
	public INBTBase writeNBT(Capability<Shootable> capability, Shootable instance, EnumFacing side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Shootable> capability, Shootable shootable, EnumFacing side, INBTBase tag) {
		shootable.deserializeNBT((NBTTagCompound) tag);
	}

}
