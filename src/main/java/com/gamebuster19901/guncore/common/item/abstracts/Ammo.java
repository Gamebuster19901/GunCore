/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.item.abstracts;

import com.gamebuster19901.guncore.common.util.EasyLocalization;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Ammo extends Item implements EasyLocalization{
	private final Projectile projectile;
	
	public Ammo(Projectile projectile, int maxStackSize) {
		super(new Item.Properties().maxStackSize(maxStackSize));
		this.setRegistryName(getResourceLocation());
		this.projectile = projectile;
	}
	
	public TranslationTextComponent getIcon() {
		String key = this.getEZTranslationKey();
		return new TranslationTextComponent("item." + key.substring(0, key.lastIndexOf('_')) + ".icon");
	}
	
	public final CompoundNBT getProjectile() {
		return projectile.getProjectileNBT();
	}
}
