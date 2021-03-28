/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.client.item.reticle;

import com.gamebuster19901.guncore.client.render.Renderer;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.util.ResourceLocation;

public interface Reticle extends Renderer{

	public int width();
	
	public int height();
	
	public ResourceLocation getImage();
	
	@Override
	public default void bind() {
		GlStateManager.pushMatrix();
		mc.textureManager.bindTexture(getImage());
		GlStateManager.enableBlend();
		GlStateManager.enableAlphaTest();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	}
	
	@Override
	public default void unbind() {
		GlStateManager.disableBlend();
		GlStateManager.disableAlphaTest();
		GlStateManager.popMatrix();
	}
	
}
