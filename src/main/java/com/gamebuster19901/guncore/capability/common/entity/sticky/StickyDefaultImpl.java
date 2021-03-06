/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.entity.sticky;

import javax.annotation.Nullable;

import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultImpl;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StickyDefaultImpl implements Sticky{

	@CapabilityInject(Sticky.class)
	public static Capability<Sticky> CAPABILITY = null;
	
	private Stickable stuckTo;
	
	private Entity entity;
	
	@Override
	public boolean canStick(Entity e) {
		if(e.getCapability(StickableDefaultImpl.CAPABILITY).isPresent()) {
			Stickable stickable = e.getCapability(StickableDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
			if (stickable.canBeStuckBy(this)){
				stuckTo = stickable;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean stick(Entity e) {
		if(e.getCapability(StickableDefaultImpl.CAPABILITY).isPresent()) {
			Stickable stickable = e.getCapability(StickableDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
			if(stickable.stick(this)) {
				stuckTo = stickable;
				entity.remove(true);
				return true;
			}
		}
		return false;
	}

	@Override
	public void unStick(boolean origin) {
		if(origin && stuckTo != null) {
			stuckTo.unStick(this, true);
		}
		stuckTo = null;
	}
	
	@Override
	@Nullable
	public Stickable getObjectStuckTo() {
		return stuckTo;
	}
	

	@Override
	public Entity getStickyEntity() {
		return entity;
	}
	
	@Override
	public void setEntity(Entity e) {
		entity = e;
	}

	@Override
	public void update(Object... data) {
		
	}
	
	@Override
	public void onTick(Object... data) {
		if(data.length == 1) {
			if(data[0] instanceof Stickable) {
				if(!(data[0] == stuckTo)) {
					throw new AssertionError();
				}
				Entity stuckTo = this.stuckTo.getEntity();
				if(stuckTo == null) {
					throw new AssertionError();
				}
				entity.setPositionAndRotation(stuckTo.posX, stuckTo.posY, stuckTo.posZ, stuckTo.rotationYaw, stuckTo.rotationPitch);
				entity.tick();
			}
		}
	}
	
	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent e) {
		Entity projectile = e.getEntity();
		if(projectile.getCapability(CAPABILITY).isPresent()) {
			Sticky sticky = projectile.getCapability(CAPABILITY).orElseThrow(AssertionError::new);
			RayTraceResult rayTrace = e.getRayTraceResult();
		    RayTraceResult.Type type = rayTrace.getType();
		    if (type == RayTraceResult.Type.ENTITY) {
		    	Entity entity = ((EntityRayTraceResult)rayTrace).getEntity();
		    	if(sticky.canStick(entity)) {
		    		sticky.stick(entity);
		    	}
		    }
		}
	}
	
}
