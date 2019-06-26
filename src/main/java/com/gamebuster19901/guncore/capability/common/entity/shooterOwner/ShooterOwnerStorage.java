package com.gamebuster19901.guncore.capability.common.entity.shooterOwner;

import java.util.UUID;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ShooterOwnerStorage implements IStorage<ShooterOwner>{

	@Override
	public INBTBase writeNBT(Capability<ShooterOwner> capability, ShooterOwner instance, EnumFacing side) {
		if(instance.getShooter() != null) {
			return new NBTTagString(instance.getShooter().toString());
		}
		return new NBTTagString("");
	}

	@Override
	public void readNBT(Capability<ShooterOwner> capability, ShooterOwner instance, EnumFacing side, INBTBase nbt) {
		NBTTagString uuidTag = (NBTTagString) nbt;
		String uuid = uuidTag.getString();
		if(!uuid.equals("")) {
			instance.setShooter(UUID.fromString(uuid));
		}
	}

}
