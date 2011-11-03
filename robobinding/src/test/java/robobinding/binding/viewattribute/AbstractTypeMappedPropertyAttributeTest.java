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
package robobinding.binding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;

import robobinding.presentationmodel.PresentationModelAdapter;
import robobinding.property.PropertyValueModel;
import robobinding.value.experimental.ValueHolders;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractTypeMappedPropertyAttributeTest<IT, VST>
{
	protected static final String PROPERTY_NAME = "property_name";
	
	protected PropertyValueModel<IT> valueModel;
	protected PresentationModelAdapter presentationModelAdapter;

	protected List<BindingExpectation<IT, VST>> bindingExpectations;
	
	protected abstract VST getViewState();
	
	protected BindingExpectation<IT, VST> getInitialBindingExpectation()
	{
		return bindingExpectations.get(0);
	}
	protected BindingExpectation<IT, VST> getLastBindingExpectation()
	{
		return bindingExpectations.get(bindingExpectations.size() - 1);
	}

	protected AbstractBindingExpectations<IT, VST> populateBindingExpectations()
	{
		TypeMappedBindingSamples<IT, VST> bindingExpectations = new TypeMappedBindingSamples<IT, VST>();
		
		populateBindingExpectations(bindingExpectations);
		
		return bindingExpectations;
	}
	
	protected abstract void populateBindingExpectations(TypeMappedBindingSamples<IT, VST> bindingSamples);

	@Before
	public void initializeBindingExpectations()
	{
		bindingExpectations = populateBindingExpectations().bindingExpectations;
	
		validateBindingExpectations();
	}

	protected void validateBindingExpectations()
	{
		if (bindingExpectations.size() < 2)
			throw new RuntimeException("Please specify at least 2 distinct binding expectations");
	
		Set<BindingExpectation<IT,VST>> uniqueBindings = Sets.newHashSet(bindingExpectations);
		if (uniqueBindings.size() != bindingExpectations.size())
			throw new RuntimeException("All binding expectations should have distinct input values");
	}
	
	protected BindingExpectation<VST, VST> expectBothViewAndModelToBe(VST value)
	{
		return new BindingExpectation<VST, VST>(value, value);
	}
	
	abstract static class AbstractBindingExpectations<IT, VST>
	{
		protected List<BindingExpectation<IT, VST>> bindingExpectations = Lists.newArrayList();
	}
	
	static class TypeMappedBindingSamples<IT, VST> extends AbstractBindingExpectations<IT, VST>
	{
		void addMapping(IT inputValue, VST expectedViewState)
		{
			bindingExpectations.add(new BindingExpectation<IT, VST>(inputValue, expectedViewState));
		}
	}
	
	static class BindingSamples<IT> extends AbstractBindingExpectations<IT, IT>
	{
		void add(IT... samples)
		{
			for (IT sample : samples)
				bindingExpectations.add(new BindingExpectation<IT, IT>(sample, sample));
		}
	}
	
	static class BindingExpectation<IT, VST>
	{
		protected final IT inputValue;
		protected final VST expectedViewState;
		public BindingExpectation(IT inputValue, VST expectedViewState)
		{
			this.inputValue = inputValue;
			this.expectedViewState = expectedViewState;
		}
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((inputValue == null) ? 0 : inputValue.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("rawtypes")
			BindingExpectation other = (BindingExpectation) obj;
			if (inputValue == null)
			{
				if (other.inputValue != null)
					return false;
			} else if (!inputValue.equals(other.inputValue))
				return false;
			return true;
		}
	}
	
	protected void createAttributeWithEither1WayOr2WayBinding()
	{
		if (new Random().nextInt(2) == 0)
			createAttributeWith1WayBinding();
		else
			createAttributeWith2WayBinding();
	}
	
	protected void createAttributeWith1WayBinding()
	{
		this.valueModel = initialValueModelInstance();
		mockPresentationModelFor1WayBinding();
		createAndBindAttribute(BindingType.ONE_WAY);
	}

	protected void createAttributeWith2WayBinding()
	{
		this.valueModel = initialValueModelInstance();
		mockPresentationModelFor2WayBinding();
		createAndBindAttribute(BindingType.TWO_WAY);
	}
	
	protected void updateValueModel(IT value)
	{
		valueModel.setValue(value);
	}
	
	private void createAndBindAttribute(BindingType bindingType)
	{
		String bindingAttributeValue = getBindingAttributeValue(bindingType);
		AbstractPropertyViewAttribute<IT> propertyViewAttribute = newAttributeInstance(bindingAttributeValue);
		propertyViewAttribute.setPresentationModelAdapter(presentationModelAdapter);
		
		if (bindingType == BindingType.ONE_WAY)
			propertyViewAttribute.performOneWayBinding();
		else
			propertyViewAttribute.performTwoWayBinding();
	}

	private String getBindingAttributeValue(BindingType bindingType)
	{
		String bindingAttributeValuePrefix = bindingType == BindingType.ONE_WAY ? "{" : "${";
		String bindingAttributeValue = bindingAttributeValuePrefix + PROPERTY_NAME + "}";
		return bindingAttributeValue;
	}
	
	private void mockPresentationModelFor1WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<IT>getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	private void mockPresentationModelFor2WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<IT>getPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	protected abstract AbstractPropertyViewAttribute<IT> newAttributeInstance(String bindingAttributeValue);

	@SuppressWarnings("unchecked")
	private PropertyValueModel<IT> initialValueModelInstance()
	{
		IT initialValue = getInitialBindingExpectation().inputValue;
		
		if (initialValue instanceof Boolean)
			return (PropertyValueModel<IT>)ValueHolders.createBoolean((Boolean)initialValue);
		else if (initialValue instanceof Integer)
			return (PropertyValueModel<IT>)ValueHolders.createInteger((Integer)initialValue);
		else
			return (PropertyValueModel<IT>)ValueHolders.create(initialValue);
		
	}
}
