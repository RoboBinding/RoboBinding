package org.robobinding.widgetaddon.menuitem;

import org.robobinding.util.BooleanDecision;
import org.robobinding.widgetaddon.AbstractListeners;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class OnMenuItemClickListeners extends AbstractListeners<OnMenuItemClickListener> implements OnMenuItemClickListener {

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		BooleanDecision isConsumed = new BooleanDecision();
		for (OnMenuItemClickListener listener : listeners) {
			isConsumed.or(listener.onMenuItemClick(item));
		}
		return isConsumed.getResult();
	}
}
