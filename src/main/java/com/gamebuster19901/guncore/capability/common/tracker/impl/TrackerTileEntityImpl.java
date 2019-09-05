/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.tracker.impl;

import static com.gamebuster19901.guncore.network.Network.CHANNEL;

import com.gamebuster19901.guncore.network.packet.server.UpdateTracker;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;

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
	public void update(Object... data) {
		if(tracker != null && !tracker.getWorld().isRemote) {
			CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(this::getChunk), new UpdateTracker(this));
		}
	}

}
