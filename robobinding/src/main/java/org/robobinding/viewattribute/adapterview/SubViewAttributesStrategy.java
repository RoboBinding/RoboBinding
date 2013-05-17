package org.robobinding.viewattribute.adapterview;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

public interface SubViewAttributesStrategy<T extends AdapterView<?>> {
    String layoutAttribute();

    String subViewPresentationModelAttribute();

    String visibilityAttribute();

    void addSubView(T adapterView, View subView, Context context);

    AbstractSubViewVisibility createVisibility(T adapterView, View subView);
}
