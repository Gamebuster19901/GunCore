/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public abstract class StickyProjectile extends ProjectileEntity{

	public StickyProjectile(EntityType type, World world) {
		super(type, world);
	}
	
	public StickyProjectile(EntityType type, World world, float damage) {
		super(type, world, damage);
	}

}
