package org.robobinding.util;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Migrated from {@link com.google.common.collect.Sets}
 * 
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Sets {
	/**
	 * Creates a <i>mutable</i>, initially empty {@code HashSet} instance.
	 *
	 * <p><b>Note:</b> if mutability is not required, use ImmutableSet#of() instead. If
	 * {@code E} is an {@link Enum} type, use {@link EnumSet#noneOf} instead. Otherwise, strongly
	 * consider using a {@code LinkedHashSet} instead, at the cost of increased memory footprint, to
	 * get deterministic iteration behavior.
	 *
	 * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and should be treated as
	 * deprecated. Instead, use the {@code HashSet} constructor directly, taking advantage of the new
	 * <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
	 */
	public static <E> HashSet<E> newHashSet() {
		return new HashSet<E>();
	} 
	/**
	 * Creates a <i>mutable</i>, empty {@code LinkedHashSet} instance.
	 *
	 * <p><b>Note:</b> if mutability is not required, use ImmutableSet#of() instead.
	 *
	 * <p><b>Note for Java 7 and later:</b> this method is now unnecessary and should be treated as
	 * deprecated. Instead, use the {@code LinkedHashSet} constructor directly, taking advantage of
	 * the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
	 *
	 * @return a new, empty {@code LinkedHashSet}
	 */
	public static <E> LinkedHashSet<E> newLinkedHashSet() {
		return new LinkedHashSet<E>();
	}
  /**
   * Creates a <i>mutable</i> {@code HashSet} instance initially containing the given elements.
   *
   * <p><b>Note:</b> if elements are non-null and won't be added or removed after this point, use
   * ImmutableSet#of() or ImmutableSet#copyOf(Object[]) instead. If {@code E} is an
   * {@link Enum} type, use {@link EnumSet#of(Enum, Enum[])} instead. Otherwise, strongly consider
   * using a {@code LinkedHashSet} instead, at the cost of increased memory footprint, to get
   * deterministic iteration behavior.
   *
   * <p>This method is just a small convenience, either for {@code newHashSet(}{@link Arrays#asList
   * asList}{@code (...))}, or for creating an empty set then calling {@link Collections#addAll}.
   * This method is not actually very useful and will likely be deprecated in the future.
   */
  @SafeVarargs
  public static <E> HashSet<E> newHashSet(E... elements) {
    HashSet<E> set = newHashSetWithExpectedSize(elements.length);
    Collections.addAll(set, elements);
    return set;
  }

  /**
   * Creates a {@code HashSet} instance, with a high enough initial table size that it <i>should</i>
   * hold {@code expectedSize} elements without resizing. This behavior cannot be broadly
   * guaranteed, but it is observed to be true for OpenJDK 1.7. It also can't be guaranteed that the
   * method isn't inadvertently <i>oversizing</i> the returned set.
   *
   * @param expectedSize the number of elements you expect to add to the
   *        returned set
   * @return a new, empty {@code HashSet} with enough capacity to hold {@code
   *         expectedSize} elements without resizing
   * @throws IllegalArgumentException if {@code expectedSize} is negative
   */
  public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
    return new HashSet<E>(Maps.capacity(expectedSize));
  }
  /**
   * Creates a <i>mutable</i> {@code HashSet} instance containing the given elements. A very thin
   * convenience for creating an empty set then calling {@link Collection#addAll} or {@link
   * Iterables#addAll}.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are non-null, use 
   * ImmutableSet#copyOf(Iterable) instead. (Or, change {@code elements} to be a 
   * FluentIterable and call {@code elements.toSet()}.)
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, use {@link #newEnumSet(Iterable, Class)}
   * instead.
   *
   * <p><b>Note for Java 7 and later:</b> if {@code elements} is a {@link Collection}, you don't
   * need this method. Instead, use the {@code HashSet} constructor directly, taking advantage of
   * the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   *
   * <p>Overall, this method is not very useful and will likely be deprecated in the future.
   */
  public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
    return (elements instanceof Collection)
        ? new HashSet<E>(Collections2.cast(elements))
        : newHashSet(elements.iterator());
  }
  /**
   * Creates a <i>mutable</i> {@code HashSet} instance containing the given elements. A very thin
   * convenience for creating an empty set and then calling {@link Iterators#addAll}.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are non-null, use
   * ImmutableSet#copyOf(Iterator) instead.
   *
   * <p><b>Note:</b> if {@code E} is an {@link Enum} type, you should create an {@link EnumSet}
   * instead.
   *
   * <p>Overall, this method is not very useful and will likely be deprecated in the future.
   */
  public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
    HashSet<E> set = newHashSet();
    Iterators.addAll(set, elements);
    return set;
  }
}
