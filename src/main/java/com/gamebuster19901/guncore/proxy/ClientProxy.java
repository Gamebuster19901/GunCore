/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.proxy;

import java.lang.reflect.Field;

import com.gamebuster19901.guncore.client.render.RenderHelper;
import com.gamebuster19901.guncore.client.render.StickyLayer;
import com.gamebuster19901.guncore.common.util.ForgeReflectionHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends Proxy{

	public ClientProxy() {
		super();
	}
	
	private static final Field PLAYER_RENDERER = ForgeReflectionHelper.findField(EntityRendererManager.class, "playerRenderer");
	
	@SubscribeEvent
	@SuppressWarnings("unused")
	public void clientSetup(FMLClientSetupEvent e){
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(RenderHelper.class);
		
		EntityRendererManager manager = Minecraft.getInstance().getRenderManager();
		
		for(Class<? extends Entity> entityClass: manager.renderers.keySet()) {
			EntityRenderer renderer = manager.getRenderer(entityClass);
			if(renderer instanceof LivingRenderer) {
				LivingRenderer livingRenderer = (LivingRenderer) renderer;
				if(PlayerEntity.class.isAssignableFrom(entityClass)) { //sanity check
					RenderHelper.replaceArrowLayerRenderer((PlayerRenderer) livingRenderer);
				}
				else {
					livingRenderer.addLayer(new StickyLayer(livingRenderer));
				}
			}
		}
		
		try {
			RenderHelper.replaceArrowLayerRenderer((PlayerRenderer) PLAYER_RENDERER.get(manager));
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new AssertionError(exception);
		}
		
	}
}
