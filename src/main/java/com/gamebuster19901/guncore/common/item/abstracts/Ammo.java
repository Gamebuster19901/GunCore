package com.gamebuster19901.guncore.common.item.abstracts;

import com.gamebuster19901.guncore.common.util.EasyLocalization;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class Ammo extends Item implements EasyLocalization{
	private final Projectile projectile;
	
	public Ammo(Projectile projectile, int maxStackSize) {
		super(new Item.Properties().maxStackSize(maxStackSize));
		this.setRegistryName(getResourceLocation());
		this.projectile = projectile;
	}
	
	public TextComponentTranslation getIcon() {
		String key = this.getEZTranslationKey();
		return new TextComponentTranslation("item." + key.substring(0, key.lastIndexOf('_')) + ".icon");
	}
	
	public final NBTTagCompound getProjectile() {
		return projectile.getProjectileNBT();
	}
}
