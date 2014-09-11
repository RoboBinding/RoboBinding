package org.robobinding.property;

import static org.mockito.Mockito.mock;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessorFactoryTest {
    @Test(expected = RuntimeException.class)
    public void whenCreateNonExistingPropertyAccessor_thenThrowException() {
	Map<String, PropertyDescriptor> emptyPropertyDescriptorMap = Maps.newHashMap();
	PropertyAccessorFactory factory = new PropertyAccessorFactory(mock(Object.class), emptyPropertyDescriptorMap);
	
	factory.create("nonExistingProperty");
    }

}
