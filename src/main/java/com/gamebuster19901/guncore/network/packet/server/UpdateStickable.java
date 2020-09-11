/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.network.packet.server;

import java.util.function.Supplier;

import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
import static com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultImpl.ID;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultImpl;
import com.gamebuster19901.guncore.exception.CapabilityMismatchError;
import com.gamebuster19901.guncore.network.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateStickable {
	private final Stickable stickable;
	private final PacketBuffer buf;
	
	public UpdateStickable(Stickable stickable) {
		this.stickable = stickable;
		this.buf = null;
	}
	
	public UpdateStickable(PacketBuffer buf) {
		stickable = null;
		this.buf = buf;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeCompoundTag(stickable.serializeNBT());
	}
	
	@SuppressWarnings("resource")
	public void handle(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			CompoundNBT nbt = buf.readCompoundTag();
			Entity e = Minecraft.getInstance().world.getEntityByID(nbt.getInt(ID));
			Stickable stickable = e.getCapability(StickableDefaultImpl.CAPABILITY).orElseThrow(() -> new CapabilityMismatchError(StickableDefaultImpl.CAPABILITY, e));
			stickable.deserializeNBT(nbt);
		});
	}
	
	public static void register(int id) {
		Network.CHANNEL.registerMessage(id++, UpdateStickable.class, UpdateStickable::encode, UpdateStickable::new, UpdateStickable::handle);
	}
	
}
