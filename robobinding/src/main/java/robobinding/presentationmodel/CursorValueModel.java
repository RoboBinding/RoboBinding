/**
 * CursorValueModel.java
 * 11 Oct 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.presentationmodel;

import java.beans.PropertyChangeListener;



/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class CursorValueModel<T> extends AbstractDataSetValueModel<TypedCursor<T>, T>
{
	private TypedCursor<T> cursor;

	public CursorValueModel(Class<? extends ItemPresentationModel<T>> rowPresentationModelClass, TypedCursor<T> cursor)
	{
		this(new DefaultItemPresentationModelFactory<T>(rowPresentationModelClass), cursor);
	}
	public CursorValueModel(ItemPresentationModelFactory<T> factory, TypedCursor<T> cursor)
	{
		super(factory);
		this.cursor = cursor;
	}
	@Override
	public int size()
	{
		return cursor.getCount();
	}
	@Override
	public T getItem(int position)
	{
		return cursor.getObjectAtPosition(position);
	}
	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public TypedCursor<T> getValue()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setValue(TypedCursor<T> newValue)
	{
		// TODO Auto-generated method stub
		
	}
}
