package org.robobinding.widget.expandablelistview;

import android.widget.ExpandableListView;
import org.robobinding.viewattribute.AbstractListeners;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class OnGroupCollapseListeners extends AbstractListeners<ExpandableListView.OnGroupCollapseListener>
        implements ExpandableListView.OnGroupCollapseListener {
    @Override
    public void onGroupCollapse(int groupPosition) {
        for(ExpandableListView.OnGroupCollapseListener listener : listeners) {
            listener.onGroupCollapse(groupPosition);
        }
    }
}
