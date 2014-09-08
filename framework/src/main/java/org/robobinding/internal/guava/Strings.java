/*
 * NOTICE: THE FILE HAS BEEN MODIFIED TO SUIT THE NEEDS OF THE PROJECT.
 *
 * Copyright (C) 2010 The Guava Authors
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


/**
 * Static utility methods pertaining to {@code String} or {@code CharSequence}
 * instances.
 *
 * @author Kevin Bourrillion
 * @since 3.0
 */
public final class Strings {
  private Strings() { }

  /**
   * Returns {@code true} if the given string is null or is the empty string.
   *
   * <p>Consider normalizing your string references with {@link #nullToEmpty}.
   * If you do, you can use {@link String#isEmpty()} instead of this
   * method, and you won't need special null-safe forms of methods like {@link
   * String#toUpperCase} either. Or, if you'd like to normalize "in the other
   * direction," converting empty strings to {@code null}, you can use {@link
   * #emptyToNull}.
   *
   * @param string a string reference to check
   * @return {@code true} if the string is null or is the empty string
   */
  public static boolean isNullOrEmpty(String string) {
    return string == null || string.length() == 0; // string.isEmpty() in Java 6
  }
}