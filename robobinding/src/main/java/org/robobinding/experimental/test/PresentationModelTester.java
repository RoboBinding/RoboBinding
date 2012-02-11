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
package org.robobinding.experimental.test;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterImpl;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.ValueModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelTester extends PresentationModelAdapterWrapper implements PresentationModelAdapter
{
	private PresentationModelTester(PresentationModelAdapter presentationModelAdapter)
	{
		super(presentationModelAdapter);
	}
	
	public PresentationModelPropertyChangeSpy spyPropertyChange(String propertyName)
	{
		ValueModel<Object> valueModel = getPropertyValueModel(propertyName);
		return spyValueModelChange(valueModel);
	}
	
	public PresentationModelPropertyChangeSpy spyReadOnlyPropertyChange(String propertyName)
	{
		ValueModel<Object> valueModel = getReadOnlyPropertyValueModel(propertyName);
		return spyValueModelChange(valueModel);
	}

	private PresentationModelPropertyChangeSpy spyValueModelChange(ValueModel<Object> valueModel)
	{
		PresentationModelPropertyChangeSpy spy = new PresentationModelPropertyChangeSpy();
		valueModel.addPropertyChangeListener(spy);
		return spy;
	}
	
	public void removePropertyChangeSpy(String propertyName, PresentationModelPropertyChangeSpy spy)
	{
		ValueModel<Object> valueModel = getPropertyValueModel(propertyName);
		valueModel.removePropertyChangeListener(spy);
	}

	public PresentationModelPropertyChangeSpy spyDataSetPropertyChange(String propertyName)
	{
		DataSetValueModel<?> dataSetValueModel = getDataSetPropertyValueModel(propertyName);
		PresentationModelPropertyChangeSpy spy = new PresentationModelPropertyChangeSpy();
		dataSetValueModel.addPropertyChangeListener(spy);
		return spy;
	}
	
	public void removeDataSetPropertyChangeSpy(String propertyName, PresentationModelPropertyChangeSpy spy)
	{
		DataSetValueModel<?> dataSetValueModel = getDataSetPropertyValueModel(propertyName);
		dataSetValueModel.removePropertyChangeListener(spy);
	}
	
	public static PresentationModelTester create(Object presentationModel)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		return new PresentationModelTester(presentationModelAdapter);
	}
}
