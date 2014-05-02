package org.robobinding.viewattribute.edittext;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnTextChangedAttribute extends AbstractCommandViewAttribute<EditText> {
    @Override
    protected void bind(final Command command) {
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
    protected Class<?> getPreferredCommandParameterType() {
	return TextChangedEvent.class;
    }

}
