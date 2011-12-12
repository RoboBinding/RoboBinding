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
package org.robobinding.presentationmodelaspects;

import org.robobinding.presentationmodelaspects.CustomSetter;
import org.robobinding.presentationmodelaspects.PresentationModel;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@PresentationModel
public class PresentationModel_AutoCodeGeneration
{
	public static final String PROPERTY = "property";
	public static final String CUSTOM_PROPERTY = "customProperty";
	public static final String PROPERTY_WITH_RETURN_VALUE = "propertyWithReturnValue";
	public static final String PROPERTY_WITH_MULTIPLE_PARAMETERS = "propertyWithMultipleParameters";
	
	public void setProperty(boolean b)
	{
	}
	
	@CustomSetter
	public void setCustomProperty(boolean b)
	{
	}
	
	public boolean setPropertyWithReturnValue(boolean b)
	{
		return true;
	}
	
	public void setPropertyWithMultipleParameters(boolean p1, String p2)
	{
		
	}
}
