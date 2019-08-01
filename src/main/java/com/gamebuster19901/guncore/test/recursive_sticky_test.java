/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.test;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.common.stickable.Stickable;
import com.gamebuster19901.guncore.capability.common.stickable.StickableDefaultProvider;
import com.gamebuster19901.guncore.capability.common.sticky.Sticky;
import com.gamebuster19901.guncore.capability.common.sticky.StickyDefaultImpl;
import com.gamebuster19901.guncore.capability.common.sticky.StickyDefaultProvider;
import com.gamebuster19901.guncore.common.entity.StickyProjectile;
import com.gamebuster19901.guncore.common.util.EasyLocalization;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("all")
public class recursive_sticky_test extends Test{

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
	public void onDamage(LivingAttackEvent e) {
		DamageSource source = e.getSource();
		Entity damaged = e.getEntity();
		Entity damager = source.getTrueSource();
		if(damager == null) {
			damager = source.getImmediateSource();
		}
		if(damaged != null && damager != null) {
			Sticky sticky = damager.getCapability(StickyDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);;
	    	if(sticky.canStick(damaged)) {
	    		Main.LOGGER.info(sticky.stick(damaged));
	    	}
		}
	}

	@Override
	public boolean test(Object... parameters) {
		return true;
	}
	
}
