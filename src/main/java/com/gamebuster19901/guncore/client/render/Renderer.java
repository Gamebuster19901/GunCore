/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.client.render;

import static com.gamebuster19901.guncore.Main.MODID;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public interface Renderer {
	public static final String DEFAULT_DIRECTORY = MODID + ":textures/gui/standard/";
	public static final Minecraft mc = Minecraft.getInstance();
	
	public void render(float partialTicks, int scaledWidth, int scaledHeight);
	
	public void render(ItemStack stack, float partialTicks, int scaledWidth, int scaledHeight);
	
	public void render(RenderGameOverlayEvent.Pre e, ItemStack stack, float partialTicks, int scaledWidth, int scaledHeight);
	
	public void bind();
	
	public void unbind();
}
