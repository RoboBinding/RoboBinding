package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.view.AbstractVisibility;

import android.view.View;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */

public class FooterVisibility extends AbstractVisibility {
    private ListView listView;
    private View footerView;

    public FooterVisibility(ListView listView, View footerView) {
	this.listView = listView;
	this.footerView = footerView;
    }

    @Override
    public void makeGone() {
	if (listView.getAdapter() == null) {
	    return;
	}

	if (listView.getFooterViewsCount() > 0 && footerView != null) {
	    listView.removeFooterView(footerView);
	}
    }

    @Override
    public void makeVisible() {
	addFooterViewIfNotExist();
	if(footerView != null){
		footerView.setVisibility(View.VISIBLE);
	}
    }

    @Override
    protected void makeInvisible() {
	addFooterViewIfNotExist();
	if(footerView != null){
		footerView.setVisibility(View.INVISIBLE);
	}
    }

    private void addFooterViewIfNotExist() {
	if (listView.getFooterViewsCount() == 0 && footerView != null) {
	    listView.addFooterView(footerView);
	}
    }

	public void setSubview(View subView) {
		this.footerView = subView;		
	}
}
