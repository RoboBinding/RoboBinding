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
public class OnChildClickListeners extends AbstractListeners<ExpandableListView.OnChildClickListener>
        implements ExpandableListView.OnChildClickListener {

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        boolean result = false;
        for (ExpandableListView.OnChildClickListener listener : listeners){
            boolean isHandled = listener.onChildClick(parent, v, groupPosition, childPosition, id);
            if(!result && isHandled){
                result = isHandled;
            }
        }

        return result;
    }
}
