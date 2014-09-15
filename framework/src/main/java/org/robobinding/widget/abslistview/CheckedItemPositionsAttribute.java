package org.robobinding.widget.abslistview;

import java.util.Map;
import java.util.Set;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.adapterview.AdapterViewListeners;
import org.robobinding.widget.listview.SparseBooleanArrayUtils;

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
public class CheckedItemPositionsAttribute implements MultiTypePropertyViewAttribute<AbsListView> {

	@Override
	public TwoWayPropertyViewAttribute<AbsListView, ?> create(AbsListView view,
			Class<?> propertyType) {
		if (SparseBooleanArray.class.isAssignableFrom(propertyType)) {
			return new SparseBooleanArrayCheckedItemPositionsAttribute();
		} else if (Set.class.isAssignableFrom(propertyType)) {
			return new SetCheckedItemPositionsAttribute();
		} else if (Map.class.isAssignableFrom(propertyType)) {
			return new MapCheckedItemPositionsAttribute();
		}

		throw new RuntimeException(
				"Could not find a suitable checkedItemPositions attribute class for property type: "
						+ propertyType);
	}

	abstract static class AbstractCheckedItemPositionsAttribute<PropertyType>
			implements TwoWayPropertyViewAttribute<AbsListView, PropertyType>,
			ViewListenersAware<AdapterViewListeners> {
		private AdapterViewListeners adapterViewListeners;

		@Override
		public void setViewListeners(AdapterViewListeners adapterViewListeners) {
			this.adapterViewListeners = adapterViewListeners;
		}

		@Override
		public void observeChangesOnTheView(final AbsListView view,
				final ValueModel<PropertyType> valueModel) {
			adapterViewListeners
					.addOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View itemView, int position, long id) {
							viewCheckedItemPositionsChanged(view, valueModel);
						}
					});
		}

		protected abstract void viewCheckedItemPositionsChanged(
				AbsListView view, ValueModel<PropertyType> valueModel);
		
		@Override
		public void updateView(AbsListView view, PropertyType newValue) {
			AbsListViewBackCompatible viewBackCompatible = new AbsListViewBackCompatible(
					view);
			viewBackCompatible.clearChoices();
			updateView(viewBackCompatible, newValue);
		}
		
		protected abstract void updateView(AbsListViewBackCompatible view, PropertyType newValue);
	}

	static class SparseBooleanArrayCheckedItemPositionsAttribute extends
			AbstractCheckedItemPositionsAttribute<SparseBooleanArray> {
		@Override
		protected void viewCheckedItemPositionsChanged(AbsListView view,
				ValueModel<SparseBooleanArray> valueModel) {
			SparseBooleanArray checkedItemPositions = new AbsListViewBackCompatible(
					view).getCheckedItemPositions();
			valueModel.setValue(checkedItemPositions);
		}

		@Override
		protected void updateView(AbsListViewBackCompatible viewBackCompatible,
				SparseBooleanArray newArray) {
			for (int i = 0; i < newArray.size(); i++) {
				viewBackCompatible.setItemChecked(newArray.keyAt(i),
						newArray.valueAt(i));
			}
		}
	}

	static class SetCheckedItemPositionsAttribute extends
			AbstractCheckedItemPositionsAttribute<Set<Integer>> {
		@Override
		protected void viewCheckedItemPositionsChanged(AbsListView view,
				ValueModel<Set<Integer>> valueModel) {
			SparseBooleanArray checkedItemPositions = new AbsListViewBackCompatible(
					view).getCheckedItemPositions();
			valueModel.setValue(SparseBooleanArrayUtils
					.toSet(checkedItemPositions));
		}

		@Override
		public void updateView(AbsListViewBackCompatible viewBackCompatible, Set<Integer> newValue) {
			for (int position : newValue) {
				viewBackCompatible.setItemChecked(position, true);
			}
		}
	}

	static class MapCheckedItemPositionsAttribute extends
			AbstractCheckedItemPositionsAttribute<Map<Integer, Boolean>> {
		@Override
		protected void viewCheckedItemPositionsChanged(AbsListView view,
				ValueModel<Map<Integer, Boolean>> valueModel) {
			SparseBooleanArray checkedItemPositions = new AbsListViewBackCompatible(
					view).getCheckedItemPositions();
			valueModel.setValue(SparseBooleanArrayUtils
					.toMap(checkedItemPositions));
		}

		@Override
		public void updateView(AbsListViewBackCompatible viewBackCompatible, Map<Integer, Boolean> newValue) {
			for (Integer position : newValue.keySet()) {
				Boolean checked = newValue.get(position);
				viewBackCompatible.setItemChecked(position, checked);
			}
		}
	}
}
