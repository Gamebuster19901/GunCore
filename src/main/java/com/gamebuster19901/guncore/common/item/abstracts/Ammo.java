/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.item.abstracts;

import com.gamebuster19901.guncore.common.util.Resourced;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;

public abstract class Ammo extends Item implements Resourced{
	private final Projectile projectile;
	private final float damage;
	
	public Ammo(Projectile projectile, int maxStackSize, float additonalDamage) {
		super(new Item.Settings().maxCount(maxStackSize));
		this.projectile = projectile;
		this.damage = additonalDamage;
	}
	
	public TranslatableText getIcon() {
		String key = this.getEZTranslationKey();
		return new TranslatableText("item." + key.substring(0, key.lastIndexOf('_')) + ".icon");
	}
	
	public final CompoundTag getProjectile() {
		return projectile.getProjectileNBT();
	}
	
	public float getDamage() {
		return damage;
	}
}
