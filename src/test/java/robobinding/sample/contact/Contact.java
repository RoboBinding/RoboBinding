/**
 * Contact.java
 * Sep 27, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.sample.contact;

import java.util.Collections;
import java.util.List;

import robobinding.utils.Validate;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class Contact
{
	private String name;
	private boolean male;
	private List<String> emails;
	private List<String> phoneNumbers;
	private boolean friend;
	private String friendDescription;
	private Contact(String name, boolean male)
	{
		Validate.notBlank(name);
		
		this.name = name;
		this.male = male;
		emails = Lists.newArrayList();
		phoneNumbers = Lists.newArrayList();
	}
	public static Contact createMale(String name)
	{
		return new Contact(name, true);
	}
	public static Contact createFemale(String name)
	{
		return new Contact(name, false);
	}
	public String getName()
	{
		return name;
	}
	public List<String> getEmails()
	{
		return Collections.unmodifiableList(emails);
	}
	public List<String> getPhoneNumbers()
	{
		return Collections.unmodifiableList(phoneNumbers);
	}
	public boolean isFriend()
	{
		return friend;
	}
	public void setFriend(boolean friend)
	{
		this.friend = friend;
		if(!friend)
		{
			setFriendDescription(null);
		}
	}
	public String getFriendDescription()
	{
		return friendDescription;
	}
	public void setFriendDescription(String friendDescription)
	{
		if(friend)
		{
			this.friendDescription = friendDescription;
		}else
		{
			this.friendDescription = null;
		}
	}
	public boolean isMale()
	{
		return male;
	}
	public void setMale(boolean male)
	{
		this.male = male;
	}
	@Override
	public boolean equals(Object other)
	{
		if (this==other) return true;
		if (!(other instanceof Contact)) return false;
		
		final Contact that = (Contact)other;
		return this.name.equals(that.getName());
	}
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}
