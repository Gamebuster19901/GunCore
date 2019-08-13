/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.stickable;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.entity.sticky.Sticky;
import com.gamebuster19901.guncore.capability.common.entity.sticky.StickyDefaultImpl;
import com.gamebuster19901.guncore.network.packet.server.UpdateStickable;

import static com.gamebuster19901.guncore.network.Network.CHANNEL;

import org.apache.logging.log4j.Level;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.network.PacketDistributor;

public class StickableDefaultImpl implements Stickable{
	
	@CapabilityInject(Stickable.class)
	public static Capability<Stickable> CAPABILITY;
	
	private Multimap<Class<? extends Sticky>, Sticky> sticks = HashMultimap.create();
	private Entity entity;

	@Override
	public boolean canBeStuckBy(Sticky sticky) {
		return canBeStuckBy(sticky.getClass());
	}
	
	@Override
	public boolean canBeStuckBy(Class<? extends Sticky> stickyType) {
		return this.getAmountStuck() <= 20;
	}

	@Override
	@Deprecated
	public boolean stick(Class<? extends Sticky> stickyType, Sticky sticky, boolean markDirty) {
		boolean stuck = sticks.put(stickyType, sticky);
		if(stuck) {
			update();
		}
		return stuck;
	}

	@Override
	@Deprecated
	public boolean unStick(Class<? extends Sticky> stickyType, Sticky sticky, boolean markDirty) {
		boolean unstuck = sticks.remove(stickyType, sticky);
		if(unstuck) {
			update();
		}
		return unstuck;
	}

	@Override
	public ImmutableMultimap<Class<? extends Sticky>, Sticky> getAllStickies() {
		return ImmutableMultimap.copyOf(sticks);
	}

	@Override
	public void update(Object... data) {
		if(entity != null && !entity.getEntityWorld().isRemote) {
			CHANNEL.send(PacketDistributor.ALL.noArg(), new UpdateStickable(this));
		}
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		ListNBT sticks = new ListNBT();
		for(Sticky sticky : this.sticks.values()) {
			sticks.add(new IntNBT(sticky.getStickyEntity().getEntityId()));
		}
		nbt.put("sticks", sticks);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		clear();
		ListNBT sticks = nbt.getList("sticks", 3);
		for(int i = 0; i < sticks.size(); i++) {
			Entity e = this.getEntity().world.getEntityByID(sticks.getInt(i));
			Main.LOGGER.log(Level.WARN, e.getDisplayName());
			if(e.getCapability(StickyDefaultImpl.CAPABILITY).isPresent()) {
				Sticky sticky = e.getCapability(StickyDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
				if(!(sticky.canStick(getEntity()) || sticky.stick(getEntity()))) {
					Main.LOGGER.warn("Couldn't stick " + sticky.getStickyEntity() + " to " + getEntity() + " even though it was serialized that way!");
				}
			}
			else {
				Main.LOGGER.warn(e + " isn't stickable, even though it was serialized as such!");
			}
		}
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity e) {
		entity = e;
	}

	@Override
	public void clear() {
		sticks = HashMultimap.create();
	}

}
