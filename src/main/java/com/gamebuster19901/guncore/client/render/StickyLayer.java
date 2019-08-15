/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.client.render;

import java.util.Random;

import com.gamebuster19901.guncore.capability.common.entity.stickable.Stickable;
import com.gamebuster19901.guncore.capability.common.entity.stickable.StickableDefaultImpl;
import com.gamebuster19901.guncore.capability.common.entity.sticky.Sticky;
import com.google.common.collect.ImmutableMultimap;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class StickyLayer <T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M>{

	private final EntityRendererManager RENDER_MANAGER = Minecraft.getInstance().getRenderManager();
	
	public StickyLayer(IEntityRenderer<T, M> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(T entityIn, float unknown1, float unknown2, float partialTicks, float unknown4, float unknown5, float unknown6, float unknown7) {
		if(entityIn.getCapability(StickableDefaultImpl.CAPABILITY).isPresent()) {
			Stickable stickable = entityIn.getCapability(StickableDefaultImpl.CAPABILITY).orElseThrow(AssertionError::new);
			ImmutableMultimap<Class<? extends Sticky>, Sticky> stickies = stickable.getAllStickies();
			for(Sticky sticky : stickies.values()) {
				Entity entity = (Entity) sticky.getStickyEntity();
				Random random = new Random(sticky.getStickyEntity().getUniqueID().getMostSignificantBits());
				
				GlStateManager.pushMatrix();
				RendererModel rendererModel = this.getEntityModel().getRandomModelBox(random);
				ModelBox modelBox = rendererModel.cubeList.get(random.nextInt(rendererModel.cubeList.size()));
				rendererModel.postRender(0.0625f);
				
				float x = random.nextFloat();
				float y = random.nextFloat();
				float z = random.nextFloat();
				float x2 = MathHelper.lerp(x, modelBox.posX1, modelBox.posX2) / 16f;
				float y2 = MathHelper.lerp(y, modelBox.posY1, modelBox.posY2) / 16f;
				float z2 = MathHelper.lerp(z, modelBox.posZ1, modelBox.posZ2) / 16f;
				
				GlStateManager.translatef(x2, y2, z2);
				
				x = -((x * 2) - 1);
				y = -((y * 2) - 1);
				z = -((z * 2) - 1);
				
				float distance = MathHelper.sqrt(x * x + z * z);
				
				entity.rotationYaw = (float)(Math.atan2((double)x, (double)z) * (180d / Math.PI));
				entity.rotationPitch = (float)(Math.atan2((double)y, (double)distance) * (180d / Math.PI));
				entity.prevRotationYaw = entity.rotationYaw;
				entity.prevRotationPitch = entity.rotationPitch;
				
				RENDER_MANAGER.renderEntity(entity, 0, 0, 0, 0, partialTicks, false);
				
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
