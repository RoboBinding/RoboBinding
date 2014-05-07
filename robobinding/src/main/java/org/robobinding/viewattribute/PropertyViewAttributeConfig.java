package org.robobinding.viewattribute;

import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

import com.google.common.base.Objects;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyViewAttributeConfig<T extends View> extends AbstractViewAttributeConfig<T> {
    private ValueModelAttribute attribute;

    public PropertyViewAttributeConfig(T view, ValueModelAttribute attribute) {
	super(view);
	this.attribute = attribute;
    }

    public ValueModelAttribute getAttribute() {
	return attribute;
    }

    @Override
    public boolean equals(Object other) {
	if (this == other)
	    return true;
	if (!(other instanceof PropertyViewAttributeConfig))
	    return false;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final PropertyViewAttributeConfig<T> that = (PropertyViewAttributeConfig) other;
	return super.equals(that) && Objects.equal(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
	return super.hashCode() + Objects.hashCode(attribute);
    }
}
