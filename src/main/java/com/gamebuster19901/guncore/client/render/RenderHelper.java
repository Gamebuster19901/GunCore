/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.client.render;

import com.gamebuster19901.guncore.capability.client.item.overlay.Overlay;
import com.gamebuster19901.guncore.capability.client.item.overlay.OverlayDefaultImpl;
import com.gamebuster19901.guncore.capability.client.item.reticle.Reticle;
import com.gamebuster19901.guncore.capability.client.item.reticle.ReticleDefaultImpl;
import com.gamebuster19901.guncore.capability.common.energy.Energy;
import com.gamebuster19901.guncore.capability.common.energy.EnergyDefaultImpl;
import com.gamebuster19901.guncore.capability.common.item.reloadable.Reloadable;
import com.gamebuster19901.guncore.capability.common.item.reloadable.ReloadableDefaultImpl;
import com.gamebuster19901.guncore.capability.common.item.weapon.Weapon;
import com.gamebuster19901.guncore.capability.common.item.weapon.WeaponDefaultImpl;
import com.gamebuster19901.guncore.common.item.abstracts.HeldWeapon;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHelper{
	public static final Minecraft mc = Minecraft.getInstance();
	
	@SubscribeEvent
	public static void onRender(RenderGameOverlayEvent.Pre e) {
		if(e.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
			ItemStack stack = mc.player.getHeldItemMainhand();
			LazyOptional<Weapon> weaponCapability = stack.getCapability(WeaponDefaultImpl.CAPABILITY);
			LazyOptional<Reticle> reticleCapability = stack.getCapability(ReticleDefaultImpl.CAPABILITY);
			if(weaponCapability.isPresent() && (mc.player.isSprinting() && !weaponCapability.orElseThrow(AssertionError::new).canFire(mc.player))) {
				//TODO: render sprinting reticle
				e.setCanceled(true);
			}
			else if(reticleCapability.isPresent()) {
				reticleCapability.orElseThrow(AssertionError::new).render(e, stack, e.getPartialTicks(), mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight());
				e.setCanceled(true);
			}
			return;
		}
		else if (e.getType() == RenderGameOverlayEvent.ElementType.CHAT) {
			ItemStack stack = mc.player.getHeldItemMainhand();
			LazyOptional<Reloadable> reloadableCapability = stack.getCapability(ReloadableDefaultImpl.CAPABILITY);
			LazyOptional<Energy> energyCapability = stack.getCapability(EnergyDefaultImpl.CAPABILITY);
			LazyOptional<Overlay> overlayCapability = stack.getCapability(OverlayDefaultImpl.CAPABILITY);
			if(overlayCapability.isPresent()) {
				if(reloadableCapability.isPresent() || energyCapability.isPresent()) {
					overlayCapability.orElseThrow(AssertionError::new).render(e, stack, e.getPartialTicks(), mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight());
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onHandRender(RenderSpecificHandEvent e) {
		EnumHand hand = e.getHand();
		if(mc.player.getHeldItem(hand).getItem() instanceof HeldWeapon) {
			e.setCanceled(true);
			mc.getFirstPersonRenderer().renderItemInFirstPerson(mc.player, e.getPartialTicks(), e.getInterpolatedPitch(), hand, e.getSwingProgress(), e.getItemStack(), 0f);
		}
	}
}
