package org.robobinding.binder;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.absspinner.AbsSpinnerAttributeMapper;
import org.robobinding.viewattribute.adapterview.AdapterViewAttributeMapper;
import org.robobinding.viewattribute.adapterview.AdapterViewListeners;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonAttributeMapper;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonListeners;
import org.robobinding.viewattribute.edittext.EditTextAttributeMapper;
import org.robobinding.viewattribute.imageview.ImageViewAttributeMapper;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;
import org.robobinding.viewattribute.listview.ListViewAttributeMapper;
import org.robobinding.viewattribute.listview.ListViewListeners;
import org.robobinding.viewattribute.progressbar.ProgressBarAttributeMapper;
import org.robobinding.viewattribute.ratingbar.RatingBarAttributeMapper;
import org.robobinding.viewattribute.ratingbar.RatingBarListeners;
import org.robobinding.viewattribute.seekbar.SeekBarAttributeMapper;
import org.robobinding.viewattribute.seekbar.SeekBarListeners;
import org.robobinding.viewattribute.textview.TextViewAttributeMapper;
import org.robobinding.viewattribute.view.ViewAttributeMapper;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersMapBuilder;

import android.view.View;
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
        viewListenersMap.put(ListView.class, ListViewListeners.class);
        viewListenersMap.put(CompoundButton.class, CompoundButtonListeners.class);
        viewListenersMap.put(SeekBar.class, SeekBarListeners.class);
        viewListenersMap.put(RatingBar.class, RatingBarListeners.class);

        return viewListenersMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    static BindingAttributeMappingsProviderMapBuilder defaultBindingAttributeMappingsProviderMapBuilder() {

	PropertyAttributeParser propertyAttributeParser = new PropertyAttributeParser();
        BindingAttributeMappingsProviderMapBuilder bindingAttributeMappingsProviderMap = new BindingAttributeMappingsProviderMapBuilder(
        	propertyAttributeParser);

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
