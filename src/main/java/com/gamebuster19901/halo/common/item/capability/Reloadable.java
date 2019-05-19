package com.gamebuster19901.halo.common.item.capability;

import com.gamebuster19901.halo.common.item.abstracts.Ammo;
import com.gamebuster19901.halo.common.util.EasyLocalization;
import com.gamebuster19901.halo.common.util.Updateable;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.INBTSerializable;

public interface Reloadable extends Updateable, INBTSerializable<NBTTagCompound>{
	
	public static interface Tag{}
	
	/**
	 * @return the magazine size
	 */
	public int getMagazineSize();
	
	public void setMagazineSize(int size);
	
	/**
	 * @return the amount of bullets in the magazine
	 */
	public int getAmountInMagazine();
	
	/**
	 * @param amount the amount to set in the mag
	 */
	public void setAmountInMagazine(int amount);
	
	public default void decreaseAmountInMag(int amount) {
		increaseAmountInMag(-amount);
	}
	
	public default void increaseAmountInMag(int amount) {
		setAmountInMagazine(MathHelper.clamp(getAmountInMagazine() + amount, 0, getMagazineSize()));
	}
	
	/**
	 * @return how long it takes in ticks to reload this
	 */
	public int getReloadTime();
	
	public void setReloadTime(int ticks);
	
	/**
	 * @return how long this weapon has been reloading
	 */
	public int getReloadProgress();
	
	public void setReloadProgress(int ticks);
	
	/**
	 * @return the ammo currently loaded in the weapon
	 */
	public Ammo getAmmoType();
	
	/**
	 * sets the ammo currently loaded in this reloadable
	 */
	public void setAmmoType(Ammo ammo);
	
	/**
	 * @return true if the specified ammo can be loaded into this weapon, false otherwise
	 */
	public boolean isValidAmmo(Ammo ammo);
	
	/**
	 * @return true if this reloadable is reloading, false otherwise
	 */
	public boolean isReloading();
	
	public void setIsReloading(boolean reloading);
	
	/**
	 * @param inventory attempt to reload the weapon by taking ammo from the specified container
	 */
	public default void attemptReload(IInventory... inventory) {
		int reloadProgress = getReloadProgress();
		int reloadTime = getReloadTime();
		
		if(reloadProgress >= reloadTime) {
			reload(inventory);
		}
	}
	
	public default void cancelReload() {
		setIsReloading(false);
		setReloadProgress(0);
	}
	
	/**
	 * Reload this reloadable
	 * @param containers
	 * @return the ammo that this reloadable reloaded with
	 */
	public Ammo reload(IInventory... inventories);
	
	public static ResourceLocation getCapabilityKey() {
		return EasyLocalization.getResourceLocation(Reloadable.class);
	}
}
