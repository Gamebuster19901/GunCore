/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.reloadable;

import com.gamebuster19901.guncore.common.item.NullAmmo;
import com.gamebuster19901.guncore.common.item.abstracts.Ammo;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.registries.ForgeRegistries;

public class ReloadableDefaultImpl implements Reloadable{
	
	@CapabilityInject(Reloadable.class)
	public static Capability<Reloadable> CAPABILITY = null;
	
	protected int magSize;
	protected int reloadTime;
	
	protected int amountLoaded = 0;
	protected int reloadProgress = 0;
	protected boolean isReloading = false;
	protected Ammo ammoType = NullAmmo.INSTANCE;
	
	public ReloadableDefaultImpl(int magSize, int reloadTime) {
		this.magSize = magSize;
		this.reloadTime = reloadTime;
	}

	@Override
	public int getMagazineSize() {
		return magSize;
	}
	
	@Override
	public void setMagazineSize(int size) {
		magSize = size;
	}

	@Override
	public int getAmountInMagazine() {
		return amountLoaded;
	}

	@Override
	public void setAmountInMagazine(int amount) {
		System.out.println(amount);
		amountLoaded = amount;
	}

	@Override
	public int getReloadTime() {
		return reloadTime;
	}
	
	@Override
	public void setReloadTime(int ticks) {
		reloadTime = ticks;
	}

	@Override
	public int getReloadProgress() {
		return reloadProgress;
	}
	
	@Override
	public void setReloadProgress(int ticks) {
		reloadProgress = ticks;
	}
	
	@Override
	public boolean isReloading() {
		return isReloading;
	}
	
	@Override
	public void setIsReloading(boolean reloading) {
		isReloading = reloading;
	}

	@Override
	public Ammo getAmmoType() {
		return ammoType;
	}
	
	@Override
	public void setAmmoType(Ammo ammo) {
		this.ammoType = ammo;
	}

	@Override
	public boolean isValidAmmo(Ammo ammo) {
		return ammo != NullAmmo.INSTANCE;
	}

	@Override
	public Ammo reload(IInventory... inventories) {
		Ammo ammo = NullAmmo.INSTANCE;
		for(IInventory inventory : inventories) {
			for(int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if(stack.getItem() instanceof Ammo) {
					Ammo stackAmmo = (Ammo) stack.getItem();
					if(isValidAmmo(stackAmmo)) {
						if(ammo == NullAmmo.INSTANCE) {
							ammo = stackAmmo;
						}
						if(ammo == stackAmmo) {
							while(!stack.isEmpty() && getAmountInMagazine() < getMagazineSize()) {
								stack.shrink(1);
								if(stack.isEmpty()) {
									inventory.removeStackFromSlot(i);
								}
								setAmountInMagazine(getAmountInMagazine() + 1);
							}
							setAmmoType(ammo);
						}
					}
				}
			}
		}
		return ammo;
	}
	
	@Deprecated
	@Override
	public void onTick(Object... data) {
		update(data);
	}
	
	@Override
	public void update(Object... data) {
		if(getReloadProgress() < getReloadTime()) {
			reloadProgress++;
		}
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("magSize", magSize);
		nbt.putInt("reloadTime", reloadTime);
		nbt.putInt("amountLoaded", amountLoaded);
		nbt.putInt("reloadProgress", reloadProgress);
		nbt.putBoolean("isReloading", isReloading);
		nbt.putString("ammoType", ammoType.getResourceLocation().toString());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT tag) {
		CompoundNBT nbt = (CompoundNBT) tag;
		magSize = nbt.getInt("magSize");
		reloadTime = nbt.getInt("reloadTime");
		amountLoaded = nbt.getInt("amountLoaded");
		reloadProgress = nbt.getInt("reloadProgress");
		isReloading = nbt.getBoolean("isReloading");
		ResourceLocation ammo = new ResourceLocation(nbt.getString("ammoType"));
		if(ForgeRegistries.ITEMS.containsKey(ammo)) {
			ammoType = (Ammo) ForgeRegistries.ITEMS.getValue(ammo);
		}
		else {
			ammoType = NullAmmo.INSTANCE;
		}
	}
}
