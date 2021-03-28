/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.shooterOwner;

import java.util.UUID;

import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;

public class ShooterOwnerDefaultImpl implements ShooterOwner, INBTSerializable<CompoundNBT>{

	@CapabilityInject(ShooterOwner.class)
	public static Capability<ShooterOwner> CAPABILITY = null;
	
	public static final String SHOOTER = "shooter";
	public static final String GUN = "gun";
	
	private UUID shooter;
	private CompoundNBT gun;
	
	public ShooterOwnerDefaultImpl(Entity shooter, Shootable gun) {
		setShooter(shooter);
		setGun(gun);
		assert shooter != null;
		assert gun != null;
	}
	
	public ShooterOwnerDefaultImpl(UUID uuid, CompoundNBT gun) {
		setShooter(uuid);
		setGun(gun);
		assert uuid != null;
		assert gun != null;
	}

	@Override
	public UUID getShooter() {
		return shooter;
	}

	@Override
	public void setShooter(UUID uuid) {
		this.shooter = uuid;
	}

	@Override
	public void setGun(Shootable shootable) {
		assert shootable != null;
		this.gun = shootable.serializeNBT();
	}

	private void setGun(CompoundNBT gun) {
		this.gun = gun;
	}

	@Override
	public CompoundNBT getGun() {
		return gun;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.put(GUN, gun);
		if(shooter != null) {
			nbt.putString(SHOOTER, shooter.toString());
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		this.gun = nbt.getCompound(GUN);
		if(nbt.contains(SHOOTER)) {
			this.shooter = UUID.fromString(SHOOTER);
		}
	}
}
