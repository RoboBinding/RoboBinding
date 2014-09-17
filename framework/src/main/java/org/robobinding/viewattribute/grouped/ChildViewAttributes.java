package org.robobinding.viewattribute.grouped;

import java.util.Map;

import org.robobinding.BindingContext;
import org.robobinding.viewattribute.Bindable;
import org.robobinding.viewattribute.ViewAttributeBinder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ChildViewAttributes {
	private final Map<String, Bindable> childViewAttributeMap;
	private final boolean failOnFirstBindingError;
	private final AttributeGroupBindingException bindingErrors;

	public ChildViewAttributes(Map<String, Bindable> childViewAttributeMap, boolean failOnFirstBindingError) {
		this.childViewAttributeMap = childViewAttributeMap;
		this.failOnFirstBindingError = failOnFirstBindingError;
		this.bindingErrors = new AttributeGroupBindingException();
	}

	public void bindTo(BindingContext bindingContext) {
		for (Map.Entry<String, Bindable> childViewAttributeEntry : childViewAttributeMap.entrySet()) {
			Bindable childViewAttribute = childViewAttributeEntry.getValue();

			try {
				childViewAttribute.bindTo(bindingContext);
			} catch (RuntimeException e) {
				bindingErrors.addChildAttributeError(childViewAttributeEntry.getKey(), e);

				if (failOnFirstBindingError)
					break;
			}
		}

		bindingErrors.assertNoErrors();
	}

	public void preInitializeView(BindingContext bindingContext) {
		for (Map.Entry<String, Bindable> childViewAttributeEntry : childViewAttributeMap.entrySet()) {
			Bindable childViewAttribute = childViewAttributeEntry.getValue();
			if (childViewAttribute instanceof ViewAttributeBinder) {
				((ViewAttributeBinder) childViewAttribute).preInitializeView(bindingContext);
			}
		}
	}
}