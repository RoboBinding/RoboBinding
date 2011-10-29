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

import robobinding.binding.CommandViewAttribute;
import robobinding.function.Function;
import robobinding.presentationmodel.PresentationModelAdapter;
import android.content.Context;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
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
		Function command = findPreferredCommand(presentationModelAdapter);
		
		if(command == null)
			command = getNoArgsCommand(presentationModelAdapter);
			
		bind(command);
	}

	private Function getNoArgsCommand(PresentationModelAdapter presentationModelAdapter)
	{
		Function noArgsCommand = presentationModelAdapter.findFunction(commandName);
	
		if (noArgsCommand == null)
			throw new IllegalArgumentException("Cannot find command: " + commandName);
	
		return noArgsCommand;
	}

	private Function findPreferredCommand(PresentationModelAdapter presentationModelAdapter)
	{
		return presentationModelAdapter.findFunction(commandName, getPreferredCommandParameterTypes());
	}
	
	protected abstract void bind(Function command);
	
}
