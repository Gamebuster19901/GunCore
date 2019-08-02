/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.sticky;

import com.gamebuster19901.guncore.common.util.Updateable;

import net.minecraft.entity.Entity;

public interface Sticky extends Updateable{

	public boolean canStick(Entity e);
	
	public boolean stick(Entity e);
	
	public void unStick(Entity e);
	
	public void unStick();
	
	public Entity getObjectStuckTo();
	
	public Entity getStickyEntity();

	public void setEntity(Entity o);
	
}
