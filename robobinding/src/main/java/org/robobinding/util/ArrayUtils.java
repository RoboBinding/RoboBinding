/*
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
 * Migrated some of methods from
 * {@link org.apache.commons.lang3.reflect.ClassUtils}.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ArrayUtils {

    /**
     * An empty immutable {@code Object} array.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * An empty immutable {@code Class} array.
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    /**
     * <p>
     * ArrayUtils instances should NOT be constructed in standard programming.
     * Instead, the class should be used as
     * <code>ArrayUtils.clone(new int[] {2})</code>.
     * </p>
     * 
     * <p>
     * This constructor is public to permit tools that require a JavaBean
     * instance to operate.
     * </p>
     */
    public ArrayUtils() {
	super();
    }

    // NOTE: Cannot use {@code} to enclose text which includes {}, but
    // <code></code> is OK

    // Is same length
    // -----------------------------------------------------------------------
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
