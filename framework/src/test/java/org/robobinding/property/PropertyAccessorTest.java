package org.robobinding.property;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessorTest {

	@Test
	public void givenAnnotatedProperty_whenHasAnnotation_thenReturnTrue() {
		PropertyAccessor propertyAccessor = new PropertyAccessor(null, getPropertyDescriptor(Bean.ANNOTATED_PROPERTY));

		boolean result = propertyAccessor.hasAnnotation(PropertyAnnotation.class);

		assertTrue(result);
	}

	@Test
	public void givenNotAnnotatedProperty_whenHasAnnotation_thenReturnFalse() {
		PropertyAccessor propertyAccessor = new PropertyAccessor(null, getPropertyDescriptor(Bean.NOT_ANNOTATED_PROPERTY));

		boolean result = propertyAccessor.hasAnnotation(PropertyAnnotation.class);

		Assert.assertFalse(result);
	}

	@Test
	public void givenAnnotatedProperty_whenGetAnnotation_thenReturnAnnotationInstance() {
		PropertyAccessor propertyAccessor = new PropertyAccessor(null, getPropertyDescriptor(Bean.ANNOTATED_PROPERTY));

		PropertyAnnotation annotation = propertyAccessor.getAnnotation(PropertyAnnotation.class);

		assertNotNull(annotation);
	}

	@Test(expected = RuntimeException.class)
	public void givenNotAnnotatedProperty_whenGetAnnotation_thenThrowException() {
		PropertyAccessor propertyAccessor = new PropertyAccessor(null, getPropertyDescriptor(Bean.NOT_ANNOTATED_PROPERTY));

		propertyAccessor.getAnnotation(PropertyAnnotation.class);
	}

	private static PropertyDescriptor getPropertyDescriptor(String propertyName) {
		Map<String, PropertyDescriptor> propertyDescriptorMap = PropertyUtils.getPropertyDescriptorMap(Bean.class);
		return propertyDescriptorMap.get(propertyName);
	}

	public static class Bean {
		public static final String ANNOTATED_PROPERTY = "annotatedProperty";
		public static final String NOT_ANNOTATED_PROPERTY = "notAnnotatedProperty";

		@PropertyAnnotation
		public boolean getAnnotatedProperty() {
			return true;
		}

		public boolean getNotAnnotatedProperty() {
			return true;
		}
	}
}
