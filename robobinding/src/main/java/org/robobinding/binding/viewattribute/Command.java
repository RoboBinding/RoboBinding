/**
 * Copyright 2011 Cheng Wei and Robert Taylor
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
package org.robobinding.binding.viewattribute;

import org.robobinding.function.Function;

public class Command
{
	public final Function function;
	private final boolean supportsPreferredParameterType;

	public Command(Function function, boolean supportsPreferredParameterType)
	{
		this.function = function;
		this.supportsPreferredParameterType = supportsPreferredParameterType;
	}
	
	public void invoke(Object arg)
	{
		if(supportsPreferredParameterType)
			function.call(arg);
		else
			function.call();
	}
}