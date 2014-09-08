package org.robobinding.widget.menuitem;

import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

import android.view.MenuItem;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuItemBinding implements ViewBinding<MenuItem> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<MenuItem> mappings) {
	mappings.mapProperty(EnabledAttribute.class, "enabled");
	mappings.mapProperty(VisibleAttribute.class, "visible");
	mappings.mapMultiTypeProperty(TitleAttribute.class, "title");
	
	mappings.mapEvent(OnMenuItemClickAttribute.class, "onMenuItemClick");
        
    }
}
