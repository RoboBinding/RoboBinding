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


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class SetterAndPropertyChangeExpectation
{
	public abstract void setProperty(PresentationModel_AutoCodeGeneration presentationModel);

	public void assertExpectation(PropertyChangeListenerTester propertyChangeListenerTester)
	{
		propertyChangeListenerTester.assertTimesOfPropertyChanged(getExpectedTimesOfPropertyChanged());
	}
	protected int getExpectedTimesOfPropertyChanged()
	{
		return 0;
	}
	
	public static SetterAndPropertyChangeExpectation PROPERTY = new SetterAndPropertyChangeExpectation() {
		
		@Override
		public void setProperty(PresentationModel_AutoCodeGeneration presentationModel)
		{
			presentationModel.setProperty(true);
		}
		@Override
		protected int getExpectedTimesOfPropertyChanged()
		{
			return 1;
		}
	};
	
	public static SetterAndPropertyChangeExpectation CUSTOM_PROPERTY = new SetterAndPropertyChangeExpectation() {
		
		@Override
		public void setProperty(PresentationModel_AutoCodeGeneration presentationModel)
		{
			presentationModel.setCustomProperty(true);
		}
	};
	
	public static SetterAndPropertyChangeExpectation PROPERTY_WITH_RETURN_VALUE = new SetterAndPropertyChangeExpectation() {
		
		@Override
		public void setProperty(PresentationModel_AutoCodeGeneration presentationModel)
		{
			presentationModel.setPropertyWithReturnValue(true);
		}
	};
	
	public static SetterAndPropertyChangeExpectation PROPERTY_WITH_MULTIPLE_PARAMETERS = new SetterAndPropertyChangeExpectation() {
		
		@Override
		public void setProperty(PresentationModel_AutoCodeGeneration presentationModel)
		{
			presentationModel.setPropertyWithMultipleParameters(true, "");
			
		}
	};
}
