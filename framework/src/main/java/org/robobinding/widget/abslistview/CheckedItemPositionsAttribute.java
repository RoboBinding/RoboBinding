package org.robobinding.widget.abslistview;

import java.util.Map;
import java.util.Set;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOn;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionsAttribute implements TwoWayMultiTypePropertyViewAttribute<AbsListView> {

	@Override
	public TwoWayPropertyViewAttribute<AbsListView, ?, ?> create(AbsListView view, Class<?> propertyType) {
		if (SparseBooleanArray.class.isAssignableFrom(propertyType)) {
			return new SparseBooleanArrayCheckedItemPositionsAttribute();
		} else if (Set.class.isAssignableFrom(propertyType)) {
			return new SetCheckedItemPositionsAttribute();
		} else if (Map.class.isAssignableFrom(propertyType)) {
			return new MapCheckedItemPositionsAttribute();
		}

		throw new RuntimeException("Could not find a suitable checkedItemPositions attribute class for property type: " + propertyType);
	}

	abstract static class AbstractCheckedItemPositionsAttribute<PropertyType> 
		implements TwoWayPropertyViewAttribute<AbsListView, AbsListViewAddOn, PropertyType> {
		@Override
		public void observeChangesOnTheView(final AbsListViewAddOn viewAddOn, final ValueModel<PropertyType> valueModel, AbsListView view) {
			viewAddOn.addOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
					viewCheckedItemPositionsChanged(viewAddOn, valueModel);
				}
			});
		}

		protected abstract void viewCheckedItemPositionsChanged(AbsListViewAddOn viewAddOn, ValueModel<PropertyType> valueModel);

		@Override
		public void updateView(AbsListView view, PropertyType newValue, AbsListViewAddOn viewAddOn) {
			viewAddOn.clearChoices();
			updateView(viewAddOn, newValue);
		}

		protected abstract void updateView(AbsListViewAddOn viewAddOn, PropertyType newValue);
	}

	static class SparseBooleanArrayCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<SparseBooleanArray> {
		@Override
		protected void viewCheckedItemPositionsChanged(AbsListViewAddOn viewAddOn, ValueModel<SparseBooleanArray> valueModel) {
			SparseBooleanArray checkedItemPositions = viewAddOn.getCheckedItemPositions();
			valueModel.setValue(checkedItemPositions);
		}

		@Override
		protected void updateView(AbsListViewAddOn viewAddOn, SparseBooleanArray newArray) {
			for (int i = 0; i < newArray.size(); i++) {
				viewAddOn.setItemChecked(newArray.keyAt(i), newArray.valueAt(i));
			}
		}
	}

	static class SetCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<Set<Integer>> {
		@Override
		protected void viewCheckedItemPositionsChanged(AbsListViewAddOn viewAddOn, ValueModel<Set<Integer>> valueModel) {
			SparseBooleanArray checkedItemPositions = viewAddOn.getCheckedItemPositions();
			valueModel.setValue(SparseBooleanArrayUtils.toSet(checkedItemPositions));
		}

		@Override
		public void updateView(AbsListViewAddOn viewAddOn, Set<Integer> newValue) {
			for (int position : newValue) {
				viewAddOn.setItemChecked(position, true);
			}
		}
	}

	static class MapCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<Map<Integer, Boolean>> {
		@Override
		protected void viewCheckedItemPositionsChanged(AbsListViewAddOn viewAddOn, ValueModel<Map<Integer, Boolean>> valueModel) {
			SparseBooleanArray checkedItemPositions = viewAddOn.getCheckedItemPositions();
			valueModel.setValue(SparseBooleanArrayUtils.toMap(checkedItemPositions));
		}

		@Override
		public void updateView(AbsListViewAddOn viewAddOn, Map<Integer, Boolean> newValue) {
			for (Integer position : newValue.keySet()) {
				Boolean checked = newValue.get(position);
				viewAddOn.setItemChecked(position, checked);
			}
		}
	}
}
