package org.robobinding.widget.expandablelistview;

import android.view.View;
import android.widget.ExpandableListView;
import org.robobinding.viewattribute.AbstractListeners;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class OnGroupClickListeners extends AbstractListeners<ExpandableListView.OnGroupClickListener>
        implements ExpandableListView.OnGroupClickListener {

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        boolean result = false;
        for (ExpandableListView.OnGroupClickListener listener : listeners){
            boolean isHandled = listener.onGroupClick(parent, v, groupPosition, id);
            if(!result && isHandled){
                result = isHandled;
            }
        }

        return result;
    }
}
