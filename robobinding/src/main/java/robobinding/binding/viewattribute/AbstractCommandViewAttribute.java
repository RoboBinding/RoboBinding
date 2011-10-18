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

import java.lang.reflect.Method;

import robobinding.beans.Command;
import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.CommandViewAttribute;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public abstract class AbstractCommandViewAttribute implements CommandViewAttribute
{
	private Object presentationModel;
	private String commandName;
	
	public AbstractCommandViewAttribute(String commandName)
	{
		this.commandName = commandName;
	}
	
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter)
	{
		this.presentationModel = presentationModelAdapter.getPresentationModel();
		
		Command command = getCommand();
		bind(command);
	}
	
	protected abstract void bind(Command command);
	
	private Command getCommand()
	{
		Method method = getPreferredMethod();
		
		if (method == null)
			method = getNoArgsMethod();
		
		return new Command(presentationModel, method);
	}

	private Method getPreferredMethod()
	{
		return findMethodWithMatchingName(getPreferredCommandParameterTypes());
	}

	private Method getNoArgsMethod()
	{
		return findMethodWithMatchingName();
	}
	
	private Method findMethodWithMatchingName(Class<?>... parameterTypes)
	{
		try
		{
			return presentationModel.getClass().getMethod(commandName, parameterTypes);
		} 
		catch (NoSuchMethodException e)
		{}
		
		return null;
	}
}
