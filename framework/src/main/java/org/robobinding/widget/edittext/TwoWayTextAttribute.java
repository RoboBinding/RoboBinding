package org.robobinding.widget.edittext;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widgetaddon.ViewAddOn;

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
 * @author Cheng Wei
 */
public class TwoWayTextAttribute implements TwoWayMultiTypePropertyViewAttribute<EditText> {
	ValueCommitMode valueCommitMode = ValueCommitMode.ON_CHANGE;

	@Override
	public TwoWayPropertyViewAttribute<EditText, ?, ?> create(EditText view, Class<?> propertyType) {
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

	private abstract static class AbstractTwoWayCharSequenceTextAttribute<PropertyType extends CharSequence> implements
			TwoWayPropertyViewAttribute<EditText, ViewAddOn, PropertyType> {
		private ValueCommitMode valueCommitMode;

		@Override
		public void updateView(EditText view, PropertyType newValue, ViewAddOn viewAddOn) {
			view.setText(newValue);
		}

		@Override
		public void observeChangesOnTheView(ViewAddOn viewAddOn, final ValueModel<PropertyType> valueModel, final EditText view) {
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
