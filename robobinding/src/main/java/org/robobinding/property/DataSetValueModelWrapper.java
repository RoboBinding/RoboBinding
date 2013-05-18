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
package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetValueModelWrapper<T> implements DataSetValueModel<T> {
    private DataSetValueModel<T> valueModel;

    public DataSetValueModelWrapper(DataSetValueModel<T> valueModel) {
	this.valueModel = valueModel;
    }

    @Override
    public int size() {
	return valueModel.size();
    }

    @Override
    public T getItem(int position) {
	return valueModel.getItem(position);
    }

    @Override
    public ItemPresentationModel<T> newItemPresentationModel() {
	return valueModel.newItemPresentationModel();
    }

    @Override
    public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	valueModel.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	valueModel.removePropertyChangeListener(listener);
    }

}
