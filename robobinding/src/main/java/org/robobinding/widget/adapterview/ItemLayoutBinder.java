package org.robobinding.widget.adapterview;

import java.util.Collection;

import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

import android.view.View;

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
    
    public ItemLayoutBinder(ItemBinder itemBinder, int layoutId,
	    Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	this.itemBinder = itemBinder;
	this.layoutId = layoutId;
	this.predefinedPendingAttributesForViewGroup = predefinedPendingAttributesForViewGroup;
    }
    
    public View inflateAndBindTo(ItemPresentationModel<?> model) {
	return itemBinder.inflateAndBind(layoutId, model, predefinedPendingAttributesForViewGroup);
    }
}
