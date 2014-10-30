package org.robobinding.widget.abslistview;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.widgetaddon.ViewAddOnAware;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOn;

import android.widget.AbsListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ChoiceModeAttribute implements OneWayPropertyViewAttribute<AbsListView, Integer>, ViewAddOnAware<AbsListViewAddOn> {
	private AbsListViewAddOn viewAddOn;
	
	@Override
	public void updateView(AbsListView view, Integer newChoiceMode) {
		viewAddOn.setChoiceMode(newChoiceMode);
	}
	
	@Override
	public void setViewAddOn(AbsListViewAddOn viewAddOn) {
		this.viewAddOn = viewAddOn;
	}

}
