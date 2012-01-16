/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.property;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DataSetDependencyProperty<T> extends DataSetPropertyWrapper<T>
{
	private Dependency dependency;
	public DataSetDependencyProperty(AbstractDataSetProperty<T> dataSetProperty, Dependency dependency)
	{
		super(dataSetProperty);
		this.dependency = dependency;
		dependency.addListenerToDependentProperties(dataSetProperty);
	}
	@Override
	public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		super.addPropertyChangeListener(listener);
		dependency.addListenerToDependentProperties(listener);
	}
	@Override
	public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		super.removePropertyChangeListener(listener);
		dependency.removeListenerOffDependentProperties(listener);
	}
	@Override
	public String toString()
	{
		return dependency.decribeDependencyProperty();
	}
}