/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.impl;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class TrackerTileEntityImpl extends TrackerBaseImpl{

	private TileEntity tracker;
	
	public TrackerTileEntityImpl(TileEntity tracker) {
		this.tracker = tracker;
	}
	
	@Override
	public World getWorld() {
		return tracker.getWorld();
	}
	
	private Chunk getChunk() {
		return (Chunk) tracker.getWorld().getChunk(tracker.getPos());
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = super.serializeNBT();

		BlockPos pos = tracker.getPos();
		
		nbt.putInt("tileX", pos.getX());
		nbt.putInt("tileY", pos.getY());
		nbt.putInt("tileZ", pos.getZ());
		
		return nbt;
	}

}
