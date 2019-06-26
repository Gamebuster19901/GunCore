package com.gamebuster19901.guncore.common.util;

import net.minecraft.util.ResourceLocation;

public interface EasyLocalization{
	public default ResourceLocation getResourceLocation() {
		return new ResourceLocation(getModId() + ':' + toSnakeCase(this.getClass().getSimpleName()));
	}
	
	public default String getEZTranslationKey() {
		return getResourceLocation().toString().replace(':', '.');
	}
	
	public static ResourceLocation getResourceLocation(String modid, Class clazz) {
		return new ResourceLocation(modid + ':' + toSnakeCase(clazz.getSimpleName()));
	}
	
	public static String getEZTranslationKey(String modid, Class clazz) {
		return getResourceLocation(modid, clazz).toString().replace(':', '.');
	}
	
	public static String toSnakeCase(String s) {
		s = s.replaceAll("([A-Z])", "_$1").toLowerCase();
		return s.substring(1);
	}
	
	public abstract String getModId();
}
