package org.robobinding.binder;

import org.robobinding.dynamicbinding.DynamicViewBinding;
import org.robobinding.dynamicbinding.DynamicViewBindingDescription;
import org.robobinding.dynamicbinding.ViewBindingApplier;
import org.robobinding.viewattribute.ViewBinding;
import org.robobinding.viewattribute.ViewListeners;
import org.robobinding.viewattribute.ViewListenersMapBuilder;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;
import org.robobinding.widget.abslistview.AbsListViewBinding;
import org.robobinding.widget.absspinner.AbsSpinnerBinding;
import org.robobinding.widget.adapterview.AdapterViewBinding;
import org.robobinding.widget.adapterview.AdapterViewListeners;
import org.robobinding.widget.compoundbutton.CompoundButtonBinding;
import org.robobinding.widget.compoundbutton.CompoundButtonListeners;
import org.robobinding.widget.edittext.EditTextBinding;
import org.robobinding.widget.imageview.ImageViewBinding;
import org.robobinding.widget.listview.ListViewBinding;
import org.robobinding.widget.listview.ListViewListeners;
import org.robobinding.widget.menuitem.MenuItemBinding;
import org.robobinding.widget.menuitem.MenuItemListeners;
import org.robobinding.widget.menuitemgroup.MenuItemGroup;
import org.robobinding.widget.menuitemgroup.MenuItemGroupBinding;
import org.robobinding.widget.progressbar.ProgressBarBinding;
import org.robobinding.widget.radiogroup.RadioGroupBinding;
import org.robobinding.widget.radiogroup.RadioGroupListeners;
import org.robobinding.widget.ratingbar.RatingBarBinding;
import org.robobinding.widget.ratingbar.RatingBarListeners;
import org.robobinding.widget.seekbar.SeekBarBinding;
import org.robobinding.widget.seekbar.SeekBarListeners;
import org.robobinding.widget.textview.TextViewBinding;
import org.robobinding.widget.view.ViewBindingForView;
import org.robobinding.widget.view.ViewListenersForView;
import org.robobinding.widget.viewanimator.ViewAnimatorBinding;

import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

/**
 * The builder class allows customizing {@link BinderFactory} by adding new,
 * overriding or extending existing {@link ViewBinding}s and
 * {@link DynamicViewBinding}s.
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
		ViewListenersMapBuilder builder = new ViewListenersMapBuilder();
		builder.put(View.class, ViewListenersForView.class);
		builder.put(AdapterView.class, AdapterViewListeners.class);
		builder.put(ListView.class, ListViewListeners.class);
		builder.put(CompoundButton.class, CompoundButtonListeners.class);
		builder.put(SeekBar.class, SeekBarListeners.class);
		builder.put(RatingBar.class, RatingBarListeners.class);
		builder.put(RadioGroup.class, RadioGroupListeners.class);
		builder.put(MenuItem.class, MenuItemListeners.class);

		return builder;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static BindingAttributeMappingsProviderMapBuilder defaultBindingAttributeMappingsProviderMapBuilder() {

		BindingAttributeMappingsProviderMapBuilder builder = new BindingAttributeMappingsProviderMapBuilder();

		builder.put(View.class, new ViewBindingForView());
		builder.put(TextView.class, new TextViewBinding());
		builder.put(EditText.class, new EditTextBinding());
		builder.put(AdapterView.class, (ViewBinding) new AdapterViewBinding());
		builder.put(CompoundButton.class, new CompoundButtonBinding());
		builder.put(ImageView.class, new ImageViewBinding());
		builder.put(ProgressBar.class, new ProgressBarBinding());
		builder.put(SeekBar.class, new SeekBarBinding());
		builder.put(RatingBar.class, new RatingBarBinding());
		builder.put(ListView.class, new ListViewBinding());
		builder.put(AbsListView.class, new AbsListViewBinding());
		builder.put(AbsSpinner.class, new AbsSpinnerBinding());
		builder.put(ViewAnimator.class, new ViewAnimatorBinding());
		builder.put(RadioGroup.class, new RadioGroupBinding());
		builder.put(MenuItem.class, new MenuItemBinding());
		builder.put(MenuItemGroup.class, new MenuItemGroupBinding());

		return builder;
	}

	public <T extends View> BinderFactoryBuilder mapView(Class<T> viewClass, ViewBinding<T> bindingAttributeMapper) {
		bindingAttributeMappingsProviderMapBuilder.put(viewClass, bindingAttributeMapper);
		return this;
	}

	public <T extends View> BinderFactoryBuilder mapView(Class<T> viewClass, ViewBinding<T> bindingAttributeMapper,
			Class<? extends ViewListeners> viewListenersClass) {
		mapView(viewClass, bindingAttributeMapper);
		viewListenersMapBuilder.put(viewClass, viewListenersClass);
		return this;
	}

	public <T extends View> BinderFactoryBuilder add(DynamicViewBindingDescription<T> viewBindingDescription) {
		ViewBindingApplier<T> viewBindingApplier = viewBindingDescription.build();
		viewBindingApplier.applyBindingAttributeMapper(bindingAttributeMappingsProviderMapBuilder);
		viewBindingApplier.applyViewListenersIfExists(viewListenersMapBuilder);
		return this;
	}

	public BinderFactory build() {
		return new BinderFactory(viewListenersMapBuilder.build(), bindingAttributeMappingsProviderMapBuilder.build());
	}

}
