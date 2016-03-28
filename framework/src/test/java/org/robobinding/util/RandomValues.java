package org.robobinding.util;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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

	private static int[] bitmapDrawableResourceIds = { R.drawable.bottom_bar, R.drawable.title_bar };

	public static BitmapDrawableData anyBitmapDrawableData(Context context) {
		int select = random.nextInt(bitmapDrawableResourceIds.length);
		int resourceId = bitmapDrawableResourceIds[select];
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
		return new BitmapDrawableData(resourceId, bitmap, new BitmapDrawable(context.getResources(), bitmap));
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
	
	public static int integerBetween(int minValue, int maxValue) {
		return random.nextInt((maxValue - minValue) + 1) + minValue;
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

	public static int nextIntegerGreaterThanZero(int n) {
		return random.nextInt(n - 1) + 1;
	}

	public static float nextFloat(int n) {
		return random.nextFloat() * nextInt(n);
	}

	@SafeVarargs
	public static <T> T either(T... objects) {
		return objects[nextInt(objects.length)];
	}

	public static String anyBlankString() {
		return RandomStringUtils.random(random.nextInt(10), " ");
	}
}
