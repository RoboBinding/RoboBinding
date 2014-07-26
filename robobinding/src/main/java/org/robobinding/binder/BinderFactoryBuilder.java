package org.robobinding.binder;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;
import org.robobinding.widget.abslistview.AbsListViewAttributeMapper;
import org.robobinding.widget.absspinner.AbsSpinnerAttributeMapper;
import org.robobinding.widget.adapterview.AdapterViewAttributeMapper;
import org.robobinding.widget.adapterview.AdapterViewListeners;
import org.robobinding.widget.compoundbutton.CompoundButtonAttributeMapper;
import org.robobinding.widget.compoundbutton.CompoundButtonListeners;
import org.robobinding.widget.edittext.EditTextAttributeMapper;
import org.robobinding.widget.imageview.ImageViewAttributeMapper;
import org.robobinding.widget.listview.ListViewAttributeMapper;
import org.robobinding.widget.progressbar.ProgressBarAttributeMapper;
import org.robobinding.widget.ratingbar.RatingBarAttributeMapper;
import org.robobinding.widget.ratingbar.RatingBarListeners;
import org.robobinding.widget.seekbar.SeekBarAttributeMapper;
import org.robobinding.widget.seekbar.SeekBarListeners;
import org.robobinding.widget.textview.TextViewAttributeMapper;
import org.robobinding.widget.view.ViewAttributeMapper;
import org.robobinding.widget.view.ViewListeners;
import org.robobinding.widget.view.ViewListenersMapBuilder;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderFactoryBuilder {
    private final ViewListenersMapBuilder viewListenersMapBuilder;
    private final BindingAttributeMappingsProviderMapBuilder bindingAttributeMappingsProviderMapBuilder;

    public BinderFactoryBuilder() {
	this.viewListenersMapBuilder = defaultViewListenersMapBuilder();
	this.bindingAttributeMappingsProviderMapBuilder = defaultBindingAttributeMappingsProviderMapBuilder();
    }


    static ViewListenersMapBuilder defaultViewListenersMapBuilder() {
        ViewListenersMapBuilder viewListenersMap = new ViewListenersMapBuilder();
        viewListenersMap.put(View.class, ViewListeners.class);
        viewListenersMap.put(AdapterView.class, AdapterViewListeners.class);
        viewListenersMap.put(CompoundButton.class, CompoundButtonListeners.class);
        viewListenersMap.put(SeekBar.class, SeekBarListeners.class);
        viewListenersMap.put(RatingBar.class, RatingBarListeners.class);

        return viewListenersMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    static BindingAttributeMappingsProviderMapBuilder defaultBindingAttributeMappingsProviderMapBuilder() {

        BindingAttributeMappingsProviderMapBuilder bindingAttributeMappingsProviderMap = new BindingAttributeMappingsProviderMapBuilder();

        bindingAttributeMappingsProviderMap.put(View.class, new ViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(TextView.class, new TextViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(EditText.class, new EditTextAttributeMapper());
        bindingAttributeMappingsProviderMap.put(AdapterView.class, (BindingAttributeMapper) new AdapterViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(CompoundButton.class, new CompoundButtonAttributeMapper());
        bindingAttributeMappingsProviderMap.put(ImageView.class, new ImageViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(ProgressBar.class, new ProgressBarAttributeMapper());
        bindingAttributeMappingsProviderMap.put(SeekBar.class, new SeekBarAttributeMapper());
        bindingAttributeMappingsProviderMap.put(RatingBar.class, new RatingBarAttributeMapper());
        bindingAttributeMappingsProviderMap.put(ListView.class, new ListViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(AbsListView.class, new AbsListViewAttributeMapper());
        bindingAttributeMappingsProviderMap.put(AbsSpinner.class, new AbsSpinnerAttributeMapper());

        return bindingAttributeMappingsProviderMap;
    }

    public <T extends View> BinderFactoryBuilder mapView(Class<T> viewClass, BindingAttributeMapper<T> bindingAttributeMapper) {
	bindingAttributeMappingsProviderMapBuilder.put(viewClass, bindingAttributeMapper);
	return this;
    }

    public <T extends View> BinderFactoryBuilder mapView(Class<T> viewClass, BindingAttributeMapper<T> bindingAttributeMapper,
	    Class<? extends ViewListeners> viewListenersClass) {
	mapView(viewClass, bindingAttributeMapper);
	viewListenersMapBuilder.put(viewClass, viewListenersClass);
	return this;
    }

    public BinderFactory build() {
	return new BinderFactory(
		viewListenersMapBuilder.build(),
		bindingAttributeMappingsProviderMapBuilder.build());
    }

}
