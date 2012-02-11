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
package org.robobinding.experimental.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.experimental.test.PresentationModelPropertyChangeSpy;
import org.robobinding.experimental.test.PresentationModelTester;
import org.robobinding.presentationmodel.DependsOnStateOf;
import org.robobinding.presentationmodel.PresentationModel;
import org.robobinding.viewattribute.RandomValues;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependsOnStateOfUnitTest
{
	private SamplePresentationModel presentationModel;
	private PresentationModelTester presentationModelTester;
	@Before
	public void setUp()
	{
		presentationModel = new SamplePresentationModel();
		presentationModelTester = PresentationModelTester.create(presentationModel);
	}
	@Test
	public void whenChangeProperty1_thenProperty2ChangeFired()
	{
		PresentationModelPropertyChangeSpy spy = presentationModelTester.spyReadOnlyPropertyChange(SamplePresentationModel.PROPERTY2);

		presentationModel.changeProperty1();
		
		assertTrue(spy.isChangeFired());
	}
	
	@PresentationModel
	public static class SamplePresentationModel
	{
		private static final String PROPERTY1 = "property1";
		private static final String PROPERTY2 = "property2";
		private boolean property1Value;
		public boolean getProperty1()
		{
			return property1Value;
		}
		
		public void changeProperty1()
		{
			property1Value = RandomValues.trueOrFalse();
			firePropertyChange(PROPERTY1);
		}
		
		@DependsOnStateOf(PROPERTY1)
		public boolean getProperty2()
		{
			return !property1Value;
		}
	}
}
