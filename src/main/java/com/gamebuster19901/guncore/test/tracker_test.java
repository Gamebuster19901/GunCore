/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test;

import com.gamebuster19901.guncore.capability.common.tracker.Tracker;
import com.gamebuster19901.guncore.capability.common.tracker.TrackerDefaultProvider;
import com.gamebuster19901.guncore.capability.common.tracker.impl.TrackerBaseImpl;
import com.gamebuster19901.guncore.common.util.Resourced;
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
			e.addCapability(Resourced.getResourceLocation("guncore", Tracker.class), new TrackerDefaultProvider(entity));
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
			if(damager.getCapability(TrackerBaseImpl.CAPABILITY).isPresent()) {
				Tracker tracker = damager.getCapability(TrackerBaseImpl.CAPABILITY).orElseThrow(AssertionError::new);
				tracker.track(damaged);
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
