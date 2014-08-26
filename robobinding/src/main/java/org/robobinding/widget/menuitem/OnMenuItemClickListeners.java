package org.robobinding.widget.menuitem;

import org.robobinding.util.BooleanDecision;
import org.robobinding.viewattribute.AbstractListeners;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnMenuItemClickListeners extends AbstractListeners<OnMenuItemClickListener> implements OnMenuItemClickListener {

    @Override
    public boolean onMenuItemClick(MenuItem item) {
	BooleanDecision isConsumed = new BooleanDecision();
	for (OnMenuItemClickListener listener : listeners) {
	    isConsumed.or(listener.onMenuItemClick(item));
	}
	return isConsumed.getResult();
    }
}
