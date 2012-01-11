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

import org.junit.runner.RunWith;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.viewattribute.listview.HeaderOrFooterVisibilityAttribute.BooleanVisibilityAttribute;
import org.robobinding.viewattribute.listview.HeaderOrFooterVisibilityAttribute.IntegerVisibilityAttribute;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class HeaderOrFooterVisibilityAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<HeaderOrFooterVisibilityAttribute>
{
	@Override
	protected void setTypeMappingExpectations()
	{
		forPropertyType(int.class).expectAttribute(IntegerVisibilityAttribute.class);
		forPropertyType(Integer.class).expectAttribute(IntegerVisibilityAttribute.class);
		forPropertyType(Boolean.class).expectAttribute(BooleanVisibilityAttribute.class);
		forPropertyType(boolean.class).expectAttribute(BooleanVisibilityAttribute.class);
	}
	
	@Override
	protected AbstractMultiTypePropertyViewAttribute<?> createAttribute()
	{
		return new HeaderOrFooterVisibilityAttribute(null);
	}
}
