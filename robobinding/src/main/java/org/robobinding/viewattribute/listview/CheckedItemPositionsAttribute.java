/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.listview;

import java.util.Map;
import java.util.Set;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.adapterview.AdapterViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

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
public class CheckedItemPositionsAttribute extends AbstractMultiTypePropertyViewAttribute<ListView> {
    @Override
    protected AbstractPropertyViewAttribute<ListView, ?> createPropertyViewAttribute(Class<?> propertyType) {
	if (SparseBooleanArray.class.isAssignableFrom(propertyType)) {
	    return new SparseBooleanArrayCheckedItemPositionsAttribute();
	} else if (Set.class.isAssignableFrom(propertyType)) {
	    return new SetCheckedItemPositionsAttribute();
	} else if (Map.class.isAssignableFrom(propertyType)) {
	    return new MapCheckedItemPositionsAttribute();
	}

	throw new RuntimeException("Could not find a suitable checkedItemPositions attribute class for property type: " + propertyType);
    }

    abstract static class AbstractCheckedItemPositionsAttribute<PropertyType> extends AbstractPropertyViewAttribute<ListView, PropertyType> implements
	    ViewListenersAware<AdapterViewListeners> {
	private AdapterViewListeners adapterViewListeners;

	@Override
	public void setViewListeners(AdapterViewListeners adapterViewListeners) {
	    this.adapterViewListeners = adapterViewListeners;
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<PropertyType> valueModel) {
	    adapterViewListeners.addOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View itemView, int position, long id) {
		    viewCheckedItemPositionsChanged(valueModel);
		}
	    });
	}

	protected abstract void viewCheckedItemPositionsChanged(ValueModel<PropertyType> valueModel);
    }

    static class SparseBooleanArrayCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<SparseBooleanArray> {
	@Override
	protected void viewCheckedItemPositionsChanged(ValueModel<SparseBooleanArray> valueModel) {
	    SparseBooleanArray checkedItemPositions = view.getCheckedItemPositions();
	    valueModel.setValue(checkedItemPositions);
	}

	@Override
	protected void valueModelUpdated(SparseBooleanArray array) {
	    ListViewUtils.clearSelections(view);
	    for (int i = 0; i < array.size(); i++) {
		view.setItemChecked(array.keyAt(i), array.valueAt(i));
	    }
	}
    }

    static class SetCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<Set<Integer>> {
	@Override
	protected void viewCheckedItemPositionsChanged(ValueModel<Set<Integer>> valueModel) {
	    SparseBooleanArray checkedItemPositions = view.getCheckedItemPositions();
	    valueModel.setValue(SparseBooleanArrayUtils.toSet(checkedItemPositions));
	}

	@Override
	protected void valueModelUpdated(Set<Integer> newValue) {
	    ListViewUtils.clearSelections(view);
	    for (int position : newValue) {
		view.setItemChecked(position, true);
	    }
	}
    }

    static class MapCheckedItemPositionsAttribute extends AbstractCheckedItemPositionsAttribute<Map<Integer, Boolean>> {
	@Override
	protected void viewCheckedItemPositionsChanged(ValueModel<Map<Integer, Boolean>> valueModel) {
	    SparseBooleanArray checkedItemPositions = view.getCheckedItemPositions();
	    valueModel.setValue(SparseBooleanArrayUtils.toMap(checkedItemPositions));
	}

	@Override
	protected void valueModelUpdated(Map<Integer, Boolean> newValue) {
	    ListViewUtils.clearSelections(view);
	    for (Integer position : newValue.keySet()) {
		Boolean checked = newValue.get(position);
		view.setItemChecked(position, checked);
	    }
	}
    }
}
