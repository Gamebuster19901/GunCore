package com.gamebuster19901.guncore.common.entity;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.gamebuster19901.guncore.capability.common.entity.shooterOwner.ShooterOwner;
import com.gamebuster19901.guncore.capability.common.item.shootable.Shootable;
import com.gamebuster19901.guncore.common.util.EasyLocalization;
import com.gamebuster19901.guncore.common.util.GunCoreDamageSource;

import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import static net.minecraft.util.math.RayTraceResult.Type.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ProjectileEntity extends GunCoreEntity implements ShooterOwner, EasyLocalization{
	
	public static final Predicate<Entity> DEFAULT_TARGETS = EntitySelectors.NOT_SPECTATING.and(Entity::canBeCollidedWith);
	
	protected NBTTagCompound gun;
	protected UUID shooter;
	protected float damage = 1f; //damage to deal if this hits an entity
	
	public ProjectileEntity(EntityType type, World worldIn) {
		super(type, worldIn);
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
				//this.world.spawnAlwaysVisibleParticle(p_190523_1_, p_190523_2_, p_190523_4_, p_190523_6_, p_190523_8_, p_190523_10_, p_190523_12_, p_190523_14_);
			}
			else if(this.ticksExisted > 120 || this.ticksExisted < 1) {
				this.remove();
				return;
			}
			
			RayTraceResult.Type hitType = MISS;
			
			Vec3d pos = this.getPositionVector();
			Vec3d nextPos = pos.add(this.motionX, this.motionY, this.motionZ);
			
			RayTraceResult blockResult = this.world.rayTraceBlocks(pos, nextPos, RayTraceFluidMode.NEVER, true, false);
			if(blockResult != null) {
				nextPos = new Vec3d(blockResult.hitVec.x, blockResult.hitVec.y,blockResult.hitVec.z);
				hitType = BLOCK;
			}
			
			RayTraceResult entityResult = getCollidingEntity(pos, nextPos, DEFAULT_TARGETS);
			if(entityResult != null) {
				nextPos = new Vec3d(entityResult.hitVec.x, entityResult.hitVec.y, entityResult.hitVec.z);
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
	public NBTTagCompound serializeNBT() {
		NBTTagCompound ret = new NBTTagCompound();
		ret.putString("id", getResourceLocation().toString());
		ret.putInt("ticksExisted", this.ticksExisted);
		this.writeUnlessRemoved(ret);
		return ret;
	}
	
	@Override
	protected void readAdditional(NBTTagCompound compound) {
		this.ticksExisted = compound.getInt("ticksExisted");
		if(compound.contains("gun")) {
			this.gun = compound.getCompound("gun");
		}
		if(compound.contains("shooter")) {
			this.shooter = UUID.fromString(compound.getString("shooter"));
		}
	}

	@Override
	protected void writeAdditional(NBTTagCompound compound) {
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
	
	public void hitEntity(RayTraceResult rayTrace) {
		if(!world.isRemote) {
			DamageSource damageSource = getDamageSource();
			
			Entity entity = rayTrace.entity;
			
			if(entity instanceof EntityLivingBase) {
				EntityLivingBase hitEntity = (EntityLivingBase) entity;
				hitEntity.attackEntityFrom(damageSource, this.damage);
			}
		}
		onHit();
	}
	
	public void hitBlock(RayTraceResult rayTrace) {
		this.world.playSound(null, posX, posY, posZ, getImpactSound(), SoundCategory.NEUTRAL, 1f, getNextSoundPitch());
		SoundType blockSound = world.getBlockState(rayTrace.getBlockPos()).getSoundType(world, rayTrace.getBlockPos(), this);
		this.world.playSound(null, posX, posY, posZ, blockSound.getBreakSound(), SoundCategory.NEUTRAL, blockSound.volume, blockSound.pitch);
		onHit();
	}
	
	public void onHit() {
		this.remove();
	}
	
	@Nullable
	protected RayTraceResult getCollidingEntity(Vec3d start, Vec3d end, Predicate<Entity> targets) {
		RayTraceResult result = null;
		Entity entity = null;
		List<Entity> entities = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1), targets);
		double distance = Integer.MAX_VALUE;
		
		for(int i = 0; i < entities.size(); i++) {
			Entity loopEntity = entities.get(i);
			assert loopEntity != this;
			AxisAlignedBB bounding = loopEntity.getBoundingBox().grow(0.3f);
			RayTraceResult rayTraceResult = bounding.calculateIntercept(start, end);
			if(rayTraceResult != null) {
				double newDistance = start.squareDistanceTo(rayTraceResult.hitVec);
				if(newDistance < distance) {
					distance = newDistance;
					entity = loopEntity;
					result = new RayTraceResult(entity, rayTraceResult.hitVec);
				}
			}
		}
		return result;
	}
	
	protected float getNextSoundPitch() {
		return this.rand.nextFloat() * (1.5f - 1) + 1;
	}
	
	protected DamageSource getDamageSource() {
		Entity shooter = null;
		for(Entity e: world.loadedEntityList) {
			if(e.getUniqueID() == this.shooter) {
				shooter = e;
				break;
			}
		}
		return new GunCoreDamageSource("guncore.projectile", this, shooter).setProjectile();
	}
}
