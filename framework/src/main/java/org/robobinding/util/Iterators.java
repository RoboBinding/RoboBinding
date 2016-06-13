package org.robobinding.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Migrated from {@link com.google.common.collect.Iterators}
 * @since 1.0
 * @author Cheng Wei
 *
 */
class Iterators {
  /**
   * Adds all elements in {@code iterator} to {@code collection}. The iterator
   * will be left exhausted: its {@code hasNext()} method will return
   * {@code false}.
   *
   * @return {@code true} if {@code collection} was modified as a result of this
   *         operation
   */
  public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
    Preconditions.checkNotNull(addTo);
    Preconditions.checkNotNull(iterator);
    boolean wasModified = false;
    while (iterator.hasNext()) {
      wasModified |= addTo.add(iterator.next());
    }
    return wasModified;
  }
}
