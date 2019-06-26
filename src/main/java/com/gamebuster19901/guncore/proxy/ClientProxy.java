package com.gamebuster19901.guncore.proxy;

import com.gamebuster19901.guncore.client.render.RenderHelper;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends Proxy{

	public ClientProxy() {
		super();
		addListener(this::clientSetup);
	}
	
	@SubscribeEvent
	@SuppressWarnings("unused")
	public void clientSetup(FMLClientSetupEvent e){
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(RenderHelper.class);
	}	
}
