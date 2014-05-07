package org.robobinding.viewattribute.impl;

import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttributeInitializer;
import org.robobinding.viewattribute.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ResolvedGroupAttributesFactory;
import org.robobinding.viewattribute.StandaloneViewAttributeInitializer;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewAttributeInitializer {
    private final StandaloneViewAttributeInitializer delegate;
    private final ResolvedGroupAttributesFactory resolvedGroupAttributesFactory;
    private ChildViewAttributeInitializer childViewAttributeInitializer;

    public ViewAttributeInitializer(StandaloneViewAttributeInitializer viewAttributeInitializer,
	    ResolvedGroupAttributesFactory resolvedGroupAttributesFactory) {
	delegate = viewAttributeInitializer;
	this.resolvedGroupAttributesFactory = resolvedGroupAttributesFactory;
    }

    public PropertyViewAttribute<View> initializePropertyViewAttribute(
	    View view, PropertyViewAttribute<View> propertyViewAttribute, ValueModelAttribute attribute) {
	delegate.setView(view);
	delegate.initializePropertyViewAttribute(propertyViewAttribute, attribute);
	return propertyViewAttribute;
    }

    public AbstractCommandViewAttribute<View> initializeCommandViewAttribute(
	    View view, AbstractCommandViewAttribute<View> commandViewAttribute, CommandAttribute attribute) {
	delegate.setView(view);
	delegate.initializeCommandViewAttribute(commandViewAttribute, attribute);
	return commandViewAttribute;
    }

    public AbstractGroupedViewAttribute<View> initializeGroupedViewAttribute(
	    View view, AbstractGroupedViewAttribute<View> groupedViewAttribute, PendingGroupAttributes pendingGroupAttributes) {
	ResolvedGroupAttributes resolvedGroupAttributes = resolvedGroupAttributesFactory.create(pendingGroupAttributes, groupedViewAttribute);
	ChildViewAttributesBuilder<View> childViewAttributesBuilder = new ChildViewAttributesBuilder<View>(
		resolvedGroupAttributes, safeGetChildViewAttributeInitializer());
	groupedViewAttribute.initialize(view, childViewAttributesBuilder);
	return groupedViewAttribute;
    }

    private ChildViewAttributeInitializer safeGetChildViewAttributeInitializer() {
	if (childViewAttributeInitializer == null) {
	    childViewAttributeInitializer = new ChildViewAttributeInitializer(delegate);
	}
	return childViewAttributeInitializer;
    }
}