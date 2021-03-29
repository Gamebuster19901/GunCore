/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.entity;

import java.util.UUID;
import java.util.function.Predicate;

import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwner;
import static com.gamebuster19901.guncore.capability.common.item.shootable.ShootableDefaultImpl.BASE_DAMAGE;
import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;
import com.gamebuster19901.guncore.common.util.Resourced;
import com.gamebuster19901.guncore.common.util.GunCoreDamageSource;

import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import static net.minecraft.util.math.RayTraceResult.Type.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;

public abstract class ProjectileEntity extends GunCoreEntity implements ShooterOwner, Resourced{
	
	public static final Predicate<Entity> ANY_ENTITY = (entity) -> {return true;};
	public static final Predicate<Entity> DEFAULT_TARGETS = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
	
	public static final String ID = "id";
	public static final String GUN = "gun";
	public static final String SHOOTER = "shooter";
	public static final String TICKS_EXISTED = "ticksExisted";
	
	protected CompoundTag gun;
	protected UUID shooter;
	protected float damage = 0f; //damage to deal if this hits an entity
	
	@SuppressWarnings("rawtypes")
	public ProjectileEntity(EntityType type, World world) {
		super(type, world);
	}
	
	@SuppressWarnings("rawtypes")
	public ProjectileEntity(EntityType type, World world, float damage) {
		super(type, world);
		this.damage = damage;
	}
	
	public void shoot(Shootable gun, @org.jetbrains.annotations.Nullable Entity shooter) {
		this.setGun(gun);
		this.setShooter(shooter);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if(this.world != null && !this.removed && !world.isClient) {
			if(this.age == 1) {
				this.world.playSound(null, getX(), getY(), getZ(), getDischargeSound(), SoundCategory.NEUTRAL, 1f, getNextSoundPitch());
			}
			else if(this.age > 120 || this.age < 1) {
				this.remove();
				return;
			}
			
			RayTraceResult.Type hitType = MISS;
			
			Vec3d pos = this.getPositionVector();
			Vec3d nextPos = pos.add(this.getMotion());
			
			BlockRayTraceResult blockResult = this.world.rayTraceBlocks(new RayTraceContext(pos, nextPos, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, this));
			if(blockResult != null && !world.getBlockState(blockResult.getPos()).isAir()) {
				hitType = BLOCK;
			}
			
			EntityRayTraceResult entityResult = getCollidingEntity(pos, nextPos, DEFAULT_TARGETS);
			if(entityResult != null) {
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
		ret.putString(ID, getResourceLocation().toString());
		ret.putInt(TICKS_EXISTED, this.ticksExisted);
		this.writeUnlessRemoved(ret);
		return ret;
	}
	
	@Override
	protected void readAdditional(CompoundNBT compound) {
		this.ticksExisted = compound.getInt(TICKS_EXISTED);
		if(compound.contains(GUN)) {
			this.gun = compound.getCompound(GUN);
		}
		if(compound.contains(SHOOTER)) {
			this.shooter = UUID.fromString(compound.getString(SHOOTER));
		}
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putInt(TICKS_EXISTED, this.ticksExisted);
		if(gun != null) {
			compound.put(GUN, gun);
		}
		if(shooter != null) {
			compound.putString(SHOOTER, shooter.toString());
		}
	}

	@Nullable
	public abstract SoundEvent getDischargeSound();
	
	@Nullable
	public abstract SoundEvent getImpactSound();
	
	@Nullable
	public abstract SoundEvent getIdleSound();
	
	public void hitEntity(EntityRayTraceResult rayTrace) {
		if(!world.isRemote) {
			DamageSource damageSource = getDamageSource();
			
			Entity entity = rayTrace.getEntity();
			
			if(entity instanceof LivingEntity) {
				LivingEntity hitEntity = (LivingEntity) entity;
				float gunDamage = 0;
				if(gun != null) {
					gunDamage = gun.getFloat(BASE_DAMAGE);
				}
				hitEntity.hurtResistantTime = 0;
				hitEntity.hurtTime = 0;
				hitEntity.attackEntityFrom(damageSource, this.damage + gunDamage);
			}
		}
		onHit();
	}
	
	public void hitBlock(BlockRayTraceResult rayTrace) {
		if(!world.isRemote) {
			if(getImpactSound() != null) {
				this.world.playSound(null, posX, posY, posZ, getImpactSound(), SoundCategory.NEUTRAL, 1f, getNextSoundPitch());
			}
			SoundType blockSound = world.getBlockState(rayTrace.getPos()).getSoundType(world, rayTrace.getPos(), this);
			this.world.playSound(null, posX, posY, posZ, blockSound.getBreakSound(), SoundCategory.NEUTRAL, 3f, blockSound.pitch);
			System.out.println(this.world.getBlockState(rayTrace.getPos()));
		}
		onHit();
	}
	
	public void onHit() {
		this.remove();
	}
	
	@Nullable
	protected EntityRayTraceResult getCollidingEntity(Vec3d start, Vec3d end, Predicate<Entity> targets) {
		AxisAlignedBB bounds = new AxisAlignedBB(start, end);
		return ProjectileHelper.rayTraceEntities(this, start, end, bounds, targets, 0f);
	}
	
	protected float getNextSoundPitch() {
		return this.rand.nextFloat() * (1.5f - 1) + 1;
	}
	
	protected DamageSourcePredicate getDamageSource() {
		Entity shooter = null;
		for(Entity e: ((ServerWorld)world).getEntitiesByType(null, ANY_ENTITY)) {
			if(e.getUuid() == this.shooter) {
				shooter = e;
				break;
			}
		}
		return new GunCoreDamageSource("guncore.projectile", this, shooter).setProjectile();
	}
}
