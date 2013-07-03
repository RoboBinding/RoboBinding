/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
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
