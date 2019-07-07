/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.entity;

import com.gamebuster19901.guncore.common.util.EasyLocalization;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public abstract class GunCoreEntity extends Entity implements EasyLocalization{

	public GunCoreEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
		if(worldIn == null) {
			this.dimension = DimensionType.OVERWORLD;
		}
	}
	
	@Override
	public boolean writeUnlessRemoved(CompoundNBT compound) {
		String s = getResourceLocation().toString();
		if (!this.removed && s != null) {
			compound.putString("id", s);
			this.writeWithoutTypeId(compound);
			return true;
		} else {
			return false;
		}
	}

}
