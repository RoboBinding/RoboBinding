package org.robobinding.viewattribute;

import android.view.View;

import com.google.common.base.Objects;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractViewAttributeConfig<T extends View> {
    private final T view;

    public AbstractViewAttributeConfig(T view) {
	this.view = view;
    }

    public T getView() {
	return view;
    }

    @Override
    public boolean equals(Object other) {
	if (this == other)
	    return true;
	if (!(other instanceof AbstractViewAttributeConfig))
	    return false;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final AbstractViewAttributeConfig<T> that = (AbstractViewAttributeConfig) other;
	return Objects.equal(view, that.view);
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(view);
    }

}
