package org.robobinding.binder;

import java.util.Collection;
import java.util.List;

import org.robobinding.PendingAttributesForView;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.util.Lists;
import org.robobinding.viewattribute.ViewAttributeBinder;

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
		List<ViewAttributeBinder> resolvedViewAttributes = Lists.newArrayList();

		Iterable<ByBindingAttributeMappingsResolver> resolvers = byBindingAttributeMappingsResolverFinder.findCandidates(
				pendingAttributesForView.getView());
		for (ByBindingAttributeMappingsResolver resolver : resolvers) {
			Collection<ViewAttributeBinder> newResolvedViewAttributes = resolver.resolve(pendingAttributesForView);
			resolvedViewAttributes.addAll(newResolvedViewAttributes);

			if (pendingAttributesForView.isEmpty())
				break;
		}

		ViewResolutionErrors errors = pendingAttributesForView.getResolutionErrors();
		ResolvedBindingAttributesForView resolvedBindingAttributes = new ResolvedBindingAttributesForView(pendingAttributesForView.getView(),
				resolvedViewAttributes);

		return new ViewResolutionResult(resolvedBindingAttributes, errors);
	}
}
