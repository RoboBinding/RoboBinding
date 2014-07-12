package org.robobinding.viewattribute.impl;

import org.robobinding.viewattribute.BindingAttributeMapper;

import android.view.View;

import com.google.common.base.Objects;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeMapperAdapter<T extends View> implements BindingAttributeMappingsProvider<T> {
    private final BindingAttributeMapper<T> bindingAttributeMapper;

    public BindingAttributeMapperAdapter(BindingAttributeMapper<T> bindingAttributeMapper) {
	this.bindingAttributeMapper = bindingAttributeMapper;
    }

    public InitailizedBindingAttributeMappings<T> createBindingAttributeMappings() {
	BindingAttributeMappingsImpl<T> bindingAttributeMappings = new BindingAttributeMappingsImpl<T>();
	bindingAttributeMapper.mapBindingAttributes(bindingAttributeMappings);
	return bindingAttributeMappings;
    }

    @Override
    public boolean equals(Object other) {
	if (this == other)
	    return true;
	if (!(other instanceof BindingAttributeMapperAdapter))
	    return false;

	@SuppressWarnings("unchecked")
	final BindingAttributeMapperAdapter<T> that = (BindingAttributeMapperAdapter<T>) other;
	return Objects.equal(bindingAttributeMapper, that.bindingAttributeMapper);
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(bindingAttributeMapper);
    }
}
