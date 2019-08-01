/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.stickable;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.sticky.Sticky;
import com.gamebuster19901.guncore.common.util.Updateable;

import com.google.common.collect.ImmutableMultimap;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface Stickable extends Updateable, INBTSerializable<CompoundNBT>{

	public boolean canBeStuckBy(Sticky sticky);
	
	public boolean canBeStuckBy(Class<? extends Sticky> stickyType);
	
	public default boolean stick(Sticky sticky) {
		LazyOptional<Stickable> optional = sticky.getStickyEntity().getCapability(StickableDefaultImpl.CAPABILITY);
		if(optional.isPresent()) {
			Stickable stickable = optional.orElseThrow(AssertionError::new);
			if(stickable.contains(this)) {
				Main.LOGGER.warn("Stopped recursive sticking of " + this);
				return false;
			}
		}
		return stick(sticky.getClass(), sticky);
	}
	
	@Deprecated
	public boolean stick(Class<? extends Sticky> stickyType, Sticky sticky);
	
	public default boolean unStick(Sticky sticky) {
		return unStick(sticky.getClass(), sticky);
	}
	
	@Deprecated
	public boolean unStick(Class<? extends Sticky> stickyType, Sticky sticky);
	
	public default void unStick(Class<? extends Sticky> stickyType) {
		for(Sticky sticky : getAllStickies().get(stickyType)) {
			if(!unStick(sticky)) {
				throw new AssertionError();
			}
		}
	}
	
	public default void unStick() {
		for(Sticky sticky : getAllStickies().values()) {
			if(!unStick(sticky)) {
				throw new AssertionError();
			}
		}
	}
	
	public ImmutableMultimap<Class<? extends Sticky>, Sticky> getAllStickies();
	
	public default int getAmountStuck(Class<? extends Sticky> stickyType) {
		return getAllStickies().get(stickyType).size();
	}
	
	public default int getAmountStuck() {
		return getAllStickies().values().size();
	}
	
	public Entity getEntity();

	public void setEntity(Entity e);
	
	public default boolean contains(Stickable other) {
		for(Sticky sticky : getAllStickies().values()) {
			LazyOptional<Stickable> optional = sticky.getStickyEntity().getCapability(StickableDefaultImpl.CAPABILITY);
			if(optional.isPresent()) {
				Stickable stickable = optional.orElseThrow(AssertionError::new);
				return stickable == other || stickable.contains(other);
			}
		}
		return false;
	}
	
}
