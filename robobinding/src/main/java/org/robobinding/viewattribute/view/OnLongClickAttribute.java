package org.robobinding.viewattribute.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;

import android.view.View;
import android.view.View.OnLongClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnLongClickAttribute extends AbstractCommandViewAttribute<View> {
    @Override
    protected void bind(final Command command) {
	view.setOnLongClickListener(new OnLongClickListener() {
	    @Override
	    public boolean onLongClick(View v) {
		ClickEvent clickEvent = new ClickEvent(v);
		command.invoke(clickEvent);
		return true;
	    }
	});
    }

    @Override
    protected Class<?> getPreferredCommandParameterType() {
	return ClickEvent.class;
    }

}
