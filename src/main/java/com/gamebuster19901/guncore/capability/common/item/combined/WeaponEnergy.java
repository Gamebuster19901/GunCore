/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.combined;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.client.item.overlay.Overlay;
import com.gamebuster19901.guncore.capability.client.item.overlay.OverlayDefaultImpl;
import com.gamebuster19901.guncore.capability.client.item.reticle.Reticle;
import com.gamebuster19901.guncore.capability.client.item.reticle.ReticleDefaultImpl;
import com.gamebuster19901.guncore.capability.common.energy.Energy;
import com.gamebuster19901.guncore.capability.common.item.weapon.Weapon;
import com.gamebuster19901.guncore.proxy.ClientProxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class WeaponEnergy implements Weapon, Energy, Reticle, Overlay{

	protected Weapon weapon;
	protected Energy energy;
	protected Reticle reticle;
	protected Overlay overlay;
	
	{
		if(Main.proxy instanceof ClientProxy) {
			reticle = new ReticleDefaultImpl();
			overlay = new OverlayDefaultImpl();
		}
	}
	
	public WeaponEnergy(Object... capabilityImplementations) {
		for(Object o : capabilityImplementations) {
			if(o == null) {
				throw new NullPointerException();
			}
			if(o instanceof Weapon) {
				if(weapon == null) {
					weapon = (Weapon)o;
				}
				else {
					throw new IllegalArgumentException("Weapon instance specified twice");
				}
			}
			if(o instanceof Energy) {
				if(energy == null) {
					energy = (Energy)o;
				}
				else {
					throw new IllegalArgumentException("Energy instance specified twice");
				}
			}
			if(o instanceof Reticle) {
				reticle = (Reticle) o;
			}
			if(o instanceof Overlay) {
				overlay = (Overlay) o;
			}
		}
		
		if(weapon == null || energy == null) {
			String err = "The following types were not specified :";
			
			if(weapon == null) {
				err = err + "weapon,";
			}
			if(energy == null) {
				err = err + "energy,";
			}
			
			err = err.substring(err.lastIndexOf(','));
			
			throw new IllegalArgumentException(err);
		}
	}
	
	@Override
	public void onTick(Object... data) {
		weapon.onTick(data);
		energy.onTick(data);
	}
	
	@Override
	public void update(Object... data) {
		weapon.update(data);
		energy.update(data);
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = (CompoundNBT) weapon.serializeNBT();
		try {
			nbt.merge(energy.serializeNBT());
		}
		catch(Exception e) {
			Main.LOGGER.catching(e);
			throw e;
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		weapon.deserializeNBT(nbt);
		energy.deserializeNBT(nbt);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return energy.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return energy.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return energy.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return energy.getMaxEnergyStored();
	}

	@Override
	public boolean canExtract() {
		return energy.canExtract();
	}

	@Override
	public boolean canReceive() {
		return energy.canReceive();
	}

	@Override
	public void render(Pre e, ItemStack stack, float partialTicks, int scaledWidth, int scaledHeight) {
		if(overlay != null && e.getType() == ElementType.CHAT) {
			overlay.render(stack, partialTicks, scaledWidth, scaledHeight);
		}
		if(reticle != null && e.getType() == ElementType.CROSSHAIRS) {
			reticle.render(stack, partialTicks, scaledWidth, scaledHeight);
		}
	}
	
	@Override
	@Deprecated
	public void render(ItemStack stack, float partialTicks, int scaledWidth, int scaledHeight) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void render(float partialTicks, int scaledWidth, int scaledHeight) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public int width() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public int height() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public ResourceLocation getImage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int setEnergy(int energy) {
		return this.energy.setEnergy(energy);
	}

	@Override
	public void setCapacity(int capacity) {
		energy.setCapacity(capacity);
	}

	@Override
	public void setCanReceive(boolean canReceive) {
		energy.setCanReceive(canReceive);
	}

	@Override
	public void setMaxReceive(int maxReceive) {
		energy.setMaxReceive(maxReceive);
	}

	@Override
	public int getMaxReceive() {
		return energy.getMaxReceive();
	}

	@Override
	public void setMaxExtract(int maxExtract) {
		energy.setMaxExtract(maxExtract);
	}

	@Override
	public int getMaxExtract() {
		return energy.getMaxExtract();
	}

	@Override
	public void setCanExtract(boolean canExtract) {
		energy.setCanExtract(canExtract);
	}

	@Override
	public boolean canFire(Entity shooter) {
		if (weapon.canFire(shooter)) {
			if(shooter instanceof PlayerEntity && ((PlayerEntity) shooter).isCreative()) {
				return this.getTimeUntilNextFire() <= 0;
			}
			return this.getEnergyStored() > 0 && this.getTimeUntilNextFire() <= 0;
		}
		return false;
	}

	@Override
	public void fire(Entity shooter) {
		weapon.fire(shooter);
		if(shooter instanceof PlayerEntity && ((PlayerEntity)shooter).isCreative()) {
			return;
		}
		energy.extractEnergy(getMaxExtract(), false);
	}

	@Override
	public int getFireRate() {
		return weapon.getFireRate();
	}

	@Override
	public void setFireRate(int rate) {
		weapon.setFireRate(rate);
	}

	@Override
	public byte getTimeUntilNextFire() {
		return weapon.getTimeUntilNextFire();
	}

	@Override
	public void setTimeUntilNextFire(byte time) {
		weapon.setTimeUntilNextFire(time);
	}

	@Override
	public boolean isAutomatic() {
		return weapon.isAutomatic();
	}

	@Override
	public void setAutomatic(boolean isAutomatic) {
		weapon.setAutomatic(isAutomatic);
	}

}
