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
package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.binder.BindingViewInflationErrorBuilder.aBindingViewInflationError;

import org.junit.Before;
import org.junit.Test;
/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PlainTextErrorFormatterTest
{
	private PlainTextErrorFormatter errorFormatter;
	
	@Before
	public void setUp()
	{
		errorFormatter = new PlainTextErrorFormatter();
	}
	
	@Test
	public void whenFormatAGivenInflationError_thenReceivedAnExpectedMessage()
	{
		String message = errorFormatter.format(aBindingViewInflationError("CustomView")
				.withAttributeResolutionErrorOf("text", "invalid syntax ${prop1")
				.withMissingRequiredAttributesResolutionErrorOf("source", "dropdownLayout")
				.withAttributeBindingErrorOf("visibility", "unmatch presentationModel.prop2 type")
				.build());

		assertThat(message, 
				equalTo("-------------------------CustomView(3 errors)-----------------------\r\n"+
						"text: invalid syntax ${prop1\r\n\r\n"+
						"Missing attributes: source, dropdownLayout\r\n\r\n"+
						"visibility: unmatch presentationModel.prop2 type\r\n"));
	}
}
