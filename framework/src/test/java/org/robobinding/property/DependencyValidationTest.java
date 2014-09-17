package org.robobinding.property;

import static org.robobinding.property.MockPropertyAccessorBuilder.aPropertyAccessor;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependencyValidationTest {
	private static final String PROPERTY_NAME = "property0";

	private Set<String> existingPropertyNames;
	private DependencyValidation validation;

	@Before
	public void setUp() {
		existingPropertyNames = Sets.newHashSet(PROPERTY_NAME, "property1", "property2", "property3");
		validation = new DependencyValidation(existingPropertyNames);
	}

	@Test
	public void givenValidDependentProperties_thenSuccessful() {
		validation.validate(aPropertyAccessor().withPropertyName(PROPERTY_NAME).build(), Sets.newHashSet("property1", "property2"));
	}

	@Test(expected = RuntimeException.class)
	public void givenDependingOnSelf_thenThrowException() {
		validation.validate(aPropertyAccessor().withPropertyName(PROPERTY_NAME).build(), Sets.newHashSet("property1", PROPERTY_NAME, "property2"));
	}

	@Test(expected = RuntimeException.class)
	public void whenCreateWithSomeNonExistingDependentProperties_thenThrowException() {
		validation.validate(aPropertyAccessor().withPropertyName(PROPERTY_NAME).build(), Sets.newHashSet("property1", "nonExistingProperty", "property2"));
	}

}
