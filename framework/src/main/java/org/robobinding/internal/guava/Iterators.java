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

import static org.robobinding.internal.guava.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class contains static utility methods that operate on or return objects
 * of type {@link Iterator}. Except as noted, each method has a corresponding
 * {@link Iterable}-based method in the {@link Iterables} class.
 *
 * <p><i>Performance notes:</i> Unless otherwise noted, all of the iterators
 * produced in this class are <i>lazy</i>, which means that they only advance
 * the backing iteration when absolutely necessary.
 *
 * <p>See the Guava User Guide section on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Iterables">
 * {@code Iterators}</a>.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Iterators {
  private Iterators() { }

  /**
   * Adds all elements in {@code iterator} to {@code collection}. The iterator
   * will be left exhausted: its {@code hasNext()} method will return
   * {@code false}.
   *
   * @return {@code true} if {@code collection} was modified as a result of this
   *         operation
   */
  public static <T> boolean addAll(
      Collection<T> addTo, Iterator<? extends T> iterator) {
    checkNotNull(addTo);
    checkNotNull(iterator);
    boolean wasModified = false;
    while (iterator.hasNext()) {
      wasModified |= addTo.add(iterator.next());
    }
    return wasModified;
  }
}