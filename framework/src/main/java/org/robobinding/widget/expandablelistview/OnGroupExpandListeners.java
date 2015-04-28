package org.robobinding.widget.expandablelistview;

import android.widget.ExpandableListView;
import org.robobinding.viewattribute.AbstractListeners;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class OnGroupExpandListeners extends AbstractListeners<ExpandableListView.OnGroupExpandListener>
        implements ExpandableListView.OnGroupExpandListener {
    @Override
    public void onGroupExpand(int groupPosition) {
        for(ExpandableListView.OnGroupExpandListener listener : listeners){
            listener.onGroupExpand(groupPosition);
        }
    }
}
