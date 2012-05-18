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
package org.robobinding.attribute;

import org.apache.commons.lang3.ArrayUtils;
import org.robobinding.function.Function;
import org.robobinding.presentationmodel.PresentationModelAdapter;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CommandAttribute extends AbstractAttribute
{
	private String commandName;
	public CommandAttribute(String name, String value)
	{
		super(name);
		
		if (curlyBracesArePresentIn(value))
		{
			throw new MalformedAttributeException(name,
					"Curly braces should be used for binding to properties. "
					+ "Event handling invokes methods on your presentation model, and there is no method called '" + value + "'");
		}
		
		this.commandName = value;
	}

	private boolean curlyBracesArePresentIn(String commandName)
	{
		return commandName.contains("{") || commandName.contains("}");
	}
	
	public Command findCommand(PresentationModelAdapter presentationModelAdapter, Class<?>... parameterTypes)
	{
		Function function = presentationModelAdapter.findFunction(commandName, parameterTypes);
		if(function != null)
		{
			return ArrayUtils.isNotEmpty(parameterTypes)?new Command(function, true):new Command(function, false);
		}else
		{
			return null;
		}
	}

	public String getCommandName()
	{
		return commandName;
	}
}
