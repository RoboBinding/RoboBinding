package org.robobinding.attribute;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildAttributeResolverMappingsTest {
	private static final String ATTRIBUTE = "attribute";

	private ChildAttributeResolverMappings mappings;

	@Before
	public void setUp() {
		mappings = new ChildAttributeResolverMappings();
	}

	@Test
	public void givenResolverForAttribute_whenAskResolverForAttribute_thenReturnExpectedResolver() {
		ChildAttributeResolver expectedResolver = givenResolverForAttribute();

		ChildAttributeResolver actualResolver = mappings.resolverFor(ATTRIBUTE);

		assertThat(actualResolver, sameInstance(expectedResolver));
	}

	private ChildAttributeResolver givenResolverForAttribute() {
		ChildAttributeResolver resolver = mock(ChildAttributeResolver.class);
		mappings.map(resolver, ATTRIBUTE);
		return resolver;
	}

	@Test(expected = RuntimeException.class)
	public void givenNoResolverForAttribute_whenAskResolverForAttribute_thenThrowException() {
		mappings.resolverFor(ATTRIBUTE);
	}
}
