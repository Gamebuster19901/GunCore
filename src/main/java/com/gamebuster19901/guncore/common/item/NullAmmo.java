/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.item;

import com.gamebuster19901.guncore.common.item.abstracts.Ammo;
import com.gamebuster19901.guncore.common.item.abstracts.Projectile;
import static com.gamebuster19901.guncore.GunCore.MODID;

import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.dimension.DimensionType;

public class NullAmmo extends Ammo {

	private static final Projectile snowball;
	static {
		SnowballEntity entity = new SnowballEntity(null, 0, 0, 0);
		snowball = new Projectile(entity.toTag(new CompoundTag()));
	}
	 
	
	public static final NullAmmo INSTANCE = new NullAmmo();
	
	public NullAmmo() {
		super(snowball, 1, 0);
	}

	@Override
	public String getModId() {
		return MODID;
	}

	@Override
	public float getDamage() {
		return 0;
	}

}
