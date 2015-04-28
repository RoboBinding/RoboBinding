package org.robobinding.widget.expandablelistview;

import android.view.ViewGroup;
import org.robobinding.BindableView;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;

import java.util.Collection;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class GroupLayoutBinder {
	private final ItemBinder itemBinder;
	private final int layoutId;
	private final Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup;

	public GroupLayoutBinder(ItemBinder itemBinder, int layoutId, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
		this.itemBinder = itemBinder;
		this.layoutId = layoutId;
		this.predefinedPendingAttributesForViewGroup = predefinedPendingAttributesForViewGroup;
	}

	public BindableView inflate(ViewGroup root) {
		return itemBinder.inflateWithoutAttachingToRoot(layoutId, 
				predefinedPendingAttributesForViewGroup, root);
	}
}
