package org.robobinding.attribute;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface PropertyAttributeVisitor<R> {
	R visitValueModel(ValueModelAttribute attribute);
	R visitStaticResource(StaticResourceAttribute attribute);
	R visitStaticResources(StaticResourcesAttribute attribute);
}
