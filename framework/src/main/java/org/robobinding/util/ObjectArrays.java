package org.robobinding.util;

import java.lang.reflect.Array;

/**
 * Migrated from {@link com.google.common.collect.ObjectArrays}
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ObjectArrays {
  /**
   * Returns a new array that appends {@code element} to {@code array}.
   *
   * @param array the array of elements to prepend
   * @param element the element to append to the end
   * @return an array whose size is one larger than {@code array}, with
   *     the same contents as {@code array}, plus {@code element} occupying the
   *     last position.
   */
  public static <T> T[] concat(T[] array, T element) {
    T[] result = arraysCopyOf(array, array.length + 1);
    result[array.length] = element;
    return result;
  }
  /** GWT safe version of Arrays.copyOf. */
  static <T> T[] arraysCopyOf(T[] original, int newLength) {
    T[] copy = newArray(original, newLength);
    System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
    return copy;
  }
  /**
   * Returns a new array of the given length with the same type as a reference
   * array.
   *
   * @param reference any array of the desired type
   * @param length the length of the new array
   */
  static <T> T[] newArray(T[] reference, int length) {
    Class<?> type = reference.getClass().getComponentType();

    // the cast is safe because
    // result.getClass() == reference.getClass().getComponentType()
    @SuppressWarnings("unchecked")
    T[] result = (T[]) Array.newInstance(type, length);
    return result;
  }
}
