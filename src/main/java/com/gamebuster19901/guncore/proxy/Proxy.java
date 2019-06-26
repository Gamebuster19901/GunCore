package com.gamebuster19901.guncore.proxy;

import java.util.HashSet;
import java.util.function.Consumer;

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
import com.gamebuster19901.guncore.common.item.abstracts.Ammo;
import com.gamebuster19901.guncore.common.item.abstracts.Projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
		CapabilityManager.INSTANCE.register(Weapon.class, new WeaponStorage(), new WeaponFactory());
		CapabilityManager.INSTANCE.register(Shootable.class, new ShootableStorage(), new ShootableFactory());
		CapabilityManager.INSTANCE.register(Reloadable.class,  new ReloadableStorage(),  new ReloadableFactory());
		CapabilityManager.INSTANCE.register(ShooterOwner.class, new ShooterOwnerStorage(), new ShooterOwnerFactory());
		CapabilityManager.INSTANCE.register(Reticle.class, new ReticleStorage(), new ReticleFactory());
		CapabilityManager.INSTANCE.register(Overlay.class, new OverlayStorage(), new OverlayFactory());
		CapabilityManager.INSTANCE.register(Energy.class, new EnergyStorage(), new EnergyFactory());
		CapabilityManager.INSTANCE.register(Overheat.class, new OverheatStorage(), new OverheatFactory());
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
	
}
