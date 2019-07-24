/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.sticky;

import javax.annotation.Nullable;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.stickable.Stickable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class StickyDefaultImpl implements Sticky{

	@CapabilityInject(Sticky.class)
	public static Capability<Sticky> CAPABILITY = null;
	/**
	 * Do not serialize/deserialize this! The object that this is sticking to should
	 * call stick(this) to set this when it is desrializing avoid a stackoverflow
	 */
	private transient Object stuckTo;
	
	@Override
	public boolean canStick(Object o) {
		if(o instanceof Stickable) {
			if (((Stickable) o).canBeStuckBy(this)){
				stuckTo = o;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean stick(Object o) {
		if(o instanceof Stickable) {
			if(((Stickable) o).stick(this)) {
				stuckTo = o;
				return true;
			}
		}
		return false;
	}

	@Override
	public void unStick(Object o) {
		if(stuckTo != o) {
			Main.LOGGER.warn("Unstuck from wrong object " + o + " was actually stuck to " + stuckTo);
			if(stuckTo instanceof Stickable) {
				((Stickable) stuckTo).unStick(this);
			}
		}
		stuckTo = null;
	}

	@Override
	public void unStick() {
		if(stuckTo instanceof Stickable) {
			((Stickable) stuckTo).unStick(this);
		}
		stuckTo = null;
	}
	
	@Override
	@Nullable
	public Object getObjectStuckTo() {
		return stuckTo;
	}

	@Override
	public void update(Object... data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CompoundNBT serializeNBT() {
		return new CompoundNBT();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {}

}
