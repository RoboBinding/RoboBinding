package org.robobinding.widgetaddon.abslistview;

import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOnFactory;

import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class AbsListViewAddOnFactory_Froyo implements ViewAddOnFactory {
	@Override
	public ViewAddOn create(Object view) {
		return new AbsListViewAddOn((AbsListView)view, new AbsListView_Froyo((ListView)view));
	}
}
