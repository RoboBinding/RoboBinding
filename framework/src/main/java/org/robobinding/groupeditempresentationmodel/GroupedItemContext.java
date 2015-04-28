package org.robobinding.groupeditempresentationmodel;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class GroupedItemContext {
	private final View itemView;
	private final int groupPosition;
    private final int childPosition;
	
	public GroupedItemContext(View itemView, int groupPosition, int childPosition) {
		this.itemView = itemView;
		this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }

	/**
	 * @return The item view. The use of the view should be limited to minimal as it may break the pure POJO PresentationModel rule.
	 */
	public View getItemView() {
		return itemView;
	}

	public int getGroupPosition() {
		return groupPosition;
	}

    public int getChildPosition() {
        return childPosition;
    }
}
