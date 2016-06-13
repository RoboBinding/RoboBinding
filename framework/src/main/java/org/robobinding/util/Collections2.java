package org.robobinding.util;

import java.util.Collection;

/**
 * Migrated from {@link com.google.common.collect.Collections2}
 * @since 1.0
 * @author Cheng Wei
 *
 */
class Collections2 {
  /**
   * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
   */
  static <T> Collection<T> cast(Iterable<T> iterable) {
    return (Collection<T>) iterable;
  }
}
