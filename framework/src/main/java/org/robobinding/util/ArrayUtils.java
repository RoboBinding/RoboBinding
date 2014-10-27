/*
 * NOTICE: THE FILE HAS BEEN MODIFIED TO SUIT THE NEEDS OF THE PROJECT.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.robobinding.util;

/**
 * Migrated some of methods from {@link org.apache.commons.lang3.ArrayUtils}.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ArrayUtils {

	/**
	 * An empty immutable {@code Object} array.
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	/**
	 * An empty immutable {@code Class} array.
	 */
	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

	private ArrayUtils() {
	}

	/**
	 * <p>
	 * Checks if an array of Objects is empty or {@code null}.
	 * </p>
	 * 
	 * @param array
	 *            the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final Object[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isNotEmpty(final Object[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks whether two arrays are the same length, treating {@code null}
	 * arrays as length {@code 0}.
	 * 
	 * <p>
	 * Any multi-dimensional aspects of the arrays are ignored.
	 * </p>
	 * 
	 * @param array1
	 *            the first array, may be {@code null}
	 * @param array2
	 *            the second array, may be {@code null}
	 * @return {@code true} if length of arrays matches, treating {@code null}
	 *         as an empty array
	 */
	public static boolean isSameLength(final Object[] array1, final Object[] array2) {
		if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0)
				|| (array1 != null && array2 != null && array1.length != array2.length)) {
			return false;
		}
		return true;
	}
}
