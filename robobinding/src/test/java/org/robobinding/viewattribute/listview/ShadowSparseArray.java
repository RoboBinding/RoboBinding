/*
 * Copyright (C) 2006 The Android Open Source Project
 * Copyright (C) 2011 Eric Bowman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robobinding.viewattribute.listview;

import android.util.SparseArray;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

/**
 * Shadow implementation of SparseArray. Basically copied & pasted the real
 * SparseArray implementation, without depending on the ArrayUtils class that is
 * not in the SDK.
 * 
 * @author Eric Bowman (ebowman@boboco.ie)
 * @since 2011-02-25 11:01
 */
// @SuppressWarnings({"JavaDoc"})
@Implements(SparseArray.class)
public class ShadowSparseArray<E> {

    private static final Object DELETED = new Object();
    private boolean mGarbage = false;

    public void __constructor__() {
	__constructor__(10);
    }

    /**
     * Creates a new SparseArray containing no mappings that will not require
     * any additional memory allocation to store the specified number of
     * mappings.
     */
    public void __constructor__(int initialCapacity) {
	initialCapacity = idealIntArraySize(initialCapacity);
	mKeys = new int[initialCapacity];
	mValues = new Object[initialCapacity];
	mSize = 0;
    }

    /**
     * Gets the Object mapped from the specified key, or <code>null</code> if no
     * such mapping has been made.
     */
    @Implementation
    public E get(int key) {
	return get(key, null);
    }

    /**
     * Gets the Object mapped from the specified key, or the specified Object if
     * no such mapping has been made.
     */
    @SuppressWarnings("unchecked")
    @Implementation
    public E get(int key, E valueIfKeyNotFound) {
	int i = binarySearch(mKeys, 0, mSize, key);

	if (i < 0 || mValues[i] == DELETED) {
	    return valueIfKeyNotFound;
	} else {
	    // noinspection unchecked
	    return (E) mValues[i];
	}
    }

    /**
     * Removes the mapping from the specified key, if there was any.
     */
    @Implementation
    public void delete(int key) {
	int i = binarySearch(mKeys, 0, mSize, key);

	if (i >= 0) {
	    if (mValues[i] != DELETED) {
		mValues[i] = DELETED;
		mGarbage = true;
	    }
	}
    }

    /**
     * Alias for {@link #delete(int)}.
     */
    @Implementation
    public void remove(int key) {
	delete(key);
    }

    private void gc() {
	// Log.e("SparseArray", "gc start with " + mSize);

	int n = mSize;
	int o = 0;
	int[] keys = mKeys;
	Object[] values = mValues;

	for (int i = 0; i < n; i++) {
	    Object val = values[i];

	    if (val != DELETED) {
		if (i != o) {
		    keys[o] = keys[i];
		    values[o] = val;
		}

		o++;
	    }
	}

	mGarbage = false;
	mSize = o;

	// Log.e("SparseArray", "gc end with " + mSize);
    }

    /**
     * Adds a mapping from the specified key to the specified value, replacing
     * the previous mapping from the specified key if there was one.
     */
    @Implementation
    public void put(int key, E value) {
	int i = binarySearch(mKeys, 0, mSize, key);

	if (i >= 0) {
	    mValues[i] = value;
	} else {
	    i = ~i;

	    if (i < mSize && mValues[i] == DELETED) {
		mKeys[i] = key;
		mValues[i] = value;
		return;
	    }

	    if (mGarbage && mSize >= mKeys.length) {
		gc();

		// Search again because indices may have changed.
		i = ~binarySearch(mKeys, 0, mSize, key);
	    }

	    if (mSize >= mKeys.length) {
		int n = idealIntArraySize(mSize + 1);

		int[] nkeys = new int[n];
		Object[] nvalues = new Object[n];

		// Log.e("SparseArray", "grow " + mKeys.length + " to " + n);
		System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
		System.arraycopy(mValues, 0, nvalues, 0, mValues.length);

		mKeys = nkeys;
		mValues = nvalues;
	    }

	    if (mSize - i != 0) {
		// Log.e("SparseArray", "move " + (mSize - i));
		System.arraycopy(mKeys, i, mKeys, i + 1, mSize - i);
		System.arraycopy(mValues, i, mValues, i + 1, mSize - i);
	    }

	    mKeys[i] = key;
	    mValues[i] = value;
	    mSize++;
	}
    }

