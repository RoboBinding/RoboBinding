package org.robobinding.widgetaddon;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ViewAddOnAware<T extends ViewAddOn> {
	void setViewAddOn(T viewAddOn);
}
