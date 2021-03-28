/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.shooterOwner;

import java.util.UUID;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ShooterOwnerStorage implements IStorage<ShooterOwner>{

	@Override
	public INBT writeNBT(Capability<ShooterOwner> capability, ShooterOwner instance, Direction side) {
		if(instance.getShooter() != null) {
			return new StringNBT(instance.getShooter().toString());
		}
		return new StringNBT("");
	}

	@Override
	public void readNBT(Capability<ShooterOwner> capability, ShooterOwner instance, Direction side, INBT nbt) {
		StringNBT uuidTag = (StringNBT) nbt;
		String uuid = uuidTag.getString();
		if(!uuid.equals("")) {
			instance.setShooter(UUID.fromString(uuid));
		}
	}

}
