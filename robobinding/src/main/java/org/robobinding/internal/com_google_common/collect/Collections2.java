/*
 * Copyright (C) 2008 The Guava Authors
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

package org.robobinding.internal.com_google_common.collect;

import java.util.Collection;

/**
 * Provides static methods for working with {@code Collection} instances.
 *
 * @author Chris Povirk
 * @author Mike Bostock
 * @author Jared Levy
 * @since 2 (imported from Google Collections Library)
 */
//@GwtCompatible
public final class Collections2 {
  private Collections2() {}

  /**
   * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
   */
  static <T> Collection<T> cast(Iterable<T> iterable) {
    return (Collection<T>) iterable;
  }

}