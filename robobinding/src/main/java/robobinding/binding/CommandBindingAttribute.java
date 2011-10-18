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
package robobinding.binding;

import robobinding.beans.PresentationModelAdapter;
import android.content.Context;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class CommandBindingAttribute extends BindingAttribute
{
	private final CommandViewAttribute commandViewAttribute;

	public CommandBindingAttribute(String attributeName, CommandViewAttribute commandViewAttribute)
	{
		super(attributeName);
		this.commandViewAttribute = commandViewAttribute;
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		commandViewAttribute.bind(presentationModelAdapter);
	}

}
