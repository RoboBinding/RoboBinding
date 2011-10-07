/**
 * ContactManager.java
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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ContactManager
{
	private Set<Contact> contacts;
	public ContactManager()
	{
		contacts = Sets.newLinkedHashSet();
		
		addContact(createContact1());
		addContact(createContact2());
		addContact(createContact3());
		addContact(createContact4());
	}
	private Contact createContact1()
	{
		Contact contact = Contact.createMale("Male1");
		contact.setFriend(true);
		contact.setFriendDescription("Smoker");
		return contact;
	}
	private Contact createContact2()
	{
		Contact contact = Contact.createMale("Male2");
		contact.setFriend(false);
		return contact;
	}
	private Contact createContact3()
	{
		Contact contact = Contact.createMale("Female1");
		contact.setFriend(true);
		contact.setFriendDescription("Like rose");
		return contact;
	}
	private Contact createContact4()
	{
		Contact contact = Contact.createMale("Female2");
		contact.setFriend(false);
		return contact;
	}
	public void addContact(Contact contactToAdd)
	{
		contacts.add(contactToAdd);
	}
	public void removeContact(Contact contactToRemove)
	{
		contacts.remove(contactToRemove);
	}
	public Collection<Contact> getContacts()
	{
		return Collections.unmodifiableCollection(contacts);
	}
}
