package org.robobinding.util;

import java.io.IOException;
import java.util.Iterator;

/**
 * Migrated from {@link com.google.common.base.Joiner}
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Joiner {
  /**
   * Returns a joiner which automatically places {@code separator} between consecutive elements.
   */
  public static Joiner on(String separator) {
    return new Joiner(separator);
  }
  private final String separator;

  private Joiner(String separator) {
    this.separator = Preconditions.checkNotNull(separator);
  }
  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   */
  public final String join(Iterable<?> parts) {
    return join(parts.iterator());
  }
  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   *
   * @since 11.0
   */
  public final String join(Iterator<?> parts) {
    return appendTo(new StringBuilder(), parts).toString();
  }
  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code builder}. Identical to
   * {@link #appendTo(Appendable, Iterable)}, except that it does not throw {@link IOException}.
   *
   * @since 11.0
   */
  public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) {
    try {
      appendTo((Appendable) builder, parts);
    } catch (IOException impossible) {
      throw new AssertionError(impossible);
    }
    return builder;
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code appendable}.
   *
   * @since 11.0
   */
  public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
    Preconditions.checkNotNull(appendable);
    if (parts.hasNext()) {
      appendable.append(toString(parts.next()));
      while (parts.hasNext()) {
        appendable.append(separator);
        appendable.append(toString(parts.next()));
      }
    }
    return appendable;
  }
  
  CharSequence toString(Object part) {
	  Preconditions.checkNotNull(part); // checkNotNull for GWT (do not optimize).
    return (part instanceof CharSequence) ? (CharSequence) part : part.toString();
  }

}
