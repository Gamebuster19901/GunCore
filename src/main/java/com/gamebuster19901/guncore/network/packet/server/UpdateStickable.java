/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.network.packet.server;

import java.util.function.Supplier;

import org.apache.logging.log4j.Level;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultImpl;
import com.gamebuster19901.guncore.exception.CapabilityMismatchError;
import com.gamebuster19901.guncore.network.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateStickable {

	private final Stickable stickable;
	private final PacketBuffer buf;
	
	public UpdateStickable() {
		this((Stickable)null);
	}
	
	public UpdateStickable(Stickable stickable) {
		this.stickable = stickable;
		this.buf = null;
	}
	
	public UpdateStickable(PacketBuffer buf) {
		this.buf = buf;
		this.stickable = null;
	}
	
	public void encode(PacketBuffer buf) {
		Entity entity = stickable.getEntity();
		buf.writeInt(entity.getEntityWorld().getDimension().getType().getId());
		buf.writeInt(entity.getEntityId());
		buf.writeCompoundTag(stickable.serializeNBT());
		Main.LOGGER.catching(new Throwable());
		Main.LOGGER.log(Level.FATAL, "ENDCODED");
	}
	
	private void decodeAndApply(PacketBuffer buf) {
		Main.LOGGER.log(Level.FATAL, "DECODED");
		World world = Minecraft.getInstance().world;
		int curDim = world.getDimension().getType().getId();
		int stickyDim = buf.readInt();
		if(curDim == stickyDim) {
			int stickyID = buf.readInt();
			Entity entity = world.getEntityByID(stickyID);
			if(entity != null) {
				if(entity.isAlive()) {
					Stickable stickable = entity.getCapability(StickableDefaultImpl.CAPABILITY).orElseThrow(() -> new CapabilityMismatchError(StickableDefaultImpl.CAPABILITY, entity));
					stickable.deserializeNBT(buf.readCompoundTag()); //update the entity nbt
				}
				else {
					Main.LOGGER.warn("Received stickable update for deceased entity #" + stickyID + "... Ignoring!");
				}
			}
			else {
				Main.LOGGER.warn("Received stickable update for untracked entity #" + stickyID + "... Ignoring!");
			}
		}
		else {
			Main.LOGGER.warn("We are in dimension " + curDim + " but received a stickable update for an entity in dimension " + stickyDim + "... ignoring!");
		}
	}
	
	public void handle(Supplier<NetworkEvent.Context> context) {
		Main.LOGGER.log(Level.FATAL, "RECEIVED");
		context.get().enqueueWork(() -> {
			decodeAndApply(buf);
		});
	}
	
	public static void register(int id) {
		Network.CHANNEL.registerMessage(id++, UpdateStickable.class, UpdateStickable::encode, UpdateStickable::new, UpdateStickable::handle);
	}
	
}
