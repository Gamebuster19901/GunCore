/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.combined;

import java.util.Random;

import com.gamebuster19901.guncore.Main;
import com.gamebuster19901.guncore.capability.client.item.overlay.Overlay;
import com.gamebuster19901.guncore.capability.client.item.overlay.OverlayDefaultImpl;
import com.gamebuster19901.guncore.capability.client.item.reticle.Reticle;
import com.gamebuster19901.guncore.capability.client.item.reticle.ReticleDefaultImpl;
import com.gamebuster19901.guncore.capability.common.item.reloadable.Reloadable;
import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;
import com.gamebuster19901.guncore.capability.common.item.weapon.Weapon;
import com.gamebuster19901.guncore.common.item.NullAmmo;
import com.gamebuster19901.guncore.common.item.abstracts.Ammo;
import com.gamebuster19901.guncore.proxy.ClientProxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class WeaponShootableReloadableImpl implements Weapon, Shootable, Reloadable, Reticle, Overlay{
	
	protected Weapon weapon = null;
	protected Shootable shootable = null;
	protected Reloadable reloadable = null;
	protected Reticle reticle;
	protected Overlay overlay;
	
	{
		if(Main.proxy instanceof ClientProxy) {
			reticle = new ReticleDefaultImpl();
			overlay = new OverlayDefaultImpl();
		}
	}
	
	public WeaponShootableReloadableImpl(Object... capabilityImplementations) {
		for(Object o : capabilityImplementations) {
			if(o == null) {
				throw new NullPointerException();
			}
			if(o instanceof Weapon) {
				if(weapon == null) {
					weapon = (Weapon) o;
				}
				else {
					throw new IllegalArgumentException("Weapon instance specified twice!");
				}
			}
			if(o instanceof Shootable) {
				if(shootable == null) {
					shootable = (Shootable) o;
				}
				else {
					throw new IllegalArgumentException("Shootable instance specified twice!");
				}
			}
			if(o instanceof Reloadable) {
				if(reloadable == null) {
					reloadable = (Reloadable) o;
				}
				else {
					throw new IllegalArgumentException("Reloadable instance specified twice!");
				}
			}
			if(o instanceof Reticle) {
				reticle = (Reticle) o;
			}
			if(o instanceof Overlay) {
				overlay = (Overlay) o;
			}
		}
		
		if(weapon == null || shootable == null || reloadable == null) {
			String err = "The following types were not specified :";
	
			if(weapon == null) {
				err = err + "weapon, ";
			}
			if(shootable == null) {
				err = err + "shootable, ";
			}
			if(reloadable == null) {
				err = err + "reloadable, ";
			}
			
			err = err.substring(err.lastIndexOf(", \""));
			
			throw new IllegalArgumentException(err);
		}
		
	}

	@Override
	public int getMagazineSize() {
		return reloadable.getMagazineSize();
	}

	@Override
	public int getAmountInMagazine() {
		return reloadable.getAmountInMagazine();
	}

	@Override
	public void setAmountInMagazine(int amount) {
		reloadable.setAmountInMagazine(amount);
	}

	@Override
	public int getReloadTime() {
		return reloadable.getAmountInMagazine();
	}

	@Override
	public int getReloadProgress() {
		return reloadable.getReloadProgress();
	}

	@Override
	public Ammo getAmmoType() {
		return reloadable.getAmmoType();
	}
	
	@Override
	public void setAmmoType(Ammo ammo) {
		reloadable.setAmmoType(ammo);
		setProjectile(ammo.getProjectile());
	}

	@Override
	public boolean isValidAmmo(Ammo ammo) {
		return reloadable.isValidAmmo(ammo);
	}

	@Override
	public boolean isReloading() {
		return reloadable.isReloading();
	}

	@Override
	public Ammo reload(IInventory... containers) {
		Ammo ammo = reloadable.reload(containers);
		setProjectile(ammo.getProjectile());
		return ammo;
	}

	@Override
	public void addBloom(float bloom) {
		shootable.addBloom(bloom);
	}
	
	@Override
	public void setBloom(float bloom) {
		shootable.setBloom(bloom);
	}
	
	@Override
	public float getBloom() {
		return shootable.getBloom();
	}

	@Override
	public float getMaxBloom() {
		return shootable.getMaxBloom();
	}

	@Override
	public float getBloomIncreasePerShot() {
		return shootable.getBloomIncreasePerShot();
	}

	@Override
	public float getBloomDecreasePerTick() {
		return shootable.getBloomDecreasePerTick();
	}

	@Override
	public float getMuzzleVelocity() {
		return shootable.getMuzzleVelocity();
	}

	@Override
	public float getMinVerticalRecoil() {
		return shootable.getVerticalRecoil();
	}

	@Override
	public float getMaxVerticalRecoil() {
		return shootable.getMaxVerticalRecoil();
	}

	@Override
	public float getMinHorizontalRecoil() {
		return shootable.getMinHorizontalRecoil();
	}

	@Override
	public float getMaxHorizontalRecoil() {
		return shootable.getMaxHorizontalRecoil();
	}
	
	@Override
	public void setProjectile(CompoundNBT projectile) {
		shootable.setProjectile(projectile);
	}
	
	@Override
	public CompoundNBT getProjectile() {
		return getAmmoType().getProjectile();
	}

	@Override
	public Random getRandom() {
		return shootable.getRandom();
	}

	@Override
	public void setRandom(Random random) {
		shootable.setRandom(random);
	}

	@Override
	public boolean canFire(Entity shooter) {
		return weapon.canFire(shooter) && this.getAmountInMagazine() > 0 && !this.isReloading() && this.getAmmoType() != NullAmmo.INSTANCE;
	}
	
	@Override
	public void fire(Entity shooter) {
		weapon.fire(shooter);
		shootable.shoot(shooter);
		if(shooter instanceof PlayerEntity && ((PlayerEntity) shooter).isCreative()) {
			return;
		}
		reloadable.decreaseAmountInMag(1);
	}

	@Override
	public int getFireRate() {
		return weapon.getFireRate();
	}

	@Override
	public byte getTimeUntilNextFire() {
		return weapon.getTimeUntilNextFire();
	}

	@Override
	public boolean isAutomatic() {
		return weapon.isAutomatic();
	}

	@Override
	public void onTick(Object... data) {
		weapon.onTick(data);
		shootable.onTick(data);
		reloadable.onTick(data);
	}
	
	@Override
	public void update(Object... data) {
		weapon.update(data);
		shootable.update(data);
		reloadable.update(data);
	}

	@Override
	public void setFireRate(int rate) {
		weapon.setFireRate(rate);
	}

	@Override
	public void setTimeUntilNextFire(byte time) {
		weapon.setTimeUntilNextFire(time);
	}

	@Override
	public void setAutomatic(boolean isAutomatic) {
		weapon.setAutomatic(isAutomatic);
	}

	@Override
	public void setMaxBloom(float maxBloom) {
		shootable.setMaxBloom(maxBloom);
	}

	@Override
	public void setBloomIncreasePerShot(float bloomIncrease) {
		shootable.setBloomIncreasePerShot(bloomIncrease);
	}

	@Override
	public void setBloomDecreasePerTick(float bloomDecrease) {
		shootable.setBloomDecreasePerTick(bloomDecrease);
	}

	@Override
	public void setMuzzleVelocity(float velocity) {
		shootable.setMuzzleVelocity(velocity);
	}

	@Override
	public void setMinVerticalRecoil(float minVertical) {
		shootable.setMinVerticalRecoil(minVertical);
	}

	@Override
	public void setMaxVerticalRecoil(float maxVertical) {
		shootable.setMaxVerticalRecoil(maxVertical);
	}

	@Override
	public void setMinHorizontalRecoil(float minHorizontal) {
		shootable.setMinHorizontalRecoil(minHorizontal);
	}

	@Override
	public void setMaxHorizontalRecoil(float maxHorizontal) {
		shootable.setMaxHorizontalRecoil(maxHorizontal);
	}

	@Override
	public void setMagazineSize(int size) {
		reloadable.setMagazineSize(size);
	}

	@Override
	public void setReloadTime(int ticks) {
		reloadable.setReloadTime(ticks);
	}

	@Override
	public void setReloadProgress(int ticks) {
		reloadable.setReloadProgress(ticks);
	}

	@Override
	public void setIsReloading(boolean reloading) {
		reloadable.setIsReloading(reloading);
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = (CompoundNBT) weapon.serializeNBT();
		nbt.merge((CompoundNBT)shootable.serializeNBT());
		nbt.merge((CompoundNBT)reloadable.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		weapon.deserializeNBT(nbt);
		shootable.deserializeNBT(nbt);
		reloadable.deserializeNBT(nbt);
	}
	
	@Override
	public void render(Pre e, ItemStack stack, float partialTicks, int scaledWidth, int scaledHeight) {
		if(overlay != null) {
			overlay.render(e, stack, partialTicks, scaledWidth, scaledHeight);
		}
		if(reticle != null) {
			reticle.render(e, stack, partialTicks, scaledWidth, scaledHeight);
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
	public void bind() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void unbind() {
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

}
