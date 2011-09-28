/**
 * AlbumPresentationModel.java
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

import java.beans.PropertyChangeListener;

import robobinding.beans.ExtendedPropertyChangeSupport;
import robobinding.value.Converters;
import robobinding.value.ValueHolders;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ContactPresentationModel implements CustomPropertyProvider, RowObservableBean<Contact>
{
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_MALE = "male";
	public static final String PROPERTY_FRIEND = "friend";
	public static final String PROPERTY_FRIEND_DESCRIPTION = "friendDescription";
	public static final String PROPERTY_EMAILS = "emails";
	public static final String PROPERTY_PHONES = "phones";
	
	private final ExtendedPropertyChangeSupport propertyChangeSupport;
	private Contact contact;
	public ContactPresentationModel()
	{
		propertyChangeSupport = new ExtendedPropertyChangeSupport(this);
	}
	@Override
	public void setData(Contact bean)
	{
		this.contact = bean;
	}
	public void setFriend(boolean friend)
	{
		boolean oldValue = contact.isFriend();
		contact.setFriend(friend);
		propertyChangeSupport.firePropertyChange(PROPERTY_FRIEND, oldValue, contact.isFriend());
		propertyChangeSupport.firePropertyChange(PROPERTY_FRIEND_DESCRIPTION, oldValue, contact.getFriendDescription());
	}
	public void setFriendDescription(String friendDescription)
	{
		String oldValue = contact.getFriendDescription();
		contact.setFriendDescription(friendDescription);
		propertyChangeSupport.firePropertyChange(PROPERTY_FRIEND_DESCRIPTION, oldValue, contact.getFriendDescription());
	}
	public void setMale(boolean male)
	{
		boolean oldValue = contact.isMale();
		contact.setMale(male);
		propertyChangeSupport.firePropertyChange(PROPERTY_MALE, oldValue, contact.isMale());
	}
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	@Override
	public boolean supports(String propertyName)
	{
		return PROPERTY_MALE.equals(propertyName);
	}
	@Override
	public ValueModel<?> createProperty(String propertyName)
	{
		if(PROPERTY_MALE.equals(propertyName))
		{
			return Converters.createBooleanToStringConverter(ValueHolders.createBoolean(contact.isMale()), "M", "F");
		}
		return null;
	}
}
