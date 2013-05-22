/**
 * FooterSourceViewAttribute.java
 * Sep 6, 2011
 * Copyright 2009~2011 ESCA Mobile Ltd company, Inc. All rights reserved.
 * ESCA Mobile Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.adapterview.SubViewAttributesStrategy;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class FooterAttributes implements SubViewAttributesStrategy<ListView> {
    static final String FOOTER_LAYOUT = "footerLayout";
    static final String FOOTER_PRESENTATION_MODEL = "footerPresentationModel";
    static final String FOOTER_VISIBILITY = "footerVisibility";

    @Override
    public String layoutAttribute() {
	return FOOTER_LAYOUT;
    }

    @Override
    public String subViewPresentationModelAttribute() {
	return FOOTER_PRESENTATION_MODEL;
    }

    @Override
    public String visibilityAttribute() {
	return FOOTER_VISIBILITY;
    }

    @Override
    public void addSubView(ListView listView, View subView, Context context) {
	listView.addFooterView(subView);
    }

    @Override
    public FooterVisibility createVisibility(ListView listView, View subView) {
	return new FooterVisibility(listView, subView);
    }
}
