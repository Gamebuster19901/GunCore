/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.util;

import javax.annotation.Nullable;

import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public interface Updateable {
	
	public default void onTick(WorldTickEvent e) {
		if(canUpdate(e)) {
			update(e);
		}
	}
	
	@SuppressWarnings("unused")
	public default boolean canUpdate(WorldTickEvent e) {
		return true;
	}
	
	public void update(@Nullable WorldTickEvent e);
}
