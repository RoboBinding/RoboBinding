/*
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

package com.google.common.collect;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Static utility methods pertaining to {@link Set} instances. Also see this
 * class's counterparts {@link Lists} and {@link Maps}.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @author Chris Povirk
 * @since 2 (imported from Google Collections Library)
 */
//@GwtCompatible(emulated = true)
public final class Sets {
  private Sets() {}

  

  // HashSet

  /**
   * Creates a <i>mutable</i>, empty {@code HashSet} instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSet#of()} instead.
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
   * non-null, use an overload of {@link ImmutableSet#of()} (for varargs) or
   * {@link ImmutableSet#copyOf(Object[])} (for an array) instead.
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, use {@link
   * EnumSet#of(Enum, Enum[])} instead.
   *
   * @param elements the elements that the set should contain
   * @return a new {@code HashSet} containing those elements (minus duplicates)
   */
  public static <E> HashSet<E> newHashSet(E... elements) {
    int capacity = Maps.capacity(elements.length);
    HashSet<E> set = new HashSet<E>(capacity);
    Collections.addAll(set, elements);
    return set;
  }

  /**
   * Creates an empty {@code HashSet} instance with enough capacity to hold the
   * specified number of elements without rehashing.
   *
   * @param expectedSize the expected size
   * @return a new, empty {@code HashSet} with enough capacity to hold {@code
   *     expectedSize} elements without rehashing
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
   * non-null, use {@link ImmutableSet#copyOf(Iterable)} instead.
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
   * non-null, use {@link ImmutableSet#copyOf(Iterable)} instead.
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, you should create an
   * {@link EnumSet} instead.
   *
   * @param elements the elements that the set should contain
   * @return a new {@code HashSet} containing those elements (minus duplicates)
   */
  public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
    HashSet<E> set = newHashSet();
    while (elements.hasNext()) {
      set.add(elements.next());
    }
    return set;
  }

  // LinkedHashSet

  /**
   * Creates a <i>mutable</i>, empty {@code LinkedHashSet} instance.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSet#of()} instead.
   *
   * @return a new, empty {@code LinkedHashSet}
   */
  public static <E> LinkedHashSet<E> newLinkedHashSet() {
    return new LinkedHashSet<E>();
  }

  

  // TreeSet

  /**
   * Creates a <i>mutable</i>, empty {@code TreeSet} instance sorted by the
   * natural sort ordering of its elements.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSortedSet#of()} instead.
   *
   * @return a new, empty {@code TreeSet}
   */
  @SuppressWarnings({ "rawtypes" })  // allow ungenerified Comparable types
  public static <E extends Comparable> TreeSet<E> newTreeSet() {
    return new TreeSet<E>();
  }

  /**
   * Creates a <i>mutable</i> {@code TreeSet} instance containing the given
   * elements sorted by their natural ordering.
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableSortedSet#copyOf(Iterable)} instead.
   *
   * <p><b>Note:</b> If {@code elements} is a {@code SortedSet} with an explicit
   * comparator, this method has different behavior than
   * {@link TreeSet#TreeSet(SortedSet)}, which returns a {@code TreeSet} with
   * that comparator.
   *
   * @param elements the elements that the set should contain
   * @return a new {@code TreeSet} containing those elements (minus duplicates)
   */
  @SuppressWarnings({ "rawtypes" })  // allow ungenerified Comparable types
  public static <E extends Comparable> TreeSet<E> newTreeSet(
      Iterable<? extends E> elements) {
    TreeSet<E> set = newTreeSet();
    for (E element : elements) {
      set.add(element);
    }
    return set;
  }

  /**
   * Creates a <i>mutable</i>, empty {@code TreeSet} instance with the given
   * comparator.
   *
   * <p><b>Note:</b> if mutability is not required, use {@code
   * ImmutableSortedSet.orderedBy(comparator).build()} instead.
   *
   * @param comparator the comparator to use to sort the set
   * @return a new, empty {@code TreeSet}
   * @throws NullPointerException if {@code comparator} is null
   */
  public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
    return new TreeSet<E>(checkNotNull(comparator));
  }

  /*
   * Regarding newSetForMap() and SetFromMap:
   *
   * Written by Doug Lea with assistance from members of JCP JSR-166
   * Expert Group and released to the public domain, as explained at
   * http://creativecommons.org/licenses/publicdomain
   */
}