package org.robobinding.viewattribute.impl;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappingsProvider;

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
    private final PropertyAttributeParser propertyAttributeParser;

    public BindingAttributeMapperAdapter(BindingAttributeMapper<T> bindingAttributeMapper, 
	    PropertyAttributeParser propertyAttributeParser) {
	this.bindingAttributeMapper = bindingAttributeMapper;
	this.propertyAttributeParser = propertyAttributeParser;
    }

    public BindingAttributeMappingsImpl<T> createBindingAttributeMappings(ViewAttributeInitializer viewAttributeInitializer) {
	BindingAttributeMappingsImpl<T> bindingAttributeMappings = new BindingAttributeMappingsImpl<T>(
		viewAttributeInitializer, propertyAttributeParser);
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
