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
package org.robobinding.viewattribute.adapterview;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.presentationmodel.DataSetAdapter;
import org.robobinding.viewattribute.MockPresentationModelForProperty;
import org.robobinding.viewattribute.adapterview.ItemLayoutAttribute.DynamicLayoutAttribute;

import android.app.Activity;
import android.widget.AdapterView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
@SuppressWarnings({"rawtypes"})
public class AbstractDynamicLayoutAttributeTest
{
	protected DynamicLayoutAttribute dynamicItemLayoutAttribute;
	protected AdapterView adapterView;
	protected DataSetAdapter<?> dataSetAdapter;
	
	@Before
	public void init()
	{
		adapterView = mock(AdapterView.class);
		dataSetAdapter = mock(DataSetAdapter.class);
	}
	
	protected void bindAttribute(DataSetAdapter<?> dataSetAdapter)
	{
		MockPresentationModelForProperty<Integer> presentationModelAdapter = new MockPresentationModelForProperty<Integer>(int.class);
		dynamicItemLayoutAttribute.bind(dataSetAdapter, presentationModelAdapter, new Activity());
	}
}
