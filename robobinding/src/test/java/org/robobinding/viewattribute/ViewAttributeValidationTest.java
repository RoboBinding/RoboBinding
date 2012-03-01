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
package org.robobinding.viewattribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeValidationTest
{
	private ViewAttributeValidation validation;
	
	@Before
	public void setUp()
	{
		validation = new ViewAttributeValidation();
	}
	
	@Test
	public void givenBlankString_thenErrorMessageAdded()
	{
		validation.notBlank(RandomValues.anyBlankString(), "error");
		
		assertTrue(validation.hasErrors());
	}
	
	@Test
	public void givenNonBlankString_thenNoErrorMessageAdded()
	{
		validation.notBlank("string", "error");
		
		assertFalse(validation.hasErrors());
	}
	
	@Test
	public void givenNullObject_thenErrorMessageAdded()
	{
		validation.notNull(null, "error");
		
		assertTrue(validation.hasErrors());
	}
	
	@Test
	public void givenNonObject_thenNoErrorMessageAdded()
	{
		validation.notNull(new Object(), "error");
		
		assertFalse(validation.hasErrors());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void whenAddBlankErrorMessage_thenRejected()
	{
		validation.addError(RandomValues.anyBlankString());
	}
	
	@Test(expected=NullPointerException.class)
	public void whenAddNullErrorMessage_thenRejected()
	{
		validation.addError(null);
	}
	
	@Test
	public void whenAddErrorMessage_thenErrorMessageAdded()
	{
		String errorMessage = "error";
		
		validation.addError(errorMessage);
		
		assertThat(errorMessagesAsString(), equalTo(errorMessage));
	}
	
	@Test
	public void whenAddTwoErrorMessages_whenTwoErrorMessagesAdded()
	{
		String errorMessage = "error";
		
		validation.addError(errorMessage);
		validation.addError(errorMessage);
		
		assertThat(errorMessagesAsString(), equalTo(errorMessage+ViewAttributeValidation.ERROR_MESSAGE_SEPARATOR+errorMessage));
	}
	private String errorMessagesAsString()
	{
		assertTrue(validation.hasErrors());
		return validation.errorMessages.toString();
	}
	
	@Test
	public void givenNoErrorMessagesAdded_whenAssertNoErrors_thenSuccessful()
	{
		validation.assertNoErrors();
	}
	
	@Test(expected=IllegalStateException.class)
	public void givenErrorMessageAdded_whenAssertNoErrors_thenThrowsException()
	{
		validation.addError("error");
		
		validation.assertNoErrors();
	}
}
