/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package robobinding.binding.viewattribute;

import org.junit.Before;
import org.junit.runner.RunWith;

import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class BooleanVisibilityAttributeTest extends AbstractTypeMappedOneWayPropertyAttributeTest<Boolean, Integer>
{
	private View view;
	
	@Before
	public void setUp()
	{
		view = new View(null);
	}
	
	@Override
	protected AbstractPropertyViewAttribute<Boolean> newAttributeInstance(String bindingAttributeValue)
	{
		VisibilityAttribute visibilityAttribute = new VisibilityAttribute(view, bindingAttributeValue, true);
		return visibilityAttribute.new BooleanVisibilityAttribute();
	}

	@Override
	protected Integer getViewState()
	{
		return view.getVisibility();
	}

	@Override
	protected void populateBindingExpectations(TypeMappedBindingSamples<Boolean, Integer> bindingExpectations)
	{
		bindingExpectations.addMapping(false, View.GONE);
		bindingExpectations.addMapping(true, View.VISIBLE);
	}
}
