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

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Static utility methods pertaining to {@link Set} instances. Also see this
 * class's counterparts {@link Lists}, {@link Maps} and {@link com.google.common.collect.Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Sets">
 * {@code Sets}</a>.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @author Chris Povirk
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Sets {
  private Sets() {}

  

  // HashSet

  /**
   * Creates a <i>mutable</i>, empty {@code HashSet} instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * com.google.common.collect.ImmutableSet#of()} instead.
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, use {@link
   * EnumSet#noneOf} instead.
   *
   * @return a new, empty {@code HashSet}
   */
  public static <E> HashSet<E> newHashSet() {
    return new HashSet<E>();
  }

  /**
   * Creates a <i>mutable</i> {@code HashSet} instance containing the given
   * elements in unspecified order.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use an overload of {@link com.google.common.collect.ImmutableSet#of()} (for varargs) or
   * {@link com.google.common.collect.ImmutableSet#copyOf(Object[])} (for an array) instead.
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, use {@link
   * EnumSet#of(Enum, Enum[])} instead.
   *
   * @param elements the elements that the set should contain
   * @return a new {@code HashSet} containing those elements (minus duplicates)
   */
  public static <E> HashSet<E> newHashSet(E... elements) {
    HashSet<E> set = newHashSetWithExpectedSize(elements.length);
    Collections.addAll(set, elements);
    return set;
  }

  /**
   * Creates a {@code HashSet} instance, with a high enough "initial capacity"
   * that it <i>should</i> hold {@code expectedSize} elements without growth.
   * This behavior cannot be broadly guaranteed, but it is observed to be true
   * for OpenJDK 1.6. It also can't be guaranteed that the method isn't
   * inadvertently <i>oversizing</i> the returned set.
   *
   * @param expectedSize the number of elements you expect to add to the
   *        returned set
   * @return a new, empty {@code HashSet} with enough capacity to hold {@code
   *         expectedSize} elements without resizing
   * @throws IllegalArgumentException if {@code expectedSize} is negative
   */
  public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
    return new HashSet<E>(Maps.capacity(expectedSize));
  }

  /**
   * Creates a <i>mutable</i> {@code HashSet} instance containing the given
   * elements in unspecified order.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use {@link com.google.common.collect.ImmutableSet#copyOf(Iterable)} instead.
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, use
   * {@link #newEnumSet(Iterable, Class)} instead.
   *
   * @param elements the elements that the set should contain
   * @return a new {@code HashSet} containing those elements (minus duplicates)
   */
  public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
    return (elements instanceof Collection)
        ? new HashSet<E>(Collections2.cast(elements))
        : newHashSet(elements.iterator());
  }

  /**
   * Creates a <i>mutable</i> {@code HashSet} instance containing the given
   * elements in unspecified order.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use {@link com.google.common.collect.ImmutableSet#copyOf(Iterable)} instead.
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, you should create an
   * {@link EnumSet} instead.
   *
   * @param elements the elements that the set should contain
   * @return a new {@code HashSet} containing those elements (minus duplicates)
   */
  public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
    HashSet<E> set = newHashSet();
    Iterators.addAll(set, elements);
    return set;
  }
  
  public static <E> LinkedHashSet<E> newLinkedHashSet() {
      return new LinkedHashSet<E>();
    }

}