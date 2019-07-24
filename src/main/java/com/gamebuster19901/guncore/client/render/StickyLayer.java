/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.client.render;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public class StickyLayer <T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M>{

	public StickyLayer(IEntityRenderer<T, M> entityRendererIn) {
		super(entityRendererIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_,
			float p_212842_6_, float p_212842_7_, float p_212842_8_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
