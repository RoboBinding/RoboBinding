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
package robobinding.binding.viewconnectors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class OnClickBinder
{
	public OnClickBinder(View view, final String commandName, final Object presentationModel)
	{
		try
		{
			final Method command = presentationModel.getClass().getMethod(commandName);
			setOnClickListener(view, presentationModel, command);
		} 
		catch (SecurityException e)
		{
			throw new RuntimeException(e);
		} 
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void setOnClickListener(View view, final Object presentationModel, final Method method)
	{
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				invokeCommand(presentationModel, method);
			}
		});
	}
	
	private void invokeCommand(final Object presentationModel, final Method command)
	{
		try
		{
			command.invoke(presentationModel);
		} 
		catch (IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		} 
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		} 
		catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}
}
