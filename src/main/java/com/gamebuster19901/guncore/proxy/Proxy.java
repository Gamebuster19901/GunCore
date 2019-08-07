/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.proxy;

import java.util.HashSet;
import java.util.function.Consumer;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.client.item.overlay.Overlay;
import com.gamebuster19901.guncore.capability.client.item.overlay.OverlayFactory;
import com.gamebuster19901.guncore.capability.client.item.overlay.OverlayStorage;
import com.gamebuster19901.guncore.capability.client.item.reticle.Reticle;
import com.gamebuster19901.guncore.capability.client.item.reticle.ReticleFactory;
import com.gamebuster19901.guncore.capability.client.item.reticle.ReticleStorage;
import com.gamebuster19901.guncore.capability.common.energy.Energy;
import com.gamebuster19901.guncore.capability.common.energy.EnergyFactory;
import com.gamebuster19901.guncore.capability.common.energy.EnergyStorage;
import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwner;
import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwnerFactory;
import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwnerStorage;
import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
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
import com.gamebuster19901.guncore.common.util.EasyLocalization;
import com.gamebuster19901.guncore.test.Test;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class Proxy {
	
	HashSet<Ammo> registeredAmmo = new HashSet<Ammo>();
	HashSet<Class<? extends Entity>> registeredProjectile = new HashSet<Class<? extends Entity>>();
	
	public Proxy() {
		getBus().register(this);
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
		
		for(Test t : Main.getTests()) {
			MinecraftForge.EVENT_BUS.register(t);
		}
	}
	
	protected static IEventBus getBus() {
		return FMLJavaModLoadingContext.get().getModEventBus();
	}
	
	protected <T extends Event> void addListener(Consumer<T> consumer) {
		getBus().addListener(consumer);
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
			e.addCapability(EasyLocalization.getResourceLocation("guncore", Stickable.class), new StickableDefaultProvider(entity));
		}
		
		if(entity instanceof StickyProjectile || entity instanceof ArrowEntity) {
			e.addCapability(EasyLocalization.getResourceLocation("guncore", Sticky.class), new StickyDefaultProvider(entity));
		}
	}
	
}
