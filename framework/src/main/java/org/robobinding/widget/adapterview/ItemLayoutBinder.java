package org.robobinding.widget.adapterview;

import java.util.Collection;

import org.robobinding.BindableView;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;

import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemLayoutBinder {
	private final ItemBinder itemBinder;
	private final int layoutId;
	private final Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup;

	public ItemLayoutBinder(ItemBinder itemBinder, int layoutId, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
		this.itemBinder = itemBinder;
		this.layoutId = layoutId;
		this.predefinedPendingAttributesForViewGroup = predefinedPendingAttributesForViewGroup;
	}

	public BindableView inflate(ViewGroup root) {
		return inflate(root, this.layoutId);
	}

	public BindableView inflate(ViewGroup root, int layoutId) {
		if (layoutId == 0) {
			layoutId = this.layoutId;
		}
		return itemBinder.inflateWithoutAttachingToRoot(layoutId,
				predefinedPendingAttributesForViewGroup, root);
	}
}
