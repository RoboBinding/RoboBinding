package org.robobinding.binder;

import org.robobinding.customviewbinding.CustomViewBinding;
import org.robobinding.customviewbinding.CustomViewBindingApplier;
import org.robobinding.customviewbinding.CustomViewBindingDescription;
import org.robobinding.viewbinding.ViewBinding;
import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.widget.abslistview.AbsListViewBinding;
import org.robobinding.widget.absspinner.AbsSpinnerBinding;
import org.robobinding.widget.adapterview.AdapterViewBinding;
import org.robobinding.widget.compoundbutton.CompoundButtonBinding;
import org.robobinding.widget.edittext.EditTextBinding;
import org.robobinding.widget.listview.ListViewBinding;
import org.robobinding.widget.menuitem.MenuItemBinding;
import org.robobinding.widget.radiogroup.RadioGroupBinding;
import org.robobinding.widget.ratingbar.RatingBarBinding;
import org.robobinding.widget.seekbar.SeekBarBinding;
import org.robobinding.widget.textview.TextViewBinding;
import org.robobinding.widget.timepicker.TimePickerBinding;
import org.robobinding.widget.view.ViewBindingForView;
import org.robobinding.widget.viewanimator.ViewAnimatorBinding;
import org.robobinding.widgetaddon.DefaultViewAddOnFactory;
import org.robobinding.widgetaddon.ViewAddOnsBuilder;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOnFactory_Froyo;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOnFactory_HoneyComb;
import org.robobinding.widgetaddon.adapterview.AdapterViewAddOn;
import org.robobinding.widgetaddon.compoundbutton.CompoundButtonAddOn;
import org.robobinding.widgetaddon.menuitem.MenuItemAddOn;
import org.robobinding.widgetaddon.radiogroup.RadioGroupAddOn;
import org.robobinding.widgetaddon.ratingbar.RatingBarAddOn;
import org.robobinding.widgetaddon.seekbar.SeekBarAddOn;
import org.robobinding.widgetaddon.timepicker.TimePickerAddOn;
import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewAnimator;

/**
 * The builder class allows customizing {@link BinderFactory} by adding new {@link CustomViewBinding}s to
 * override or extend existing {@link ViewBinding}s.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderFactoryBuilder {
	private final ViewAddOnsBuilder viewAddOnsBuilder;
	private final ViewBindingMap viewBindingMap;

	public BinderFactoryBuilder() {
		this.viewAddOnsBuilder = defaultViewAddOnsBuilder();
		this.viewBindingMap = defaultViewBindingMap();
	}

	static ViewAddOnsBuilder defaultViewAddOnsBuilder() {
		ViewAddOnsBuilder builder = new ViewAddOnsBuilder();
		builder.put(View.class, new DefaultViewAddOnFactory(ViewAddOnForView.class));
		builder.put(AdapterView.class, new DefaultViewAddOnFactory(AdapterViewAddOn.class));
		builder.put(CompoundButton.class, new DefaultViewAddOnFactory(CompoundButtonAddOn.class));
		builder.put(SeekBar.class, new DefaultViewAddOnFactory(SeekBarAddOn.class));
		builder.put(RatingBar.class, new DefaultViewAddOnFactory(RatingBarAddOn.class));
		builder.put(RadioGroup.class, new DefaultViewAddOnFactory(RadioGroupAddOn.class));
	    builder.put(TimePicker.class, new DefaultViewAddOnFactory(TimePickerAddOn.class));
		builder.put(MenuItem.class, new DefaultViewAddOnFactory(MenuItemAddOn.class));
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			builder.put(AbsListView.class, new AbsListViewAddOnFactory_HoneyComb());
		} else {
			builder.put(AbsListView.class, new AbsListViewAddOnFactory_Froyo());
		}
 
		return builder;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static ViewBindingMap defaultViewBindingMap() {

		ViewBindingMap builder = new ViewBindingMap();

		builder.put(View.class, new ViewBindingForView());
		builder.put(TextView.class, new TextViewBinding());
		builder.put(EditText.class, new EditTextBinding());
		builder.put(AdapterView.class, (ViewBinding) new AdapterViewBinding());
		builder.put(CompoundButton.class, new CompoundButtonBinding());
		builder.put(SeekBar.class, new SeekBarBinding());
		builder.put(RatingBar.class, new RatingBarBinding());
		builder.put(ListView.class, new ListViewBinding());
		builder.put(AbsListView.class, new AbsListViewBinding());
		builder.put(AbsSpinner.class, new AbsSpinnerBinding());
		builder.put(ViewAnimator.class, new ViewAnimatorBinding());
		builder.put(RadioGroup.class, new RadioGroupBinding());
		builder.put(MenuItem.class, new MenuItemBinding());
        builder.put(TimePicker.class, new TimePickerBinding());

		return builder;
	}

	public BinderFactoryBuilder add(CustomViewBindingDescription description) {
		CustomViewBindingApplier viewBindingApplier = description.build();
		viewBindingApplier.applyBindingAttributeMapper(viewBindingMap);
		viewBindingApplier.applyViewAddOnIfExists(viewAddOnsBuilder);
		return this;
	}

	public BinderFactory build() {
		return new BinderFactory(viewBindingMap, viewAddOnsBuilder.build());
	}

}
