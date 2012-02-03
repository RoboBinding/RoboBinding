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
package org.robobinding.viewattribute.textview;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CharSequenceMatcher extends TypeSafeMatcher<CharSequence>
{
	private final CharSequence value;

	public CharSequenceMatcher(CharSequence value)
	{
		this.value = value;
	}

	@Override
	public boolean matchesSafely(CharSequence item)
	{
		return value.toString().equals(item.toString());
	}
	
	@Override
	public void describeTo(Description description)
	{
		description.appendText("equivalent text value to '").appendText(value.toString()).appendText("'");
	}
	
	@Factory 
	public static Matcher<CharSequence> sameAs(CharSequence value)
	{
		return new CharSequenceMatcher(value);
	}
}
