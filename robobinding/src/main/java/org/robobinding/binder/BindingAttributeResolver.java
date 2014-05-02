package org.robobinding.binder;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import org.robobinding.PendingAttributesForView;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.viewattribute.ViewAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingAttributeResolver {
    private final ByBindingAttributeMappingsResolverFinder byBindingAttributeMappingsResolverFinder;

    public BindingAttributeResolver(ByBindingAttributeMappingsResolverFinder byBindingAttributeMappingsResolverFinder) {
	this.byBindingAttributeMappingsResolverFinder = byBindingAttributeMappingsResolverFinder;
    }

    public ViewResolutionResult resolve(PendingAttributesForView pendingAttributesForView) {
	List<ViewAttribute> resolvedViewAttributes = newArrayList();

	Iterable<ByBindingAttributeMappingsResolver>  resolvers = byBindingAttributeMappingsResolverFinder.findCandidateResolvers(
		pendingAttributesForView.getView());
	for (ByBindingAttributeMappingsResolver resolver : resolvers) {
	    Collection<ViewAttribute> newResolvedViewAttributes = resolver.resolve(pendingAttributesForView);
	    resolvedViewAttributes.addAll(newResolvedViewAttributes);

	    if (pendingAttributesForView.isEmpty())
		break;
	}

	ViewResolutionErrors errors = pendingAttributesForView.getResolutionErrors();
	ResolvedBindingAttributesForView resolvedBindingAttributes = new ResolvedBindingAttributesForView(
		pendingAttributesForView.getView(), resolvedViewAttributes);

	return new ViewResolutionResult(resolvedBindingAttributes, errors);
    }
}
