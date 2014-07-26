package org.robobinding.property;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.robobinding.internal.java_beans.PropertyDescriptor;
import org.robobinding.util.MethodUtils;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessorTest {
    @Test
    public void givenReadOnlyProperty_whenGetValue_thenReturnExpectedValue() {
	Bean bean = new Bean();
        PropertyAccessor propertyAccessor = new PropertyAccessor(bean, getPropertyDescriptor(Bean.READ_ONLY_PROPERTY));
    
        Object actualValue = propertyAccessor.getValue();
    
        Assert.assertEquals(bean.getReadOnlyProperty(), actualValue);
    }

    @Test(expected = RuntimeException.class)
    public void givenReadOnlyProperty_whenSetValue_thenThrowException() {
	Bean bean = new Bean();
        PropertyAccessor propertyAccessor = new PropertyAccessor(bean, getPropertyDescriptor(Bean.READ_ONLY_PROPERTY));
    
        propertyAccessor.setValue(Boolean.FALSE);
    }

    @Test
    public void givenWriteOnlyProperty_whenSetValue_thenValueSet() {
	Bean bean = new Bean();
        PropertyAccessor propertyAccessor = new PropertyAccessor(bean, getPropertyDescriptor(Bean.WRITE_ONLY_PROPERTY));
    
        propertyAccessor.setValue(Boolean.FALSE);
    
        assertThat(bean.writeOnlyPropertyValue, is(Boolean.FALSE));
    }

    @Test(expected = RuntimeException.class)
    public void givenWriteOnlyProperty_whenGetValue_thenThrowException() {
	Bean bean = new Bean();
        PropertyAccessor propertyAccessor = new PropertyAccessor(bean, getPropertyDescriptor(Bean.WRITE_ONLY_PROPERTY));
    
        propertyAccessor.getValue();
    }

    @Test
    public void givenHasReadMethod_whenCheckReadable_thenPassCheck() {
	Method readMethod = aMockMethod();
	PropertyDescriptor descriptor = mock(PropertyDescriptor.class);
	when(descriptor.getReadMethod()).thenReturn(readMethod);
	PropertyAccessor propertyAccessor = new PropertyAccessor(null, descriptor);

	propertyAccessor.checkReadable();
    }
    
    private Method aMockMethod() {
	return MethodUtils.getAccessibleMethod(Bean.class, Bean.SOME_METHOD, new Class[0]);
    }

    @Test(expected = RuntimeException.class)
    public void givenNoReadMethod_whenCheckReadable_thenThrowException() {
	PropertyDescriptor descriptor = mock(PropertyDescriptor.class);
	when(descriptor.getReadMethod()).thenReturn(null);
	PropertyAccessor propertyAccessor = new PropertyAccessor(null, descriptor);
    
	propertyAccessor.checkReadable();
    }

    @Test
    public void givenHasWriteMethod_whenCheckWritable_thenPassCheck() {
	PropertyDescriptor descriptor = mock(PropertyDescriptor.class);
	Method writeMethod = aMockMethod();
	when(descriptor.getWriteMethod()).thenReturn(writeMethod);
	PropertyAccessor propertyAccessor = new PropertyAccessor(null, descriptor);

	propertyAccessor.checkWritable();
    }

    @Test(expected = RuntimeException.class)
    public void givenNoWriteMethod_whenCheckWritable_thenThrowException() {
	PropertyDescriptor descriptor = mock(PropertyDescriptor.class);
	when(descriptor.getWriteMethod()).thenReturn(null);
	PropertyAccessor propertyAccessor = new PropertyAccessor(null, descriptor);

	propertyAccessor.checkWritable();
    }

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
	public static final String READ_ONLY_PROPERTY = "readOnlyProperty";
	public static final String WRITE_ONLY_PROPERTY = "writeOnlyProperty";
	public static final String ANNOTATED_PROPERTY = "annotatedProperty";
	public static final String NOT_ANNOTATED_PROPERTY = "notAnnotatedProperty";
	public static final String SOME_METHOD = "someMethod";

	private boolean writeOnlyPropertyValue;

	public boolean getReadOnlyProperty() {
	    return true;
	}

	public void setWriteOnlyProperty(boolean newValue) {
	    writeOnlyPropertyValue = newValue;
	}

	@PropertyAnnotation
	public boolean getAnnotatedProperty() {
	    return true;
	}

	public boolean getNotAnnotatedProperty() {
	    return true;
	}
	
	public void someMethod() {
	}
    }
}
