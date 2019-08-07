/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test;

import com.gamebuster19901.guncore.capability.common.tracker.Tracker;
import com.gamebuster19901.guncore.capability.common.tracker.TrackerDefaultImpl;
import com.gamebuster19901.guncore.capability.common.tracker.TrackerDefaultProvider;
import com.gamebuster19901.guncore.capability.common.tracker.context.EntityTrackingContext;
import com.gamebuster19901.guncore.common.util.EasyLocalization;
import com.gamebuster19901.guncore.test.command.TrackingCommand;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class tracker_test extends Test{

	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> e) {
		Entity entity = e.getObject();
		if(entity instanceof PlayerEntity) {
			e.addCapability(EasyLocalization.getResourceLocation("guncore", Tracker.class), new TrackerDefaultProvider(entity, 10, 20));
		}
	}
	
	@SubscribeEvent
	public void onDamage(LivingAttackEvent e) {
		DamageSource source = e.getSource();
		Entity damaged = e.getEntity();
		Entity damager = source.getTrueSource();
		if(damager == null) {
			damager = source.getImmediateSource();
		}
		if(damaged != null && damager != null) {
			if(damager.getCapability(TrackerDefaultImpl.CAPABILITY).isPresent()) {
				Tracker tracker = damager.getCapability(TrackerDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
				EntityTrackingContext trackingContext = new EntityTrackingContext(damager, damaged);
		    	if(tracker.canTrack(trackingContext)){
		    		tracker.track(trackingContext);
		    	}
			}
		}
	}
	
	@SubscribeEvent
	public void onServerStart(FMLServerStartingEvent e) {
		TrackingCommand.register(e.getCommandDispatcher());
	}
	
	@Override
	public boolean test(Object... parameters) {
		return true;
	}

}
