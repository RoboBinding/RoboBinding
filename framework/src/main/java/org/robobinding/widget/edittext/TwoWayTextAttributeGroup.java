package org.robobinding.widget.edittext;

import static org.robobinding.attribute.ChildAttributeResolvers.enumChildAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.MalformedAttributeException;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayTextAttributeGroup implements GroupedViewAttribute<EditText> {
	public static final String TEXT = "text";
	public static final String VALUE_COMMIT_MODE = "valueCommitMode";

	@Override
	public String[] getCompulsoryAttributes() {
		return new String[] { TEXT };
	}

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
		resolverMappings.map(valueModelAttributeResolver(), TEXT);
		resolverMappings.map(enumChildAttributeResolver(ValueCommitMode.class), VALUE_COMMIT_MODE);
	}

	@Override
	public void validateResolvedChildAttributes(ResolvedGroupAttributes resolvedGroupAttributes) {
		if (valueCommitModeSpecified(resolvedGroupAttributes) && isTextAttributeNotTwoWayBinding(resolvedGroupAttributes))
			throw new MalformedAttributeException(VALUE_COMMIT_MODE,
					"The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");
	}

	private boolean valueCommitModeSpecified(ResolvedGroupAttributes resolvedGroupAttributes) {
		return resolvedGroupAttributes.hasAttribute(VALUE_COMMIT_MODE);
	}

	private boolean isTextAttributeNotTwoWayBinding(ResolvedGroupAttributes resolvedGroupAttributes) {
		ValueModelAttribute textAttribute = resolvedGroupAttributes.valueModelAttributeFor(TEXT);
		return !textAttribute.isTwoWayBinding();
	}

	@Override
	public void setupChildViewAttributes(EditText view, ChildViewAttributesBuilder<EditText> childViewAttributesBuilder) {
		TwoWayTextAttribute textAttribute = new TwoWayTextAttribute();
		childViewAttributesBuilder.add(TEXT, textAttribute);
		textAttribute.setValueCommitMode(determineValueCommitMode(childViewAttributesBuilder));
	}

	private ValueCommitMode determineValueCommitMode(ChildViewAttributesBuilder<EditText> childViewAttributesBuilder) {
		if (valueCommitModeSpecified(childViewAttributesBuilder)) {
			EnumAttribute<ValueCommitMode> enumAttribute = childViewAttributesBuilder.enumAttributeFor(VALUE_COMMIT_MODE);
			return enumAttribute.getValue();
		}

		return ValueCommitMode.ON_CHANGE;
	}

	private boolean valueCommitModeSpecified(ChildViewAttributesBuilder<EditText> childViewAttributesBuilder) {
		return childViewAttributesBuilder.hasAttribute(VALUE_COMMIT_MODE);
	}

	@Override
	public void postBind(EditText view, BindingContext bindingContext) {
	}
}
