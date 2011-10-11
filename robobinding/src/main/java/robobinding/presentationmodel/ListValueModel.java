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
package robobinding.presentationmodel;

import java.beans.PropertyChangeListener;
import java.util.List;

import robobinding.value.ValueModel;


/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ListValueModel<T> extends AbstractDataSetValueModel<T> implements ValueModel<List<T>>
{
	private List<T> data;
	
	public ListValueModel(Class<? extends RowPresentationModel<T>> rowPresentationModelClass, List<T> data)
	{
		this(new DefaultRowPresentationModelFactory<T>(rowPresentationModelClass), data);
	}
	public ListValueModel(RowPresentationModelFactory<T> factory, List<T> data)
	{
		super(factory);
		this.data = data;
	}
	@Override
	public List<T> getValue()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setValue(List<T> newValue)
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
		return data.size();
	}
	public T getBean(int index)
	{
		return data.get(index);
	}
}
