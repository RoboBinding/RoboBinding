package org.robobinding.viewattribute.grouped;

import java.util.Map;

import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class GroupedViewAttributeBinderFactory {
	private final GroupedViewAttributeFactory<Object> factory;
	private final GroupAttributesResolver groupAttributesResolver;
	private final ViewAttributeBinderFactory viewAttributeBinderFactory;
	
	@SuppressWarnings("unchecked")
	public GroupedViewAttributeBinderFactory(GroupedViewAttributeFactory<?> factory, 
			GroupAttributesResolver groupAttributesResolver,
			ViewAttributeBinderFactory viewAttributeBinderFactory) {
		this.factory = (GroupedViewAttributeFactory<Object>)factory;
		this.groupAttributesResolver = groupAttributesResolver;
		this.viewAttributeBinderFactory = viewAttributeBinderFactory;
	}

	public GroupedViewAttributeBinder create(Object view, Map<String, String> presentAttributeMappings) {
		PendingGroupAttributes pendingGroupAttributes = new PendingGroupAttributes(presentAttributeMappings);
		GroupedViewAttribute<Object> viewAttribute = factory.create();
		ResolvedGroupAttributes resolvedGroupAttributes = groupAttributesResolver.resolve(pendingGroupAttributes, viewAttribute);
		ChildViewAttributesBuilderImpl<Object> childViewAttributesBuilder = new ChildViewAttributesBuilderImpl<Object>(
				resolvedGroupAttributes, viewAttributeBinderFactory);

		return new GroupedViewAttributeBinder(view, viewAttribute, childViewAttributesBuilder);

	}
}
