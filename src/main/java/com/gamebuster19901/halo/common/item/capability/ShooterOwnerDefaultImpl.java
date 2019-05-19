package com.gamebuster19901.halo.common.item.capability;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;

public class ShooterOwnerDefaultImpl implements ShooterOwner, INBTSerializable<NBTTagCompound>{

	@CapabilityInject(ShooterOwner.class)
	public static Capability<ShooterOwner> CAPABILITY = null;
	
	private UUID shooter;
	private NBTTagCompound gun;
	
	public ShooterOwnerDefaultImpl(Entity shooter, Shootable gun) {
		setShooter(shooter);
		setGun(gun);
		assert shooter != null;
		assert gun != null;
	}
	
	public ShooterOwnerDefaultImpl(UUID uuid, NBTTagCompound gun) {
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

	private void setGun(NBTTagCompound gun) {
		this.gun = gun;
	}

	@Override
	public NBTTagCompound getGun() {
		return gun;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("gun", gun);
		if(shooter != null) {
			nbt.setString("shooter", shooter.toString());
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.gun = nbt.getCompoundTag("gun");
		if(nbt.hasKey("shooter")) {
			this.shooter = UUID.fromString("shooter");
		}
	}

	
}
