package org.robobinding.widget.menuitemgroup;

import android.view.Menu;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuItemGroup {
	private Menu menu;
	private int groupId;

	public MenuItemGroup(Menu menu, int groupId) {
		this.menu = menu;
		this.groupId = groupId;
	}

	public void setEnabled(boolean enabled) {
		menu.setGroupEnabled(groupId, enabled);
	}

	public void setVisible(boolean visible) {
		menu.setGroupVisible(groupId, visible);
	}

}
