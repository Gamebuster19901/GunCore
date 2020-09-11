/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
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

public class TrackerTileEntityImpl extends TrackerBaseImpl {

	public static final String TILE_W = "tileW";
	public static final String TILE_X = "tileX";
	public static final String TILE_Y = "tileY";
	public static final String TILE_Z = "tileZ";
	
	private TileEntity tracker;
	
	public TrackerTileEntityImpl(TileEntity tracker) {
		this.tracker = tracker;
	}
	
	@Override
	public World getWorld() {
		return tracker.getWorld();
	}
	
	public Chunk getChunk() {
		return (Chunk) tracker.getWorld().getChunk(tracker.getPos());
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = super.serializeNBT();

		BlockPos pos = tracker.getPos();
		
		nbt.putInt(TILE_W, tracker.getWorld().getDimension().getType().getId());
		nbt.putInt(TILE_X, pos.getX());
		nbt.putInt(TILE_Y, pos.getY());
		nbt.putInt(TILE_Z, pos.getZ());
		
		return nbt;
	}

}
