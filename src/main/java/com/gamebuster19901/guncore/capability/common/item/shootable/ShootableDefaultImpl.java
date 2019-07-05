/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.capability.common.item.shootable;

import java.util.Random;

import com.gamebuster19901.guncore.common.item.NullAmmo;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ShootableDefaultImpl implements Shootable{
	@CapabilityInject(Shootable.class)
	public static Capability<Shootable> CAPABILITY = null;
	
	protected Random rand = new Random();
	
	protected float maxBloom;
	protected float bloomI;
	protected float bloomD;
	protected float muzzleVelocity;
	protected float minRecoilX;
	protected float maxRecoilX;
	protected float minRecoilY;
	protected float maxRecoilY;
	protected NBTTagCompound projectile = NullAmmo.INSTANCE.getProjectile();
	
	protected float bloom = maxBloom;

	
	public ShootableDefaultImpl(float maxBloom, float bloomI, float bloomD, float muzzleVelocity, float minRecoilX, float maxRecoilX, float minRecoilY, float maxRecoilY, NBTTagCompound projectile) {
		this.maxBloom = maxBloom;
		this.bloomI = bloomI;
		this.bloomD = bloomD;
		this.muzzleVelocity = muzzleVelocity;
		this.minRecoilX = minRecoilX;
		this.maxRecoilX = maxRecoilX;
		this.minRecoilY = minRecoilY;
		this.maxRecoilY = maxRecoilY;
		if(projectile != null) {
			this.projectile = projectile;
		}
	}

	@Override
	public void addBloom(float bloom) {
		setBloom(this.getBloom() + bloom);
	}
	
	@Override
	public void setBloom(float bloom) {
		this.bloom = MathHelper.clamp(bloom, 0, maxBloom);
	}
	
	@Override
	public float getBloom() {
		return bloom;
	}

	@Override
	public float getMaxBloom() {
		return maxBloom;
	}
	
	@Override
	public void setMaxBloom(float maxBloom) {
		this.maxBloom = maxBloom;
	}


	@Override
	public float getBloomIncreasePerShot() {
		return bloomI;
	}
	
	@Override
	public void setBloomIncreasePerShot(float bloomIncrease) {
		bloomI = bloomIncrease;
	}


	@Override
	public float getBloomDecreasePerTick() {
		return bloomD;
	}
	
	@Override
	public void setBloomDecreasePerTick(float bloomDecrease) {
		bloomD = bloomDecrease;
	}


	@Override
	public float getMuzzleVelocity() {
		return muzzleVelocity;
	}
	
	@Override
	public void setMuzzleVelocity(float velocity) {
		muzzleVelocity = velocity;
	}


	@Override
	public float getMinVerticalRecoil() {
		return minRecoilY;
	}
	
	@Override
	public void setMinVerticalRecoil(float minVertical) {
		minRecoilY = minVertical;
	}


	@Override
	public float getMaxVerticalRecoil() {
		return maxRecoilY;
	}
	
	@Override
	public void setMaxVerticalRecoil(float maxVertical) {
		maxRecoilY = maxVertical;
	}


	@Override
	public float getMinHorizontalRecoil() {
		return minRecoilX;
	}
	
	@Override
	public void setMinHorizontalRecoil(float minHorizontal) {
		minRecoilX = minHorizontal;
	}


	@Override
	public float getMaxHorizontalRecoil() {
		return maxRecoilX;
	}
	
	@Override
	public void setMaxHorizontalRecoil(float maxHorizontal) {
		maxRecoilX = maxHorizontal;
	}

	@Override
	public NBTTagCompound getProjectile() {
		return projectile;
	}
	
	@Override
	public void setProjectile(NBTTagCompound projectile) {
		this.projectile = projectile;
	}
	
	@Override
	@Deprecated
	public void onTick(WorldTickEvent e) {
		update(e);
	}

	@Override
	public void update(WorldTickEvent e) {
		if(bloom > 0) {
			bloom = MathHelper.clamp(bloom - bloomD, 0, getMaxBloom());
		}
	}
	
	@Override
	public Random getRandom() {
		return rand;
	}
	
	@Override
	public void setRandom(Random random) {
		this.rand = random;
	}
	
	@Override
	public void shoot(Entity shooter) {
		Shootable.super.shoot(shooter);
		bloom = MathHelper.clamp(bloom + bloomI, 0, getMaxBloom());
	}
	
	@Override
	public void shoot(World world, Vec3d pos, Vec3d vector) {
		Shootable.super.shoot(world, pos, vector);
		bloom = MathHelper.clamp(bloom + bloomI, 0, getMaxBloom());
	}


	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.putFloat("maxBloom", maxBloom);
		nbt.putFloat("bloomI", bloomI);
		nbt.putFloat("bloomD", bloomD);
		nbt.putFloat("muzzleVelocity", muzzleVelocity);
		nbt.putFloat("minRecoilX", minRecoilX);
		nbt.putFloat("minRecoilY", minRecoilY);
		nbt.putFloat("maxRecoilX", maxRecoilX);
		nbt.putFloat("maxRecoilY", maxRecoilY);
		nbt.put("projectile", projectile);
		nbt.putFloat("bloom", bloom);
		return nbt;
	}


	@Override
	public void deserializeNBT(NBTTagCompound base) {
		NBTTagCompound nbt = (NBTTagCompound) base;
		maxBloom = nbt.getFloat("maxBloom");
		bloomI = nbt.getFloat("bloomI");
		bloomD = nbt.getFloat("bloomD");
		muzzleVelocity = nbt.getFloat("muzzleVelocity");
		minRecoilX = nbt.getFloat("minRecoilX");
		minRecoilY = nbt.getFloat("minRecoilY");
		maxRecoilX = nbt.getFloat("maxRecoilX");
		maxRecoilY = nbt.getFloat("maxRecoilY");
		projectile = nbt.getCompound("projectile");
		bloom = nbt.getFloat("bloom");
	}
	
}