/**
 * ListRowsValueModel.java
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

import robobinding.value.ValueModel;


/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class CollectionValueModel<R extends RowObservableBean<B>, B> extends AbstractCollectionValueModel<R, B> implements ValueModel<Collection<B>>
{
	public CollectionValueModel(Class<R> rowPresentationModelClass)
	{
		super(rowPresentationModelClass);
	}
	public CollectionValueModel(RowPresentationModelFactory<R, B> factory)
	{
		super(factory);
	}
	@Override
	public Collection<B> getValue()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setValue(Collection<B> newValue)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		// TODO Auto-generated method stub
		
	}
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	public B getBean(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
