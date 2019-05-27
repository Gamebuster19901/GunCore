package com.gamebuster19901.halo.client.render;

import com.gamebuster19901.halo.client.item.capability.reticle.Reticle;
import com.gamebuster19901.halo.client.item.capability.reticle.ReticleDefaultImpl;
import com.gamebuster19901.halo.common.item.abstracts.HeldWeapon;

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
			LazyOptional<Reticle> reticleCapability = stack.getCapability(ReticleDefaultImpl.CAPABILITY);
			if(reticleCapability.isPresent()) {
				reticleCapability.orElseThrow(AssertionError::new).render(stack, e.getPartialTicks(), mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight());
				e.setCanceled(true);
			}
			return;
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
