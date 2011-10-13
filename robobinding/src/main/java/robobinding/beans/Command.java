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
package robobinding.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * 
 */
public class Command
{
	private final Object presentationModel;
	private final Method method;

	public Command(Object presentationModel, Method method)
	{
		if (method == null)
			throw new RuntimeException();

		this.presentationModel = presentationModel;
		this.method = method;
	}

	public void invoke(Object... preferredArgs)
	{
		try
		{
			method.invoke(presentationModel, methodAcceptsArguments() ? preferredArgs : null);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		} catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	private boolean methodAcceptsArguments()
	{
		return method.getParameterTypes().length > 0;
	}

	public Method getMethod()
	{
		return method;
	}
}
