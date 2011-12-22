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
package org.robobinding.viewattribute;

import org.robobinding.function.Function;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractCommandViewAttribute implements CommandViewAttribute
{
	private String commandName;

	public AbstractCommandViewAttribute(String commandName)
	{
		this.commandName = commandName;
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		Function function = presentationModelAdapter.findFunction(commandName, getPreferredCommandParameterType());
		boolean supportsPreferredParameterType = true;

		if (function == null)
		{
			function = getNoArgsCommand(presentationModelAdapter);
			supportsPreferredParameterType = false;
		}

		bind(new Command(function, supportsPreferredParameterType));
	}

	private Function getNoArgsCommand(PresentationModelAdapter presentationModelAdapter)
	{
		Function noArgsCommand = presentationModelAdapter.findFunction(commandName);

		if (noArgsCommand == null)
		{
			throw new IllegalArgumentException("Could not find method " + commandName + "() or " + commandName + "(" + getAcceptedParameterTypesDescription()
					+ ") in class " + presentationModelAdapter.getPresentationModelClass().getName());
		}

		return noArgsCommand;
	}

	private String getAcceptedParameterTypesDescription()
	{
		Class<?> clazz = getPreferredCommandParameterType();
		StringBuilder descriptionBuilder = new StringBuilder(clazz.getSimpleName());

		while (clazz.getSuperclass() != Object.class)
		{
			clazz = clazz.getSuperclass();
			descriptionBuilder.append('/').append(clazz.getSimpleName());
		}
		return descriptionBuilder.toString();
	}

	protected abstract void bind(Command command);

	protected abstract Class<?> getPreferredCommandParameterType();

}
