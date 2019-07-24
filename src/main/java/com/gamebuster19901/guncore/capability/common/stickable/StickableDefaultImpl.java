/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.stickable;

import org.apache.logging.log4j.Level;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.sticky.Sticky;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class StickableDefaultImpl implements Stickable{

	@CapabilityInject(Stickable.class)
	public static Capability<Stickable> CAPABILITY;
	
	private Multimap<Class<? extends Sticky>, Sticky> sticks = HashMultimap.create();

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
	public boolean stick(Class<? extends Sticky> stickyType, Sticky sticky) {
		return sticks.put(stickyType, sticky);
	}

	@Override
	@Deprecated
	public boolean unStick(Class<? extends Sticky> stickyType, Sticky sticky) {
		return sticks.remove(stickyType, sticky);
	}

	@Override
	public ImmutableMultimap<Class<? extends Sticky>, Sticky> getAllStickies() {
		return ImmutableMultimap.copyOf(sticks);
	}

	@Override
	public void update(Object... data) {
		// TODO Auto-generated method stub
		
	}

	//TODO: Figure out a better way to do this, perhaps look at how riding entities are saved?
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		
		ListNBT sticks = new ListNBT();
		for(Class<? extends Sticky> stickyType : this.sticks.keySet()) {
			ListNBT list = new ListNBT();
			list.add(new StringNBT(stickyType.getCanonicalName()));
			for(Sticky sticky : this.sticks.get(stickyType)) {
				list.add(sticky.serializeNBT());
			}
			sticks.add(list);
		}
		
		nbt.put("sticks", sticks);
		
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		ListNBT sticks = nbt.getList("sticks", 9);
		for(int i = 0; i < sticks.size(); i++) {
			int j = 0;
			ListNBT list = sticks.getList(i);
			Class<? extends Sticky> stickyType;
			
			try {
				stickyType = (Class<? extends Sticky>) Class.forName(list.getString(j++));
				Sticky sticky = stickyType.newInstance();
				if(!sticky.stick(this)) {
					Main.LOGGER.warn("Could not stick sticky " + sticky + " to " + this + " even though it was serialized as such!");
				}
			} catch (ClassNotFoundException e) {
				Main.LOGGER.catching(Level.ERROR, e);
				Main.LOGGER.error("Perhaps a mod was removed?");
				continue;
			} catch (ClassCastException | InstantiationException | IllegalAccessException e) {
				ClassFormatError formatError = new ClassFormatError();
				formatError.initCause(e);
				throw formatError;
			}
		}
	}

}
