package org.robobinding.dynamicbinding;

import org.robobinding.internal.guava.Preconditions;
import org.robobinding.property.PropertyDescriptor.Setter;
import org.robobinding.util.ArrayUtils;
import org.robobinding.viewattribute.ViewListeners;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsWithCreate;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DynamicViewBindingDescription<T extends View> {
    private final BindingAttributeMappingsWithCreate<T> bindingAttributeMappings;
    private final ViewPropertySetterLocator viewPropertySetterLocator;
    private final ViewBindingApplierFactory<T> viewBindingApplierFactory;
    
    private Class<? extends ViewListeners> viewListenersClass;

    DynamicViewBindingDescription(BindingAttributeMappingsWithCreate<T> bindingAttributeMappings, 
	    ViewPropertySetterLocator viewPropertySetterFactory,
            ViewBindingApplierFactory<T> viewBindingApplierFactory) {
        this.bindingAttributeMappings = bindingAttributeMappings;
        this.viewPropertySetterLocator = viewPropertySetterFactory;
        this.viewBindingApplierFactory = viewBindingApplierFactory;
    }

    private DynamicViewBindingDescription<T> oneWayProperty(String attributeName) {
	org.robobinding.util.Preconditions.checkNotBlank(attributeName, "attributeName must not be empty");
	
	Setter viewPropertySetter = viewPropertySetterLocator.get(attributeName);
	bindingAttributeMappings.mapProperty(new DynamicPropertyViewAttributeFactory<T>(viewPropertySetter), attributeName);
	return this;
    }
    
    public DynamicViewBindingDescription<T> oneWayProperties(String... attributeNames) {
	Preconditions.checkArgument(ArrayUtils.isNotEmpty(attributeNames), "attributeNames must not be empty");
	for (String attributeName : attributeNames) {
	    oneWayProperty(attributeName);
	}
	return this;
    }
    
    public DynamicViewBindingDescription<T> event(Class<? extends EventViewAttribute<T>> eventViewAttributeClass, String attributeName) {
	bindingAttributeMappings.mapEvent(eventViewAttributeClass, attributeName);
	return this;
    }
    
    public DynamicViewBindingDescription<T> event(EventViewAttributeFactory<T> eventViewAttributeFactory, String attributeName) {
	bindingAttributeMappings.mapEvent(eventViewAttributeFactory, attributeName);
	return this;
    }
    
    public DynamicViewBindingDescription<T> setViewListenersClass(Class<? extends ViewListeners> viewListenersClass) {
	this.viewListenersClass = viewListenersClass;
	return this;
    }
    
    public ViewBindingApplier<T> build() {
	return viewBindingApplierFactory.create(new BindingAttributeMappingsProvider<T>() {
	    @Override
	    public InitailizedBindingAttributeMappings<T> createBindingAttributeMappings() {
	        return bindingAttributeMappings.createInitailizedBindingAttributeMappings();
	    }
	}, viewListenersClass);
    }

}
