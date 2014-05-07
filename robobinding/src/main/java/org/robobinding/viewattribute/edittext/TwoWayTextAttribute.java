package org.robobinding.viewattribute.edittext;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayTextAttribute extends AbstractMultiTypePropertyViewAttribute<EditText> {
    ValueCommitMode valueCommitMode = ValueCommitMode.ON_CHANGE;

    @Override
    protected AbstractPropertyViewAttribute<EditText, ?> createPropertyViewAttribute(Class<?> propertyType) {
	if (String.class.isAssignableFrom(propertyType)) {
	    return createNewStringAttribute();
	} else if (CharSequence.class.isAssignableFrom(propertyType)) {
	    return createNewCharSequenceAttribute();
	}

	return null;
    }

    private TwoWayStringTextAttribute createNewStringAttribute() {
	TwoWayStringTextAttribute stringTextAttribute = new TwoWayStringTextAttribute();
	stringTextAttribute.setValueCommitMode(valueCommitMode);
	return stringTextAttribute;
    }

    private TwoWayCharSequenceTextAttribute createNewCharSequenceAttribute() {
	TwoWayCharSequenceTextAttribute charSequenceTextAttribute = new TwoWayCharSequenceTextAttribute();
	charSequenceTextAttribute.setValueCommitMode(valueCommitMode);
	return charSequenceTextAttribute;
    }

    void setValueCommitMode(ValueCommitMode valueCommitMode) {
	this.valueCommitMode = valueCommitMode;
    }

    private abstract static class AbstractTwoWayCharSequenceTextAttribute<PropertyType extends CharSequence> extends
	    AbstractPropertyViewAttribute<EditText, PropertyType> {
	private ValueCommitMode valueCommitMode;

	@Override
	protected void valueModelUpdated(PropertyType newValue) {
	    view.setText(newValue);
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<PropertyType> valueModel) {
	    if (valueCommitMode == ValueCommitMode.ON_FOCUS_LOST) {
		view.setOnFocusChangeListener(new OnFocusChangeListener() {

		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus)
			    updateValueModel(valueModel, view.getText());
		    }
		});
	    } else {
		view.addTextChangedListener(new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
			updateValueModel(valueModel, s);
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    }
		});
	    }
	}

	void setValueCommitMode(ValueCommitMode valueCommitMode) {
	    this.valueCommitMode = valueCommitMode;
	}

	protected abstract void updateValueModel(ValueModel<PropertyType> valueModel, CharSequence charSequence);
    }

    static class TwoWayStringTextAttribute extends AbstractTwoWayCharSequenceTextAttribute<String> {
	@Override
	protected void updateValueModel(ValueModel<String> valueModel, CharSequence charSequence) {
	    valueModel.setValue(charSequence.toString());
	}
    }

    static class TwoWayCharSequenceTextAttribute extends AbstractTwoWayCharSequenceTextAttribute<CharSequence> {
	@Override
	protected void updateValueModel(ValueModel<CharSequence> valueModel, CharSequence charSequence) {
	    valueModel.setValue(charSequence);
	}
    }
}
