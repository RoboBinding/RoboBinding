package org.robobinding.viewattribute;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import android.graphics.Color;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RandomValues {
    private static Random random = new Random();

    private RandomValues() {
    }

    public static DrawableData anyDrawableData() {
	int index = random.nextInt(DrawableData.numDrawableData());
	return DrawableData.get(index);
    }

    public static int anyColor() {
	return random.nextBoolean() ? Color.RED : Color.BLACK;
    }

    public static boolean trueOrFalse() {
	return random.nextBoolean();
    }

    public static int anyInteger() {
	return random.nextInt(100);
    }

    public static int anyIntegerGreaterThanZero() {
	return random.nextInt(100) + 1;
    }

    public static float anyFloat() {
	return random.nextFloat() * anyInteger();
    }

    public static int anyVisibility() {
	int random = new Random().nextInt(3);

	switch (random) {
	case 0:
	    return View.GONE;
	case 1:
	    return View.INVISIBLE;
	default:
	    return View.VISIBLE;
	}
    }

    public static Class<Integer> primitiveOrBoxedIntegerClass() {
	if (random.nextInt(2) == 0)
	    return int.class;

	return Integer.class;
    }

    public static Class<Boolean> primitiveOrBoxedBooleanClass() {
	if (random.nextInt(2) == 0)
	    return boolean.class;

	return Boolean.class;
    }

    public static int anyIndex(int collectionSize) {
	return nextInt(collectionSize);
    }

    public static String anyLayoutResource() {
	return random.nextBoolean() ? "@layout/layout1" : "@layout/layout2";
    }

    public static int nextInt(int n) {
	return random.nextInt(n);
    }

    public static float nextFloat(int n) {
	return random.nextFloat() * nextInt(n);
    }

    public static <T> T either(T... objects) {
	return objects[nextInt(objects.length)];
    }

    public static String anyBlankString() {
	return RandomStringUtils.random(random.nextInt(10), " ");
    }
}
