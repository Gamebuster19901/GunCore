/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.weapon;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class WeaponStorage implements Capability.IStorage<Weapon>{

	public static final WeaponStorage INSTANCE = new WeaponStorage();
	
	public static final String FIRE_RATE = "fireRate";
	public static final String AUTOMATIC = "isAutomatic";
	public static final String NEXT_FIRE = "nextFire";
	
	@Override
	public INBT writeNBT(Capability<Weapon> capability, Weapon instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<Weapon> capability, Weapon instance, Direction side, INBT tag) {
		instance.deserializeNBT((CompoundNBT) tag);
	}

}
