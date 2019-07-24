/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public abstract class StickyProjectile extends ProjectileEntity{

	public StickyProjectile(EntityType type, World worldIn) {
		super(type, worldIn);
	}

}
