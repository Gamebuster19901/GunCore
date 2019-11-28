/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.proxy;

import java.util.HashSet;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.client.item.overlay.Overlay;
import com.gamebuster19901.guncore.capability.client.item.overlay.OverlayFactory;
import com.gamebuster19901.guncore.capability.client.item.overlay.OverlayStorage;
import com.gamebuster19901.guncore.capability.client.item.reticle.Reticle;
import com.gamebuster19901.guncore.capability.client.item.reticle.ReticleFactory;
import com.gamebuster19901.guncore.capability.client.item.reticle.ReticleStorage;
import com.gamebuster19901.guncore.capability.common.charge.Charge;
import com.gamebuster19901.guncore.capability.common.charge.ChargeFactory;
import com.gamebuster19901.guncore.capability.common.charge.ChargeStorage;
import com.gamebuster19901.guncore.capability.common.emp.EMPable;
import com.gamebuster19901.guncore.capability.common.emp.EMPableFactory;
import com.gamebuster19901.guncore.capability.common.emp.EMPableStorage;
import com.gamebuster19901.guncore.capability.common.energy.Energy;
import com.gamebuster19901.guncore.capability.common.energy.EnergyFactory;
import com.gamebuster19901.guncore.capability.common.energy.EnergyStorage;
import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwner;
import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwnerFactory;
import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwnerStorage;
import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultImpl;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultProvider;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableFactory;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableStorage;
import com.gamebuster19901.guncore.capability.common.entity.sticky.Sticky;
import com.gamebuster19901.guncore.capability.common.entity.sticky.StickyDefaultImpl;
import com.gamebuster19901.guncore.capability.common.entity.sticky.StickyDefaultProvider;
import com.gamebuster19901.guncore.capability.common.entity.sticky.StickyFactory;
import com.gamebuster19901.guncore.capability.common.entity.sticky.StickyStorage;
import com.gamebuster19901.guncore.capability.common.heat.Overheat;
import com.gamebuster19901.guncore.capability.common.heat.OverheatFactory;
import com.gamebuster19901.guncore.capability.common.heat.OverheatStorage;
import com.gamebuster19901.guncore.capability.common.item.reloadable.Reloadable;
import com.gamebuster19901.guncore.capability.common.item.reloadable.ReloadableFactory;
import com.gamebuster19901.guncore.capability.common.item.reloadable.ReloadableStorage;
import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;
import com.gamebuster19901.guncore.capability.common.item.shootable.ShootableFactory;
import com.gamebuster19901.guncore.capability.common.item.shootable.ShootableStorage;
import com.gamebuster19901.guncore.capability.common.item.weapon.Weapon;
import com.gamebuster19901.guncore.capability.common.item.weapon.WeaponFactory;
import com.gamebuster19901.guncore.capability.common.item.weapon.WeaponStorage;
import com.gamebuster19901.guncore.capability.common.tracker.Tracker;
import com.gamebuster19901.guncore.capability.common.tracker.TrackerFactory;
import com.gamebuster19901.guncore.capability.common.tracker.TrackerStorage;
import com.gamebuster19901.guncore.common.entity.StickyProjectile;
import com.gamebuster19901.guncore.common.item.abstracts.Ammo;
import com.gamebuster19901.guncore.common.item.abstracts.Projectile;
import com.gamebuster19901.guncore.common.util.Resourced;
import com.gamebuster19901.guncore.network.Network;
import com.gamebuster19901.guncore.test.Test;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class Proxy {
	
	HashSet<Ammo> registeredAmmo = new HashSet<Ammo>();
	HashSet<Class<? extends Entity>> registeredProjectile = new HashSet<Class<? extends Entity>>();
	
	public Proxy() {
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}
	
	@SubscribeEvent
	@SuppressWarnings("unused")
	public void setup(FMLCommonSetupEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(Projectile.class);
		MinecraftForge.EVENT_BUS.register(StickyDefaultImpl.class);
		CapabilityManager.INSTANCE.register(Weapon.class, new WeaponStorage(), new WeaponFactory());
		CapabilityManager.INSTANCE.register(Shootable.class, new ShootableStorage(), new ShootableFactory());
		CapabilityManager.INSTANCE.register(Reloadable.class,  new ReloadableStorage(),  new ReloadableFactory());
		CapabilityManager.INSTANCE.register(ShooterOwner.class, new ShooterOwnerStorage(), new ShooterOwnerFactory());
		CapabilityManager.INSTANCE.register(Reticle.class, new ReticleStorage(), new ReticleFactory());
		CapabilityManager.INSTANCE.register(Overlay.class, new OverlayStorage(), new OverlayFactory());
		CapabilityManager.INSTANCE.register(Stickable.class, new StickableStorage(), new StickableFactory());
		CapabilityManager.INSTANCE.register(Sticky.class, new StickyStorage(), new StickyFactory());
		CapabilityManager.INSTANCE.register(Energy.class, new EnergyStorage(), new EnergyFactory());
		CapabilityManager.INSTANCE.register(Overheat.class, new OverheatStorage(), new OverheatFactory());
		CapabilityManager.INSTANCE.register(Tracker.class, new TrackerStorage(), new TrackerFactory());
		CapabilityManager.INSTANCE.register(EMPable.class, new EMPableStorage(), new EMPableFactory());
		CapabilityManager.INSTANCE.register(Charge.class, new ChargeStorage(), new ChargeFactory());
		Network.register();
		
		for(Test t : Main.getTests()) {
			MinecraftForge.EVENT_BUS.register(t);
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		registerAmmo(event);
	}
	
	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {

	}
	
	private void registerAmmo(RegistryEvent.Register<Item> event) {

	}
	
	public void registerAmmo(RegistryEvent.Register<Item> event, Ammo ammo) {
		registeredAmmo.add(ammo);
		event.getRegistry().register(ammo);
	}
	
	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> e) {
		
	}
	
	@SubscribeEvent
	public void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> e) {
		Entity entity = e.getObject();
		if(entity instanceof LivingEntity || entity instanceof PlayerEntity) {
			e.addCapability(Resourced.getResourceLocation("guncore", Stickable.class), new StickableDefaultProvider(entity));
		}
		
		if(entity instanceof StickyProjectile || entity instanceof ArrowEntity) {
			e.addCapability(Resourced.getResourceLocation("guncore", Sticky.class), new StickyDefaultProvider(entity));
		}
	}
	
	@SubscribeEvent
	public void onStartTracking(PlayerEvent.StartTracking e) {
		Entity target = e.getTarget();
		if(!target.world.isRemote) {
			if(target.isAlive()) {
				LazyOptional<Stickable> stickableCapability = target.getCapability(StickableDefaultImpl.CAPABILITY);
				if(stickableCapability.isPresent()) {
					stickableCapability.orElseThrow(AssertionError::new).update();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent e) {
		if(!e.world.isRemote && e.phase == TickEvent.Phase.END) {
			
			ServerWorld world = (ServerWorld) e.world;
			
			world.getEntities().forEachOrdered(entity -> {
				LazyOptional<Stickable> stickableCapability = entity.getCapability(StickableDefaultImpl.CAPABILITY);
				if(stickableCapability.isPresent()) {
					stickableCapability.orElseThrow(AssertionError::new).onTick();
				}
			});
			
		}
	}
	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent e) {
		Entity entity = e.getEntity();
		if(!entity.world.isRemote) {
			LazyOptional<Sticky> stickyCapability = entity.getCapability(StickyDefaultImpl.CAPABILITY);
			LazyOptional<Stickable> stickableCapability = entity.getCapability(StickableDefaultImpl.CAPABILITY);
			
			if(stickyCapability.isPresent()) {
				stickyCapability.orElseThrow(AssertionError::new).unStick(true);
			}
			if(stickableCapability.isPresent()) {
				stickableCapability.orElseThrow(AssertionError::new).unStick();
			}
		}
	}
	
}
