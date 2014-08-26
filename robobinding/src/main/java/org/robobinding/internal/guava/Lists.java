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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;

/**
 * Static utility methods pertaining to {@link List} instances. Also see this
 * class's counterparts {@link Sets}, {@link Maps} and {@link Queues}.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Lists">
 * {@code Lists}</a>.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Louis Wasserman
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Lists {
  private Lists() { }

  // ArrayList

  /**
   * Creates a <i>mutable</i>, empty {@code ArrayList} instance (for Java 6 and
   * earlier).
   *
   * <p><b>Note:</b> if mutability is not required, use {@link
   * ImmutableList#of()} instead.
   *
   * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and
   * should be treated as deprecated. Instead, use the {@code ArrayList}
   * {@linkplain ArrayList#ArrayList() constructor} directly, taking advantage
   * of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  public static <E> ArrayList<E> newArrayList() {
    return new ArrayList<E>();
  }

  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements.
   *
   * <p><b>Note:</b> essentially the only reason to use this method is when you
   * will need to add or remove elements later. Otherwise, for non-null elements
   * use {@link ImmutableList#of()} (for varargs) or {@link
   * ImmutableList#copyOf(Object[])} (for an array) instead. If any elements
   * might be null, or you need support for {@link List#set(int, Object)}, use
   * {@link Arrays#asList}.
   *
   * <p>Note that even when you do need the ability to add or remove, this method
   * provides only a tiny bit of syntactic sugar for {@code newArrayList(}{@link
   * Arrays#asList asList}{@code (...))}, or for creating an empty list then
   * calling {@link Collections#addAll}. This method is not actually very useful
   * and will likely be deprecated in the future.
   */
  public static <E> ArrayList<E> newArrayList(E... elements) {
    Preconditions.checkNotNull(elements); // for GWT
    // Avoid integer overflow when a large array is passed in
    int capacity = computeArrayListCapacity(elements.length);
    ArrayList<E> list = new ArrayList<E>(capacity);
    Collections.addAll(list, elements);
    return list;
  }

  static int computeArrayListCapacity(int arraySize) {
    checkNonnegative(arraySize, "arraySize");

    // TODO(kevinb): Figure out the right behavior, and document it
    return Ints.saturatedCast(5L + arraySize + (arraySize / 10));
  }

  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements; a very thin shortcut for creating an empty list then calling
   * {@link Iterables#addAll}.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use {@link ImmutableList#copyOf(Iterable)} instead. (Or, change
   * {@code elements} to be a {@link FluentIterable} and call
   * {@code elements.toList()}.)
   *
   * <p><b>Note for Java 7 and later:</b> if {@code elements} is a {@link
   * Collection}, you don't need this method. Use the {@code ArrayList}
   * {@linkplain ArrayList#ArrayList(Collection) constructor} directly, taking
   * advantage of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
    Preconditions.checkNotNull(elements); // for GWT
    // Let ArrayList's sizing logic work, if possible
    return (elements instanceof Collection)
        ? new ArrayList<E>(Collections2.cast(elements))
        : newArrayList(elements.iterator());
  }

  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements; a very thin shortcut for creating an empty list and then calling
   * {@link Iterators#addAll}.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use {@link ImmutableList#copyOf(Iterator)} instead.
   */
  public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
    ArrayList<E> list = newArrayList();
    Iterators.addAll(list, elements);
    return list;
  }

  

  // LinkedList

  /**
   * Creates a <i>mutable</i>, empty {@code LinkedList} instance (for Java 6 and
   * earlier).
   *
   * <p><b>Note:</b> if you won't be adding any elements to the list, use {@link
   * ImmutableList#of()} instead.
   *
   * <p><b>Performance note:</b> {@link ArrayList} and {@link
   * java.util.ArrayDeque} consistently outperform {@code LinkedList} except in
   * certain rare and specific situations. Unless you have spent a lot of time
   * benchmarking your specific needs, use one of those instead.
   *
   * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and
   * should be treated as deprecated. Instead, use the {@code LinkedList}
   * {@linkplain LinkedList#LinkedList() constructor} directly, taking advantage
   * of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  public static <E> LinkedList<E> newLinkedList() {
    return new LinkedList<E>();
  }

  /**
   * Creates a <i>mutable</i> {@code LinkedList} instance containing the given
   * elements; a very thin shortcut for creating an empty list then calling
   * {@link Iterables#addAll}.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use {@link ImmutableList#copyOf(Iterable)} instead. (Or, change
   * {@code elements} to be a {@link FluentIterable} and call
   * {@code elements.toList()}.)
   *
   * <p><b>Performance note:</b> {@link ArrayList} and {@link
   * java.util.ArrayDeque} consistently outperform {@code LinkedList} except in
   * certain rare and specific situations. Unless you have spent a lot of time
   * benchmarking your specific needs, use one of those instead.
   *
   * <p><b>Note for Java 7 and later:</b> if {@code elements} is a {@link
   * Collection}, you don't need this method. Use the {@code LinkedList}
   * {@linkplain LinkedList#LinkedList(Collection) constructor} directly, taking
   * advantage of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  public static <E> LinkedList<E> newLinkedList(
      Iterable<? extends E> elements) {
    LinkedList<E> list = newLinkedList();
    Iterables.addAll(list, elements);
    return list;
  }
}