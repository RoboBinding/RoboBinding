package org.robobinding.property;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPropertyAccessorBuilder {
    private PropertyAccessor propertyAccessor;
    private MockPropertyAccessorBuilder() {
	propertyAccessor = mock(PropertyAccessor.class);	
    }
    
    public MockPropertyAccessorBuilder withPropertyName(String propertyName) {
	when(propertyAccessor.getPropertyName()).thenReturn(propertyName);
	return this;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MockPropertyAccessorBuilder withPropertyType(Class<?> type) {
	when(propertyAccessor.getPropertyType()).thenReturn((Class) type);
	return this;
    }
    
    public MockPropertyAccessorBuilder withAnnotation(Class<? extends Annotation> annotationClass, boolean hasAnnotation) {
	when(propertyAccessor.hasAnnotation(eq(annotationClass))).thenReturn(hasAnnotation);
	return this;
    }
    
    public MockPropertyAccessorBuilder withValue(Object value) {
	when(propertyAccessor.getValue()).thenReturn(value);
	return this;
    }
    
    public PropertyAccessor build() {
	return propertyAccessor;
    }
    
    public static MockPropertyAccessorBuilder aPropertyAccessor() {
	return new MockPropertyAccessorBuilder();
    }

}
