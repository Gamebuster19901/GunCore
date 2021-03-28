/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.combined;

import java.util.Random;

import com.gamebuster19901.guncore.capability.common.heat.Overheat;
import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class WeaponShootableEnergyOverheatImpl extends WeaponEnergy implements Shootable, Overheat{

	protected Shootable shootable;
	protected Overheat overheat;
	
	public WeaponShootableEnergyOverheatImpl(Object... capabilityImplementations) {
		super(capabilityImplementations);
		for(Object o : capabilityImplementations) {
			if(o == null) {
				throw new NullPointerException();
			}
			if(o instanceof Shootable) {
				if(shootable == null) {
					shootable = (Shootable) o;
				}
				else {
					throw new IllegalArgumentException("Shootable instance specified twice");
				}
			}
			if(o instanceof Overheat) {
				if(overheat == null) {
					overheat = (Overheat) o;
				}
				else {
					throw new IllegalArgumentException("Overheat instance specified twice");
				}
			}
		}
		
		if(weapon == null || shootable == null || energy == null || overheat == null) {
			String err = "The following types were not specified :";
			if(shootable == null) {
				err = err + "shootable,";
			}
			if(overheat == null) {
				err = err + "overheat,";
			}
			
			err = err.substring(err.lastIndexOf(','));
			
			throw new IllegalArgumentException(err);	
		}	
	}

	@Override
	public void onTick(Object... data) {
		weapon.onTick(data);
		shootable.onTick(data);
		energy.onTick(data);
		overheat.onTick(data);
	}
	
	@Override
	public void update(Object... data) {
		weapon.update(data);
		shootable.update(data);
		energy.update(data);
		overheat.update(data);
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = super.serializeNBT();
		nbt.merge(shootable.serializeNBT());
		nbt.merge(overheat.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		super.deserializeNBT(nbt);
		shootable.deserializeNBT(nbt);
		overheat.deserializeNBT(nbt);
	}

	@Override
	public double getTemp() {
		return overheat.getTemp();
	}

	@Override
	public void setTemp(double temp) {
		overheat.setTemp(temp);
	}

	@Override
	public double getMinTemp() {
		return overheat.getMinTemp();
	}

	@Override
	public void setMinTemp(double minTemp) {
		overheat.setMinTemp(minTemp);
	}

	@Override
	public double getMaxTemp() {
		return overheat.getMaxTemp();
	}

	@Override
	public void setMaxTemp(double maxTemp) {
		overheat.setMaxTemp(maxTemp);
	}

	@Override
	public void overheat() {
		overheat.overheat();
	}

	@Override
	public void stopOverheat() {
		overheat.stopOverheat();
	}

	@Override
	public boolean isOverheating() {
		return overheat.isOverheating();
	}

	@Override
	public double getTempDecrease() {
		return overheat.getTempDecrease();
	}

	@Override
	public void setTempDecrease(double tempDecrease) {
		overheat.setTempDecrease(tempDecrease);
	}

	@Override
	public double getTempIncrease() {
		return overheat.getTempIncrease();
	}

	@Override
	public void setTempIncrease(double tempIncrease) {
		overheat.setTempIncrease(tempIncrease);
	}

	@Override
	public void setBloom(float bloom) {
		shootable.setBloom(bloom);
	}

	@Override
	public void addBloom(float bloom) {
		shootable.addBloom(bloom);
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
	public void setMaxBloom(float maxBloom) {
		shootable.setMaxBloom(maxBloom);
	}

	@Override
	public float getBloomIncreasePerShot() {
		return shootable.getBloomIncreasePerShot();
	}

	@Override
	public void setBloomIncreasePerShot(float bloomIncrease) {
		shootable.setBloomIncreasePerShot(bloomIncrease);
	}

	@Override
	public float getBloomDecreasePerTick() {
		return shootable.getBloomDecreasePerTick();
	}

	@Override
	public void setBloomDecreasePerTick(float bloomDecrease) {
		shootable.setBloomDecreasePerTick(bloomDecrease);
	}

	@Override
	public float getMuzzleVelocity() {
		return shootable.getMuzzleVelocity();
	}

	@Override
	public void setMuzzleVelocity(float velocity) {
		shootable.setMuzzleVelocity(velocity);
	}

	@Override
	public float getMinVerticalRecoil() {
		return shootable.getMinVerticalRecoil();
	}

	@Override
	public void setMinVerticalRecoil(float minVertical) {
		shootable.setMinVerticalRecoil(minVertical);
	}

	@Override
	public float getMaxVerticalRecoil() {
		return shootable.getMaxVerticalRecoil();
	}

	@Override
	public void setMaxVerticalRecoil(float maxVertical) {
		shootable.setMaxVerticalRecoil(maxVertical);
	}

	@Override
	public float getMinHorizontalRecoil() {
		return shootable.getMinHorizontalRecoil();
	}

	@Override
	public void setMinHorizontalRecoil(float minHorizontal) {
		shootable.setMinHorizontalRecoil(minHorizontal);
	}

	@Override
	public float getMaxHorizontalRecoil() {
		return shootable.getMaxHorizontalRecoil();
	}

	@Override
	public void setMaxHorizontalRecoil(float maxHorizontal) {
		shootable.setMaxHorizontalRecoil(maxHorizontal);
	}

	@Override
	public void setProjectile(CompoundNBT projectile) {
		shootable.setProjectile(projectile);
	}

	@Override
	public CompoundNBT getProjectile() {
		return shootable.getProjectile();
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
		return super.canFire(shooter) && !this.isOverheating();
	}

	@Override
	public void fire(Entity shooter) {
		weapon.fire(shooter);
		shootable.shoot(shooter);
		if(shooter instanceof PlayerEntity && ((PlayerEntity)shooter).isCreative()) {
			return;
		}
		energy.extractEnergy(getMaxExtract(), false);
		overheat.addTemp(getTempIncrease());
	}

	@Override
	public float getBaseDamage() {
		return shootable.getBaseDamage();
	}

	@Override
	public void setBaseDamage(float damage) {
		shootable.setBaseDamage(damage);
	}
}
