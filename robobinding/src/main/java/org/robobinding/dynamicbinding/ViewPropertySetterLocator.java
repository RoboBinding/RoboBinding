package org.robobinding.dynamicbinding;

import java.text.MessageFormat;

import org.robobinding.property.PropertyDescriptor;
import org.robobinding.property.PropertyDescriptor.Setter;
import org.robobinding.property.PropertyUtils;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ViewPropertySetterLocator {
    private final Class<? extends View> viewClass;
    
    public ViewPropertySetterLocator(Class<? extends View> viewClass) {
        this.viewClass = viewClass;
    }

    public Setter get(String attribute) {
	PropertyDescriptor descriptor = PropertyUtils.findPropertyDescriptor(viewClass, attribute);
	
	if (descriptor == null) {
	    throw new RuntimeException(MessageFormat.format("Cannot find the attribute ''{0}'' from the view ''{1}''", 
		    attribute, viewClass.getName()));
	}

	if (!descriptor.isWritable()) {
	    throw new RuntimeException(MessageFormat.format("The attribute ''{0}'' of the view ''{1}'' is not writable.", 
		    attribute, viewClass.getName()));
	}
	
	return descriptor.getSetter();
    }

}
