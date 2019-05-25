package com.gamebuster19901.halo.client.item.capability.reticle;

import com.gamebuster19901.halo.common.item.capability.shootable.Shootable;
import com.gamebuster19901.halo.common.item.capability.shootable.ShootableDefaultImpl;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

public class ReticleDefaultImpl extends Gui implements Reticle{

	@CapabilityInject(Reticle.class)
	public static Capability<Reticle> CAPABILITY = null;
	public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation(DEFAULT_DIRECTORY + "reticles.png");

	@Override
	public int width() {
		return 11;
	}

	@Override
	public int height() {
		return 11;
	}

	@Override
	public ResourceLocation getImage() {
		return DEFAULT_IMAGE;
	}

	@Override
	public void render(float partialTicks, int scaledWidth, int scaledHeight) {
		GameSettings gameSettings = mc.gameSettings;
		if(gameSettings.thirdPersonView == 0) {
			bind();
			this.drawTexturedModalRect((scaledWidth / 2) - 6, (scaledHeight / 2) - 6, 0, 0, 12, 12);
			unbind();
		}
	}
	
	@Override
	public void render(ItemStack item, float partialTicks, int scaledWidth, int scaledHeight) {
		render(partialTicks, scaledWidth, scaledHeight);
		int x = scaledWidth / 2 - 1;
		int y = scaledHeight / 2 - 2;
		LazyOptional<Shootable> shootableCapability = item.getCapability(ShootableDefaultImpl.CAPABILITY);
		if(shootableCapability.isPresent()) {
			Shootable shootable = shootableCapability.orElseThrow(AssertionError::new);
			int offset1 = 7;
			int offset2 = -2;
			int bloom = MathHelper.ceil(shootable.getBloom());
			this.drawTexturedModalRect(x + offset1 + bloom, y + offset2, 5, 0, 2, 8);
			this.drawTexturedModalRect(x - offset1 - bloom, y + offset2, 5, 0, 2, 8);
			this.drawTexturedModalRect(x + offset2 - 1, y + 1 + offset1 + bloom, 0, 5, 8, 2);
			this.drawTexturedModalRect(x + offset2 - 1, y + 1 - offset1 - bloom, 0, 5, 8, 2);
		}
	}
	
}
