package org.robobinding.viewattribute;

import org.robobinding.attribute.CommandAttribute;

import android.view.View;

import com.google.common.base.Objects;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CommandViewAttributeConfig<T extends View> extends AbstractViewAttributeConfig<T> {
    private CommandAttribute attribute;

    public CommandViewAttributeConfig(T view, CommandAttribute attribute) {
	super(view);
	this.attribute = attribute;
    }

    public CommandAttribute getAttribute() {
	return attribute;
    }

    @Override
    public boolean equals(Object other) {
	if (this == other)
	    return true;
	if (!(other instanceof CommandViewAttributeConfig))
	    return false;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	final CommandViewAttributeConfig<T> that = (CommandViewAttributeConfig) other;
	return super.equals(that) && Objects.equal(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
	return super.hashCode() + Objects.hashCode(attribute);
    }
}
