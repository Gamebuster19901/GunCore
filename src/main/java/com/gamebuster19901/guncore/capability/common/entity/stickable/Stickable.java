/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.stickable;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.entity.sticky.Sticky;
import com.gamebuster19901.guncore.common.util.Clearable;
import com.gamebuster19901.guncore.common.util.Updateable;

import com.google.common.collect.ImmutableMultimap;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface Stickable extends Clearable, Updateable, INBTSerializable<CompoundNBT>{

	public boolean canBeStuckBy(Sticky sticky);
	
	public boolean canBeStuckBy(Class<? extends Sticky> stickyType);
	
	public default boolean stick(Sticky sticky) {
		LazyOptional<Stickable> optional = sticky.getStickyEntity().getCapability(StickableDefaultImpl.CAPABILITY);
		if(optional.isPresent()) {
			Stickable stickable = optional.orElseThrow(AssertionError::new);
			if(stickable.contains(this)) {
				Main.LOGGER.warn("Stopped recursive sticking of " + this.getEntity().getDisplayName().getFormattedText());
				return false;
			}
		}
		return stick(sticky.getClass(), sticky, true);
	}
	
	@Deprecated
	public boolean stick(Class<? extends Sticky> stickyType, Sticky sticky, boolean markDirty);
	
	public default boolean unStick(Sticky sticky, boolean markDirty) {
		return unStick(sticky.getClass(), sticky, markDirty);
	}
	
	@Deprecated
	public boolean unStick(Class<? extends Sticky> stickyType, Sticky sticky, boolean markDirty);
	
	public default void unStick(Class<? extends Sticky> stickyType) {
		for(Sticky sticky : getAllStickies().get(stickyType)) {
			if(!unStick(sticky, false)) {
				throw new AssertionError();
			}
		}
		update();
	}
	
	public default void unStick() {
		for(Sticky sticky : getAllStickies().values()) {
			if(!unStick(sticky, false)) {
				throw new AssertionError();
			}
		}
		update();
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
