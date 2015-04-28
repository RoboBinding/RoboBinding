package org.robobinding.widget.expandablelistview;

import android.widget.ExpandableListView;
import org.robobinding.widget.listview.ListViewListeners;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class ExpandableListViewListeners extends ListViewListeners {
	private final ExpandableListView expandableListView;
    private OnGroupClickListeners onGroupClickListeners;
    private OnChildClickListeners onChildClickListeners;
    private OnGroupCollapseListeners onGroupCollapseListeners;
    private OnGroupExpandListeners onGroupExpandListeners;

	public ExpandableListViewListeners(ExpandableListView expandableListView) {
		super(expandableListView);
		this.expandableListView = expandableListView;
	}

    public void addOnGroupClickListener(ExpandableListView.OnGroupClickListener onGroupClickListener){
        ensureOnGroupClickListenerInitialized();
        onGroupClickListeners.addListener(onGroupClickListener);
    }

    public void addOnChildClickListener(ExpandableListView.OnChildClickListener onChildClickListener){
        ensureOnChildClickListenerInitialized();
        onChildClickListeners.addListener(onChildClickListener);
    }

    public void addOnGroupCollapseListener(ExpandableListView.OnGroupCollapseListener onGroupCollapseListener) {

    }

    public void addOnGroupExpandListener(ExpandableListView.OnGroupExpandListener onGroupExpandListener){

    }

    private void ensureOnGroupClickListenerInitialized() {
        if(onGroupClickListeners == null) {
            onGroupClickListeners = new OnGroupClickListeners();
            expandableListView.setOnGroupClickListener(onGroupClickListeners);
        }
    }

    private void ensureOnChildClickListenerInitialized() {
        if(onChildClickListeners == null) {
            onChildClickListeners = new OnChildClickListeners();
            expandableListView.setOnChildClickListener(onChildClickListeners);
        }
    }

    private void ensureOnGroupCollapseListenerInitialized() {
        if(onGroupCollapseListeners == null) {
            onGroupCollapseListeners = new OnGroupCollapseListeners();
            expandableListView.setOnGroupCollapseListener(onGroupCollapseListeners);
        }
    }

    private void ensureOnGroupExpandListenerInitialized() {
        if(onGroupExpandListeners == null) {
            onGroupCollapseListeners = new OnGroupCollapseListeners();
            expandableListView.setOnGroupExpandListener(onGroupExpandListeners);
        }
    }



}
