package org.robobinding.supportwidget.swiperefreshlayout;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * 
 * @since
 * @version
 * @author Liang Song
 */
public class SwipeRefreshLayoutBinding implements
		ViewBinding<SwipeRefreshLayout> {

	@Override
	public void mapBindingAttributes(
			BindingAttributeMappings<SwipeRefreshLayout> mappings) {
		mappings.mapEvent(OnRefreshAttribute.class, "onRefresh");
	}
}
