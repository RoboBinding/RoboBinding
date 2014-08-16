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
 * of type {@code Iterable}. Except as noted, each method has a corresponding
 * {@link Iterator}-based method in the {@link Iterators} class.
 *
 * <p><i>Performance notes:</i> Unless otherwise noted, all of the iterables
 * produced in this class are <i>lazy</i>, which means that their iterators
 * only advance the backing iteration when absolutely necessary.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Iterables">
 * {@code Iterables}</a>.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Iterables {
  /**
   * Adds all elements in {@code iterable} to {@code collection}.
   *
   * @return {@code true} if {@code collection} was modified as a result of this
   *     operation.
   */
  public static <T> boolean addAll(
      Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
    if (elementsToAdd instanceof Collection) {
      Collection<? extends T> c = Collections2.cast(elementsToAdd);
      return addTo.addAll(c);
    }
    return Iterators.addAll(addTo, checkNotNull(elementsToAdd).iterator());
  }
}