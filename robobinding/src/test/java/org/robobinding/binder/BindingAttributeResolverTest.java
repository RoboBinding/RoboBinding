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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.internal.com_google_common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingAttributeResolverTest
{
	@Test
	public void givenThereAreNoPendingAttributes_whenVerifyingAllAttributesResolved_thenDoNothing()
	{
		Map<String, String> pendingAttributes = Maps.newHashMap();
		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(pendingAttributes);
	
		bindingAttributeResolver.verifyAllAttributesResolved();
	}
	
	@Test (expected = UnrecognizedBindingAttributeException.class)
	public void givenThereArePendingAttributes_whenVerifyingAllAttributesResolved_thenThrowUnrecognizedBindingAttributeException()
	{
		Map<String, String> pendingAttributes = Maps.newHashMap();
		pendingAttributes.put("text", "{value}");
		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(pendingAttributes);
	
		bindingAttributeResolver.verifyAllAttributesResolved();
	}
}
