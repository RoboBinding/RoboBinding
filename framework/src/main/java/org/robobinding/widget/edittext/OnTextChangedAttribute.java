package org.robobinding.widget.edittext;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnTextChangedAttribute implements EventViewAttribute<EditText> {
	@Override
	public void bind(final EditText view, final Command command) {
		view.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				TextChangedEvent event = new TextChangedEvent(view, start, before, count);
				command.invoke(event);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	@Override
	public Class<TextChangedEvent> getEventType() {
		return TextChangedEvent.class;
	}

}
