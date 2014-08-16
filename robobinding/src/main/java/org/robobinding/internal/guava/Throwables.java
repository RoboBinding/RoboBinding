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

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Static utility methods pertaining to instances of {@link Throwable}.
 *
 * <p>See the Guava User Guide entry on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/ThrowablesExplained">
 * Throwables</a>.
 *
 * @author Kevin Bourrillion
 * @author Ben Yu
 * @since 1.0
 */
public final class Throwables {
  private Throwables() { }

  /**
   * Returns a string containing the result of
   * {@link Throwable#toString() toString()}, followed by the full, recursive
   * stack trace of {@code throwable}. Note that you probably should not be
   * parsing the resulting string; if you need programmatic access to the stack
   * frames, you can call {@link Throwable#getStackTrace()}.
   */
  public static String getStackTraceAsString(Throwable throwable) {
    StringWriter stringWriter = new StringWriter();
    throwable.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }
}