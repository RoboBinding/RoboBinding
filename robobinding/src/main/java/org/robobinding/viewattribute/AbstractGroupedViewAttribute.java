package org.robobinding.viewattribute;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ResolvedGroupAttributes;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements ViewAttribute, ChildAttributesResolver {
    private static final String[] NO_COMPULSORY_ATTRIBUTES = new String[0];

    protected T view;
    private ChildViewAttributesBuilder<T> childViewAttributesBuilder;
    private ChildViewAttributes childViewAttributes;

    public void initialize(T view, ChildViewAttributesBuilder<T> childViewAttributesBuilder) {
	this.view = view;
	this.childViewAttributesBuilder = childViewAttributesBuilder;
    }

    @Override
    public final void bindTo(BindingContext bindingContext) {
	childViewAttributes = initializeChildViewAttributes(bindingContext);
	childViewAttributes.bindTo(bindingContext);
	postBind(bindingContext);
    }

    private ChildViewAttributes initializeChildViewAttributes(BindingContext bindingContext)
    {
	setupChildViewAttributes(childViewAttributesBuilder, bindingContext);
	return childViewAttributesBuilder.build();
    }

    protected abstract void setupChildViewAttributes(ChildViewAttributesBuilder<T> childViewAttributesBuilder, BindingContext bindingContext);

    protected void postBind(BindingContext bindingContext) {

    }

    @Override
    public final void preInitializeView(BindingContext bindingContext) {
	childViewAttributes.preInitializeView(bindingContext);
    }

    @Override
    public String[] getCompulsoryAttributes() {
        return NO_COMPULSORY_ATTRIBUTES;
    }

    @Override
    public abstract void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings);

    @Override
    public void validateResolvedChildAttributes(ResolvedGroupAttributes resolvedGroupAttributes) {
    }
}
