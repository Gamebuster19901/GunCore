/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.entity;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwner;
import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;
import com.gamebuster19901.guncore.common.util.Resourced;
import com.gamebuster19901.guncore.common.util.GunCoreDamageSource;

import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import static net.minecraft.util.math.RayTraceResult.Type.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class ProjectileEntity extends GunCoreEntity implements ShooterOwner, Resourced{
	
	public static final Predicate<Entity> ANY_ENTITY = (entity) -> {return true;};
	public static final Predicate<Entity> DEFAULT_TARGETS = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
	
	protected CompoundNBT gun;
	protected UUID shooter;
	protected float damage = 0f; //damage to deal if this hits an entity
	
	public ProjectileEntity(EntityType type, World world) {
		super(type, world);
	}
	
	public ProjectileEntity(EntityType type, World world, float damage) {
		super(type, world);
		this.damage = damage;
	}
	
	public void shoot(Shootable gun, @Nullable Entity shooter) {
		this.setGun(gun);
		this.setShooter(shooter);
	}
	
	@Override
	public void tick() {
		if(this.world != null && !this.removed) {
			if(this.ticksExisted == 1) {
				this.world.playSound(posX, posY, posZ, getShootingSound(), SoundCategory.NEUTRAL, 1f, getNextSoundPitch(), false);
			}
			else if(this.ticksExisted > 120 || this.ticksExisted < 1) {
				this.remove();
				return;
			}
			
			RayTraceResult.Type hitType = MISS;
			
			Vec3d pos = this.getPositionVector();
			Vec3d nextPos = pos.add(this.getMotion());
			
			RayTraceResult blockResult = this.world.rayTraceBlocks(new RayTraceContext(pos, nextPos, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if(blockResult != null) {
				blockResult.getHitVec();
				hitType = BLOCK;
			}
			
			EntityRayTraceResult entityResult = getCollidingEntity(pos, nextPos, DEFAULT_TARGETS);
			if(entityResult != null) {
				entityResult.getHitVec();
				hitType = ENTITY;
			}

			this.setPosition(nextPos.x, nextPos.y, nextPos.z);
			
			if(hitType == BLOCK) {
				hitBlock(blockResult);
			}
			else if (hitType == ENTITY) {
				hitEntity(entityResult);
			}
		}
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT ret = new CompoundNBT();
		ret.putString("id", getResourceLocation().toString());
		ret.putInt("ticksExisted", this.ticksExisted);
		this.writeUnlessRemoved(ret);
		return ret;
	}
	
	@Override
	protected void readAdditional(CompoundNBT compound) {
		this.ticksExisted = compound.getInt("ticksExisted");
		if(compound.contains("gun")) {
			this.gun = compound.getCompound("gun");
		}
		if(compound.contains("shooter")) {
			this.shooter = UUID.fromString(compound.getString("shooter"));
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putInt("ticksExisted", this.ticksExisted);
		if(gun != null) {
			compound.put("gun", gun);
		}
		if(shooter != null) {
			compound.putString("shooter", shooter.toString());
		}
	}

	@Nullable
	public abstract SoundEvent getShootingSound();

	
	@Nullable
	public abstract SoundEvent getImpactSound();
	
	public void hitEntity(EntityRayTraceResult rayTrace) {
		if(!world.isRemote) {
			DamageSource damageSource = getDamageSource();
			
			Entity entity = rayTrace.getEntity();
			
			if(entity instanceof LivingEntity) {
				LivingEntity hitEntity = (LivingEntity) entity;
				float gunDamage = 0;
				if(gun != null) {
					gunDamage = gun.getFloat("baseDamage");
				}
				hitEntity.attackEntityFrom(damageSource, this.damage + gunDamage);
			}
		}
		onHit();
	}
	
	public void hitBlock(RayTraceResult rayTrace) {
		this.world.playSound(null, posX, posY, posZ, getImpactSound(), SoundCategory.NEUTRAL, 1f, getNextSoundPitch());
		SoundType blockSound = world.getBlockState(new BlockPos(rayTrace.getHitVec())).getSoundType(world, new BlockPos(rayTrace.getHitVec()), this);
		this.world.playSound(null, posX, posY, posZ, blockSound.getBreakSound(), SoundCategory.NEUTRAL, blockSound.volume, blockSound.pitch);
		onHit();
	}
	
	public void onHit() {
		this.remove();
	}
	
	@Nullable
	protected EntityRayTraceResult getCollidingEntity(Vec3d start, Vec3d end, Predicate<Entity> targets) {
		AxisAlignedBB bounds = new AxisAlignedBB(start, end);
		return ProjectileHelper.func_221273_a(this, start, end, bounds, targets, 0f);
	}
	
	protected float getNextSoundPitch() {
		return this.rand.nextFloat() * (1.5f - 1) + 1;
	}
	
	protected DamageSource getDamageSource() {
		Entity shooter = null;
		for(Entity e: ((ServerWorld)world).getEntities(null, ANY_ENTITY)) {
			if(e.getUniqueID() == this.shooter) {
				shooter = e;
				break;
			}
		}
		return new GunCoreDamageSource("guncore.projectile", this, shooter).setProjectile();
	}
}
