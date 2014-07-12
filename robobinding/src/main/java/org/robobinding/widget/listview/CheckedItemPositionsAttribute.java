package org.robobinding.widget.listview;

import java.util.Map;
import java.util.Set;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.adapterview.AdapterViewListeners;
import org.robobinding.widget.view.ViewListenersAware;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionsAttribute implements MultiTypePropertyViewAttribute<ListView> {

    @Override
    public TwoWayPropertyViewAttribute<ListView, ?> create(ListView view, Class<?> propertyType) {
        if (SparseBooleanArray.class.isAssignableFrom(propertyType)) {
	    return new SparseBooleanArrayCheckedItemPositionsAttribute();
	} else if (Set.class.isAssignableFrom(propertyType)) {
	    return new SetCheckedItemPositionsAttribute();
	} else if (Map.class.isAssignableFrom(propertyType)) {
	    return new MapCheckedItemPositionsAttribute();
	}

	throw new RuntimeException("Could not find a suitable checkedItemPositions attribute class for property type: " + propertyType);
    }

    abstract static class AbstractCheckedItemPositionsAttribute<PropertyType> implements TwoWayPropertyViewAttribute<ListView, PropertyType>,
	    ViewListenersAware<AdapterViewListeners> {
	private AdapterViewListeners adapterViewListeners;

	@Override
	public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	    this.adapterViewListeners = adapterViewListeners;
	}

	@Override
	public void observeChangesOnTheView(final ListView view, final ValueModel<PropertyType> valueModel) {
	    adapterViewListeners.addOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
		    viewCheckedItemPositionsChanged(view, valueModel);
		}
	    });
	}

	protected abstract void viewCheckedItemPositionsChanged(ListView view, ValueModel<PropertyType> valueModel);
    }

    static class SparseBooleanArrayCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<SparseBooleanArray> {
	@Override
	protected void viewCheckedItemPositionsChanged(ListView view, ValueModel<SparseBooleanArray> valueModel) {
	    SparseBooleanArray checkedItemPositions = view.getCheckedItemPositions();
	    valueModel.setValue(checkedItemPositions);
	}

	@Override
	public void updateView(ListView view, SparseBooleanArray newArray) {
	    ListViewUtils.clearSelections(view);
	    for (int i = 0; i < newArray.size(); i++) {
		view.setItemChecked(newArray.keyAt(i), newArray.valueAt(i));
	    }
	}
    }

    static class SetCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<Set<Integer>> {
	@Override
	protected void viewCheckedItemPositionsChanged(ListView view, ValueModel<Set<Integer>> valueModel) {
	    SparseBooleanArray checkedItemPositions = view.getCheckedItemPositions();
	    valueModel.setValue(SparseBooleanArrayUtils.toSet(checkedItemPositions));
	}

	@Override
	public void updateView(ListView view, Set<Integer> newValue) {
	    ListViewUtils.clearSelections(view);
	    for (int position : newValue) {
		view.setItemChecked(position, true);
	    }
	}
    }

    static class MapCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<Map<Integer, Boolean>> {
	@Override
	protected void viewCheckedItemPositionsChanged(ListView view, ValueModel<Map<Integer, Boolean>> valueModel) {
	    SparseBooleanArray checkedItemPositions = view.getCheckedItemPositions();
	    valueModel.setValue(SparseBooleanArrayUtils.toMap(checkedItemPositions));
	}

	@Override
	public void updateView(ListView view, Map<Integer, Boolean> newValue) {
	    ListViewUtils.clearSelections(view);
	    for (Integer position : newValue.keySet()) {
		Boolean checked = newValue.get(position);
		view.setItemChecked(position, checked);
	    }
	}
    }
}