    /**
     * Returns the number of key-value mappings that this SparseArray currently
     * stores.
     */
    @Implementation
    public int size() {
	if (mGarbage) {
	    gc();
	}

	return mSize;
    }

    /**
     * Given an index in the range <code>0...size()-1</code>, returns the key
     * from the <code>index</code>th key-value mapping that this SparseArray
     * stores.
     */
    @Implementation
    public int keyAt(int index) {
	if (mGarbage) {
	    gc();
	}

	return mKeys[index];
    }

    /**
     * Given an index in the range <code>0...size()-1</code>, returns the value
     * from the <code>index</code>th key-value mapping that this SparseArray
     * stores.
     */
    @SuppressWarnings("unchecked")
    @Implementation
    public E valueAt(int index) {
	if (mGarbage) {
	    gc();
	}

	// noinspection unchecked
	return (E) mValues[index];
    }

    /**
     * Given an index in the range <code>0...size()-1</code>, sets a new value
     * for the <code>index</code>th key-value mapping that this SparseArray
     * stores.
     */
    @Implementation
    public void setValueAt(int index, E value) {
	if (mGarbage) {
	    gc();
	}

	mValues[index] = value;
    }

    /**
     * Returns the index for which {@link #keyAt} would return the specified
     * key, or a negative number if the specified key is not mapped.
     */
    @Implementation
    public int indexOfKey(int key) {
	if (mGarbage) {
	    gc();
	}

	return binarySearch(mKeys, 0, mSize, key);
    }

    /**
     * Returns an index for which {@link #valueAt} would return the specified
     * key, or a negative number if no keys map to the specified value. Beware
     * that this is a linear search, unlike lookups by key, and that multiple
     * keys can map to the same value and this will find only one of them.
     */
    @Implementation
    public int indexOfValue(E value) {
	if (mGarbage) {
	    gc();
	}

	for (int i = 0; i < mSize; i++)
	    if (mValues[i] == value)
		return i;

	return -1;
    }

    /**
     * Removes all key-value mappings from this SparseArray.
     */
    @Implementation
    public void clear() {
	int n = mSize;
	Object[] values = mValues;

	for (int i = 0; i < n; i++) {
	    values[i] = null;
	}

	mSize = 0;
	mGarbage = false;
    }

    /**
     * Puts a key/value pair into the array, optimizing for the case where the
     * key is greater than all existing keys in the array.
     */
    @Implementation
    public void append(int key, E value) {
	if (mSize != 0 && key <= mKeys[mSize - 1]) {
	    put(key, value);
	    return;
	}

	if (mGarbage && mSize >= mKeys.length) {
	    gc();
	}

	int pos = mSize;
	if (pos >= mKeys.length) {
	    int n = idealIntArraySize(pos + 1);

	    int[] nkeys = new int[n];
	    Object[] nvalues = new Object[n];

	    // Log.e("SparseArray", "grow " + mKeys.length + " to " + n);
	    System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
	    System.arraycopy(mValues, 0, nvalues, 0, mValues.length);

	    mKeys = nkeys;
	    mValues = nvalues;
	}

	mKeys[pos] = key;
	mValues[pos] = value;
	mSize = pos + 1;
    }

    private static int binarySearch(int[] a, int start, int len, int key) {
	int high = start + len, low = start - 1, guess;

	while (high - low > 1) {
	    guess = (high + low) / 2;

	    if (a[guess] < key)
		low = guess;
	    else
		high = guess;
	}

	if (high == start + len)
	    return ~(start + len);
	else if (a[high] == key)
	    return high;
	else
	    return ~high;
    }

    @SuppressWarnings("unused")
    private void checkIntegrity() {
	for (int i = 1; i < mSize; i++) {
	    if (mKeys[i] <= mKeys[i - 1]) {
		for (int j = 0; j < mSize; j++) {
		    System.err.println("FAIL: " + j + ": " + mKeys[j] + " -> " + mValues[j]);
		}

		throw new RuntimeException();
	    }
	}
    }

    public static int idealIntArraySize(int need) {
	return idealByteArraySize(need * 4) / 4;
    }

    public static int idealByteArraySize(int need) {
	for (int i = 4; i < 32; i++)
	    if (need <= (1 << i) - 12)
		return (1 << i) - 12;

	return need;
    }

    private int[] mKeys;
    private Object[] mValues;
    private int mSize;
}
