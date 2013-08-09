/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute;

import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributesBuilder<T extends View> {
    private final PendingGroupAttributes pendingGroupAttributes;
    private final ChildViewAttributeInitializer viewAttributeInitializer;

    public ChildViewAttributesBuilder(PendingGroupAttributes pendingGroupAttributes, ChildViewAttributeInitializer viewAttributeInitializer) {
	this.pendingGroupAttributes = pendingGroupAttributes;
	this.viewAttributeInitializer = viewAttributeInitializer;
    }

    public ChildViewAttributes<T> build(ChildViewAttributesResolver childViewAttributesResolver) {
	pendingGroupAttributes.assertAttributesArePresent(childViewAttributesResolver.getCompulsoryAttributes());
	ChildAttributeResolverMappings resolverMappings = createResolverMappings(childViewAttributesResolver);
	ResolvedGroupAttributes resolvedGroupAttributes = new ResolvedGroupAttributes(pendingGroupAttributes, resolverMappings);

	childViewAttributesResolver.validateResolvedChildAttributes(resolvedGroupAttributes);

	return new ChildViewAttributes<T>(resolvedGroupAttributes, viewAttributeInitializer);
    }

    private ChildAttributeResolverMappings createResolverMappings(ChildViewAttributesResolver childViewAttributesResolver) {
	ChildAttributeResolverMappings resolverMappings = new ChildAttributeResolverMappings();
	childViewAttributesResolver.mapChildAttributeResolvers(resolverMappings);
	return resolverMappings;
    }

}
