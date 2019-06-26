package com.gamebuster19901.guncore.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public final class ForgeReflectionHelper {

	static final Method FIND_FIELDS;
	
	static {
		try {
			FIND_FIELDS = ObfuscationReflectionHelper.class.getDeclaredMethod("findField", Class.class, String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new AssertionError(e);
		}
		FIND_FIELDS.setAccessible(true);
	}
	
	public static final Field findField(@Nonnull Class<?> clazz, @Nonnull String fieldName) {
		try {
			return (Field) FIND_FIELDS.invoke(null, clazz, fieldName);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new AssertionError(e);
		}
	}
	
}
