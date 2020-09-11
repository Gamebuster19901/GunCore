/*
 * GunCore Copyright 2019 - 2020 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.common.util;

import net.minecraft.util.ResourceLocation;

public interface Resourced{
	public default ResourceLocation getResourceLocation() {
		return getResourceLocation(getModId(), this.getClass());
	}
	
	public default ResourceLocation getResourceLocation(String additional) {
		return getResourceLocation(getModId(), this.getClass(), additional);
	}
	
	public default String getEZTranslationKey() {
		return getEZTranslationKey(getModId(), this.getClass());
	}
	
	public default String getEZTranslationKey(String additional) {
		return getEZTranslationKey(getModId(), this.getClass(), additional);
	}
	
	@SuppressWarnings("rawtypes")
	public static ResourceLocation getResourceLocation(String modid, Class clazz) {
		return new ResourceLocation(modid + ':' + toSnakeCase(clazz.getSimpleName()));
	}
	
	@SuppressWarnings("rawtypes")
	public static ResourceLocation getResourceLocation(String modid, Class clazz, String additional) {
		if(additional.charAt(0) != '_') {
			additional = "_" + additional;
		}
		return new ResourceLocation(modid + ":" + toSnakeCase(clazz.getSimpleName() + additional));
	}
	
	@SuppressWarnings("rawtypes")
	public static String getEZTranslationKey(String modid, Class clazz) {
		return getResourceLocation(modid, clazz).toString().replace(':', '.');
	}
	
	@SuppressWarnings("rawtypes")
	public static String getEZTranslationKey(String modid, Class clazz, String additional) {
		if(additional.charAt(0) != '_') {
			additional = "_" + additional;
		}
		return getResourceLocation(modid, clazz, additional).toString().replace(':', '.');
	}
	
	public static String toSnakeCase(String s) {
		s = s.replaceAll("([A-Z])", "_$1").toLowerCase();
		return s.substring(1);
	}
	
	public abstract String getModId();
}
