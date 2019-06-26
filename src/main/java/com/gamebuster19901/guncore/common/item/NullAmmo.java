package com.gamebuster19901.guncore.common.item;

import com.gamebuster19901.guncore.common.item.abstracts.Ammo;
import com.gamebuster19901.guncore.common.item.abstracts.Projectile;
import static com.gamebuster19901.guncore.Main.MODID;

import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.world.dimension.DimensionType;

public class NullAmmo extends Ammo {

	private static final Projectile snowball;
	static {
		EntitySnowball entity = new EntitySnowball(null);
		entity.dimension = DimensionType.OVERWORLD;
		snowball = new Projectile(entity.serializeNBT());
	}
	 
	
	public static final NullAmmo INSTANCE = new NullAmmo();
	
	public NullAmmo() {
		super(snowball, 1);
	}

	@Override
	public String getModId() {
		return MODID;
	}

}
