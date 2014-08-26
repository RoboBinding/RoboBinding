package org.robobinding;

import org.robobinding.property.ValueModel;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewBinder {
    private ViewBinder viewBinder;
    private BindingContext bindingContext;

    public SubViewBinder(ViewBinder viewBinder, BindingContext bindingContext) {
	this.viewBinder = viewBinder;
	this.bindingContext = bindingContext;
    }

    public View inflateAndBind(int layoutId, String presentationModelPropertyName) {
	Object presentationModel = getPresentationModel(presentationModelPropertyName);
	return viewBinder.inflateAndBind(layoutId, presentationModel);
    }

    private Object getPresentationModel(String presentationModelPropertyName) {
	ValueModel<Object> valueModel = bindingContext.getReadOnlyPropertyValueModel(presentationModelPropertyName);
	return valueModel.getValue();
    }

    public View inflate(int layoutId) {
	return viewBinder.inflate(layoutId);
    }

}