/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.item.abstracts;

import java.lang.reflect.Field;
import java.util.Random;

import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;
import com.gamebuster19901.guncore.common.entity.ProjectileEntity;
import com.gamebuster19901.guncore.common.util.ForgeReflectionHelper;
import com.gamebuster19901.guncore.common.util.VecMath;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class Projectile {
	private static final Field RAND = ForgeReflectionHelper.findField(Entity.class, "rand");
	
	protected final CompoundTag projectile;
	
	protected double x;
	protected double y;
	protected double z;
	
	protected Entity shooter;
	protected Shootable gun;
	protected ProjectileEntity projectileEntity;
	
	/**
	 * @param projectile the nbt representing the projectile
	 */
	public Projectile(CompoundTag projectile) {
		this.projectile = projectile;
	}
	
	/**
	 * @param projectile the projectile to shoot
	 */
	public Projectile(Entity projectile) {
		this(projectile.toTag(new CompoundTag()));
	}
	
	/**
	 * Makes the entity shoot the projectile from their eye position
	 * 
	 * @param shooter
	 * @param gun
	 * @param gunNBT
	 */
	public void shoot(Entity shooter, Shootable gun) {
		this.shooter = shooter;
		Vec3d pos = shooter.getRotationVector().add(new Vec3d(0,-0.5,0));
		Vec3d lookVec = shooter.getRotationVector().add(0,0.3d,0);
		float distance = 1.5f;
		
		shoot(shooter.getEntityWorld(), VecMath.traverse(pos, lookVec, distance), lookVec, gun);
	}
	
	/**
	 * Makes the entity shoot the projectile from an arbitrary position
	 * @param shooter
	 * @param world
	 * @param pos
	 * @param vector
	 * @param gun
	 * @param gunNBT
	 */
	public void shoot(Entity shooter, World world, Vec3d pos, Vec3d vector, Shootable gun) {
		this.shooter = shooter;
		shoot(shooter.getEntityWorld(), new Vec3d(shooter.getX(), shooter.getY(), shooter.getZ()), shooter.getRotationVector(), gun);
	}
	
	/**
	 * Shoots the projectile from an arbitrary position
	 * 
	 * @param world the world to spawn the projectile in
	 * @param pos the position to spawn the projectile at
	 * @param vector the projectile's vector
	 * @param gun the gun which shot the projectile
	 */
	public void shoot(World world, Vec3d pos, Vec3d vector, Shootable gun) {
		if(gun == null) {
			gun = HandImpl.INSTANCE;
		}
		this.gun = gun;
		if(!world.isClient && projectile != null) {
			projectile.putString("ownerName", shooter.getName().getString());
			projectileEntity = (ProjectileEntity)EntityType.getEntityFromTag(projectile, world).get();
			projectileEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
			if(projectileEntity != null) {
				try {
					projectileEntity.setUuid(MathHelper.randomUuid((Random)RAND.get(projectileEntity)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new AssertionError(e);
				}
				Vec3d lookVec = shooter.getRotationVector();
				projectileEntity.shoot(gun, shooter);
				shoot(lookVec.x, lookVec.y, lookVec.z, gun.getMuzzleVelocity(), gun.getBloom()); //Shoot before spawning entity so it has momentum when spawned!
				world.spawnEntity(projectileEntity);
				return;
			}
			
			throw new IllegalStateException("Illegal projectile NBT: ( " + projectile.toString() + " )");
		}
	}
	
	
	/**
	 * See EntityArrow
	 * @deprecated Use any of the other shoot() methods in this class.
	 */
	@Deprecated
	public void shoot(double vx, double vy, double vz, float velocity, float inaccuracy) {
		if(projectileEntity != null) {
			Random rand;
			try {
				rand = (Random) RAND.get(projectileEntity);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new AssertionError(e);
			}
			
			float f = MathHelper.sqrt(vx * vx + vy * vy + vz * vz);
			vx = vx / (double)f;
			vy = vy / (double)f;
			vz = vz / (double)f;
			vx = vx + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
			vy = vy + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
			vz = vz + rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
			vx = vx * (double)velocity;
			vy = vy * (double)velocity;
			vz = vz * (double)velocity;
			projectileEntity.setVelocity(new Vec3d(vx,vy,vz));
			float f1 = MathHelper.sqrt(vx * vx + vz * vz);
			projectileEntity.yaw = -(float)(MathHelper.atan2(vx, vz) * (180D / Math.PI));
			projectileEntity.pitch = -(float)(MathHelper.atan2(vy, (double)f1) * (180D / Math.PI));
			projectileEntity.prevYaw = -projectileEntity.yaw;
			projectileEntity.prevPitch = -projectileEntity.pitch;
		}
	}
	
	public CompoundTag getProjectileNBT() {
		return projectile;
	}
	
	public Entity getProjectileEntity() {
		return projectileEntity;
	}
}
