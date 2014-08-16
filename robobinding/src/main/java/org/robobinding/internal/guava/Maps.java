/*
 * NOTICE: THE FILE HAS BEEN MODIFIED TO SUIT THE NEEDS OF THE PROJECT.
 *
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robobinding.internal.guava;

import static org.robobinding.internal.guava.CollectPreconditions.checkNonnegative;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * Static utility methods pertaining to {@link Map} instances (including instances of
 * {@link SortedMap}, {@link BiMap}, etc.). Also see this class's counterparts
 * {@link Lists}, {@link Sets} and {@link Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Maps">
 * {@code Maps}</a>.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Isaac Shum
 * @author Louis Wasserman
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Maps {
  private Maps() { }

  /**
   * Creates a <i>mutable</i>, empty {@code HashMap} instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableMap#of()} instead.
   *
   * <p><b>Note:</b> if {@code K} is an {@code enum} type, use {@link
   * #newEnumMap} instead.
   *
   * @return a new, empty {@code HashMap}
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<K, V>();
  }
  
  /**
   * Creates a <i>mutable</i> {@code HashMap} instance with the same mappings as
   * the specified map.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableMap#copyOf(Map)} instead.
   *
   * <p><b>Note:</b> if {@code K} is an {@link Enum} type, use {@link
   * #newEnumMap} instead.
   *
   * @param map the mappings to be placed in the new map
   * @return a new {@code HashMap} initialized with the mappings from {@code
   *         map}
   */
  public static <K, V> HashMap<K, V> newHashMap(
      Map<? extends K, ? extends V> map) {
    return new HashMap<K, V>(map);
  }

  /**
   * Creates a <i>mutable</i>, empty, insertion-ordered {@code LinkedHashMap}
   * instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableMap#of()} instead.
   *
   * @return a new, empty {@code LinkedHashMap}
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
    return new LinkedHashMap<K, V>();
  }
  
  /**
   * Returns a capacity that is sufficient to keep the map from being resized as
   * long as it grows no larger than expectedSize and the load factor is >= its
   * default (0.75).
   */
  static int capacity(int expectedSize) {
    if (expectedSize < 3) {
      checkNonnegative(expectedSize, "expectedSize");
      return expectedSize + 1;
    }
    if (expectedSize < Ints.MAX_POWER_OF_TWO) {
      // This is the calculation used in JDK8 to resize when a putAll
      // happens; it seems to be the most conservative calculation we
      // can make.  0,75 is the default load factor.
      return (int) ((float) expectedSize / 0.75F + 1.0F);
    }
    return Integer.MAX_VALUE; // any large value
  }
}