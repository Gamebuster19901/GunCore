/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.entity;

import com.gamebuster19901.guncore.common.util.Resourced;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import net.minecraftforge.fml.network.NetworkHooks;

public abstract class GunCoreEntity extends Entity implements Resourced{

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

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
}
