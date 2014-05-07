package org.robobinding.viewattribute;

import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributeInitializer {
    private final StandaloneViewAttributeInitializer standaloneViewAttributeInitializer;

    public ChildViewAttributeInitializer(StandaloneViewAttributeInitializer standaloneViewAttributeInitializer) {
	this.standaloneViewAttributeInitializer = standaloneViewAttributeInitializer;
    }

    public void initializePropertyViewAttribute(
		PropertyViewAttribute<? extends View> propertyViewAttribute, ValueModelAttribute attribute) {
	standaloneViewAttributeInitializer.initializePropertyViewAttribute(propertyViewAttribute, attribute);
    }

    @SuppressWarnings("unchecked")
    public void initializeChildViewAttribute(ChildViewAttribute childAttribute,
	    AbstractAttribute attribute) {
	if (childAttribute instanceof ChildViewAttributeWithAttribute<?>) {
	    ((ChildViewAttributeWithAttribute<AbstractAttribute>) childAttribute).setAttribute(attribute);
	}
    }

}
