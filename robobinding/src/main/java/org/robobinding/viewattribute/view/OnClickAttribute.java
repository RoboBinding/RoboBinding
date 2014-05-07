package org.robobinding.viewattribute.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnClickAttribute extends AbstractCommandViewAttribute<View> {
    @Override
    protected void bind(final Command command) {
	view.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		ClickEvent clickEvent = new ClickEvent(v);
		command.invoke(clickEvent);
	    }
	});
    }

    @Override
    protected Class<?> getPreferredCommandParameterType() {
	return ClickEvent.class;
    }
}
