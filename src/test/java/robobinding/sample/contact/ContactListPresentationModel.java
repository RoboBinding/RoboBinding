/**
 * ContactsPresentationModel.java
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
import java.util.Collection;

import robobinding.beans.ExtendedPropertyChangeSupport;
import robobinding.beans.ObservableBean;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ContactListPresentationModel implements ObservableBean, CustomPropertyProvider
{
	public static final String PROPERTY_CONTACTS = "contacts";
	public static final String PROPERTY_SELECTED_CONTACT_INDEX = "selectedContactIndex";
	
	private ContactManager contactManager;
	private int selectedContactIndex;
	private Action newAction;
	private Action editAction;
	private Action deleteAction;
	
	private final ExtendedPropertyChangeSupport propertyChangeSupport;
	public ContactListPresentationModel()
	{
		propertyChangeSupport = new ExtendedPropertyChangeSupport(this);
	}
	public Collection<Contact> getContacts()
	{
		return contactManager.getContacts();
	}
	public int getSelectedContactIndex()
	{
		return selectedContactIndex;
	}
	public void setSelectedContactIndex(int selectedContactIndex)
	{
		int oldValue = getSelectedContactIndex();
		this.selectedContactIndex = selectedContactIndex;
		propertyChangeSupport.firePropertyChange(PROPERTY_SELECTED_CONTACT_INDEX, oldValue, selectedContactIndex);
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	@Override
	public boolean supports(String propertyName)
	{
		return PROPERTY_CONTACTS.equals(propertyName);
	}
	@Override
	public ValueModel<?> createProperty(String propertyName)
	{
		if(PROPERTY_CONTACTS.equals(propertyName))
		{
			return new CollectionValueModel<ContactPresentationModel, Contact>(ContactPresentationModel.class);
		}
		return null;
	}
	public Action getEditAction()
	{
		return editAction;
	}
	public Action getNewAction()
	{
		return newAction;
	}
	public Action getDeleteAction()
	{
		return deleteAction;
	}
}
