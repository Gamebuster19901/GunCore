/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test;

import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultProvider;
import com.gamebuster19901.guncore.capability.common.entity.sticky.Sticky;
import com.gamebuster19901.guncore.capability.common.entity.sticky.StickyDefaultProvider;
import com.gamebuster19901.guncore.common.entity.StickyProjectile;
import com.gamebuster19901.guncore.common.util.EasyLocalization;
import com.gamebuster19901.guncore.test.command.StickCommand;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@SuppressWarnings("all")
public class sticky_test extends Test{

	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> e) {
		Entity entity = e.getObject();
		if(!(entity instanceof LivingEntity || entity instanceof PlayerEntity)) {
			e.addCapability(EasyLocalization.getResourceLocation("guncore", Stickable.class), new StickableDefaultProvider(entity));
		}
		
		if(!(entity instanceof StickyProjectile || entity instanceof ArrowEntity)) {
			e.addCapability(EasyLocalization.getResourceLocation("guncore", Sticky.class), new StickyDefaultProvider(entity));
		}
	}
	
	@SubscribeEvent
	public void onServerStart(FMLServerStartingEvent e) {
		StickCommand.register(e.getCommandDispatcher());
	}

	@Override
	public boolean test(Object... parameters) {
		return true;
	}
	
}
