package org.robobinding.property;

import java.util.Map;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessorFactory {
    private final Object bean;
    private final Map<String, PropertyDescriptor> propertyDescriptorMap;
    
    public PropertyAccessorFactory(Object bean, Map<String, PropertyDescriptor> propertyDescriptorMap) {
	this.bean = bean;
	this.propertyDescriptorMap = propertyDescriptorMap;
    }
    
    public PropertyAccessor create(String propertyName) {
	PropertyDescriptor propertyDescriptor = propertyDescriptorMap.get(propertyName);
	
	if (propertyDescriptor == null) {
            throw new RuntimeException("The property '" + PropertyUtils.shortDescription(bean.getClass(), propertyName) 
        	    + "' does not exist");
        }
	
	return new PropertyAccessor(bean, propertyDescriptor);
    }
}
