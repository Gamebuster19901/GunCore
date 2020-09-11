/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.overlay;

import com.gamebuster19901.guncore.client.render.Renderer;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.util.ResourceLocation;

public interface Overlay extends Renderer{
	public int width();
	
	public int height();
	
	public ResourceLocation getImage();
	
	@Override
	public default void bind() {
		GlStateManager.pushMatrix();
		mc.textureManager.bindTexture(getImage());
		GlStateManager.enableBlend();
		GlStateManager.enableAlphaTest();
	}
	
	@Override
	public default void unbind() {
		GlStateManager.disableBlend();
		GlStateManager.disableAlphaTest();
		GlStateManager.popMatrix();
	}
}
