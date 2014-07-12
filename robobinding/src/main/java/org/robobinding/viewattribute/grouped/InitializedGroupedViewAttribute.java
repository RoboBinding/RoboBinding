package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface InitializedGroupedViewAttribute<T extends View> {
    void setupChildViewAttributes(T view, ChildViewAttributesBuilder<T> childViewAttributesBuilder, BindingContext bindingContext);
    void postBind(T view, BindingContext bindingContext);
}
