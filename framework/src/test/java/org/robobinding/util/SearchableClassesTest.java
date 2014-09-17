package org.robobinding.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SearchableClassesTest {
	private SearchableClasses searchableClasses;

	@Before
	public void setUp() {
		searchableClasses = new SearchableClasses(Sets.<Class<?>> newHashSet(GrandparentClazz.class, GrandparentSuperInterface.class, Clazz.class,
				ParentInterface.class));
	}

	@Test
	public void whenFindNearestAssignableFromUnknownClazz_thenNullIsReturned() {
		Class<?> foundClazz = searchableClasses.findNearestAssignableFrom(UnknownClazz.class);

		assertThat(foundClazz, is(nullValue()));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void whenfindNearestAssignableFrom_thenNearestAssignableIsReturned() {
		assertThat(searchableClasses.findNearestAssignableFrom(Clazz.class), sameInstance((Class) Clazz.class));

		assertThat(searchableClasses.findNearestAssignableFrom(UnregisteredParentClazz.class), sameInstance((Class) GrandparentSuperInterface.class));

		assertThat(searchableClasses.findNearestAssignableFrom(UnregisteredGrandparentInterface.class), sameInstance((Class) GrandparentSuperInterface.class));
	}

	@Test
	public void whenFindAssignablesInOrderFromUnknownClazz_thenEmptyIsReturned() {
		Queue<Class<?>> foundClasses = searchableClasses.findAssignablesInOrderFrom(UnknownClazz.class);

		assertThat(foundClasses, hasSize(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenFindAssignablesInOrderFromClazz_thenAssignablesIsReturnedInOrder() {
		Queue<Class<?>> foundClasses = searchableClasses.findAssignablesInOrderFrom(Clazz.class);

		Queue<Class<?>> expectedAssignablesInOrder = Lists.newLinkedList(Lists.newArrayList(Clazz.class, ParentInterface.class,
				GrandparentSuperInterface.class, GrandparentClazz.class));

		assertThat(foundClasses, equalTo(expectedAssignablesInOrder));
	}

	public static class UnknownClazz {
	}

	public static class Clazz extends UnregisteredParentClazz implements ParentInterface {
	}

	public interface ParentInterface extends UnregisteredParentSuperInterface {
	}

	public interface UnregisteredParentSuperInterface {
	}

	public static class UnregisteredParentClazz extends GrandparentClazz implements UnregisteredGrandparentInterface {
	}

	public interface UnregisteredGrandparentInterface extends GrandparentSuperInterface {
	}

	public interface GrandparentSuperInterface {
	}

	public static class GrandparentClazz {
	}
}
