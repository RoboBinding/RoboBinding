package org.robobinding.util;

/**
 * Migrated from {@link com.google.common.collect.CollectPreconditions}
 * 
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class CollectPreconditions {
  static int checkNonnegative(int value, String name) {
    if (value < 0) {
      throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
    }
    return value;
  }
}
