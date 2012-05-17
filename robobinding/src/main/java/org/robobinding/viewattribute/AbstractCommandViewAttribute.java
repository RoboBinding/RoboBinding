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

import org.robobinding.BindingContext;
import org.robobinding.attributevalue.Command;
import org.robobinding.attributevalue.CommandAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractCommandViewAttribute<T extends View> implements ViewAttribute
{
	protected T view;
	private CommandAttribute attribute;

	public void setView(T view)
	{
		this.view = view;
	}

	public void setAttribute(CommandAttribute attribute)
	{
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext)
	{
		try
		{
			performValidate();
			performBind(bindingContext.getPresentationModelAdapter());
		}catch(RuntimeException e)
		{
			throw new AttributeBindingException(attribute.getName(), e.getMessage());
		}
	}

	private void performValidate()
	{
		ViewAttributeValidation validation = new ViewAttributeValidation();
		validate(validation);
		validation.assertNoErrors();
	}

	protected void validate(ViewAttributeValidation validation)
	{
		validation.addErrorIfViewNotSet(view);
		validation.addErrorIfCommandNameNotSet(attribute);
	}

	private void performBind(PresentationModelAdapter presentationModelAdapter)
	{
		Command command = attribute.findCommand(presentationModelAdapter, getPreferredCommandParameterType());
		if(command != null)
		{
			bind(command);
		}else
		{
			bind(getNoArgsCommand(presentationModelAdapter));
		}
	}

	private Command getNoArgsCommand(PresentationModelAdapter presentationModelAdapter)
	{
		Command noArgsCommand = attribute.findCommand(presentationModelAdapter);

		if (noArgsCommand == null)
		{
			String commandName = attribute.getCommandName();
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
