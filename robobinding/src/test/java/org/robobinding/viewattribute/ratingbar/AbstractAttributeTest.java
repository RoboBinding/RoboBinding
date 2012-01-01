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
package org.robobinding.viewattribute.ratingbar;

import java.lang.reflect.Constructor;

import org.junit.runner.RunWith;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class AbstractAttributeTest<S extends View, T extends PropertyViewAttribute<S>>
{
	protected S view;
	protected T attribute;
	
	protected void initializeViewAndAttribute(Class<S> viewClass, Class<T> attributeClass)
	{
		try
		{
			Constructor<S> constructor = viewClass.getConstructor(Context.class);
			view = constructor.newInstance(new Activity());
		} 
		catch (Exception e)
		{
			throw new RuntimeException("Error instantiating view: " + e.getMessage());
		}
		
		try
		{
			attribute = attributeClass.newInstance();
			attribute.setView(view);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error instantiating attribute: " + e.getMessage());
		}
	}
}
