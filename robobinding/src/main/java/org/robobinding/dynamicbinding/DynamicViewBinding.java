package org.robobinding.dynamicbinding;

import org.robobinding.viewattribute.ViewBinding;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsWithCreate;

import android.view.View;

import com.google.common.base.Preconditions;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DynamicViewBinding {
    
    public <T extends View> DynamicViewBindingDescription<T> forView(Class<T> viewClass) {
	return newViewAttributeBinding(viewClass, new BindingAttributeMappingsImpl<T>());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends View> DynamicViewBindingDescription<T> extend(Class<? extends View> viewClass, 
	    ViewBinding<T> existingBindingAttributeMapper) {
	Preconditions.checkNotNull(existingBindingAttributeMapper, "existingViewBinding must not be null");
	return newViewAttributeBinding(viewClass, new ExtensionBindingAttributeMappings(existingBindingAttributeMapper));
    }
    
    private <T extends View> DynamicViewBindingDescription<T> newViewAttributeBinding(Class<T> viewClass, 
	    BindingAttributeMappingsWithCreate<T> bindingViewAttributeMappings) {
	Preconditions.checkNotNull(viewClass, "viewClass must not be null");
	
	ViewBindingApplierFactory<T> viewBindingApplierFactory = new ViewBindingApplierFactory<T>(viewClass);

	return new DynamicViewBindingDescription<T>(bindingViewAttributeMappings, 
		new ViewPropertySetterLocator(viewClass), 
		viewBindingApplierFactory);
    }
}
