package org.robobinding;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robobinding.annotation.PreInitializingViews;
import org.robobinding.function.Function;
import org.robobinding.function.MethodDescriptor;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.property.AbstractGetSet;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.ListDataSet;
import org.robobinding.property.PropertyDescriptor;
import org.robobinding.property.SimpleProperty;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Sample1PresentationModel$$PM extends AbstractPresentationModelObject{
	private Sample1PresentationModel presentationModel;
	
	public Sample1PresentationModel$$PM(Sample1PresentationModel presentationModel) {
		super(presentationModel);
	}
	
	@Override
	public Set<String> propertyNames() {
		return Sets.newHashSet("property");
	}

	@Override
	public Set<String> dataSetPropertyNames() {
		return Sets.newHashSet("source");
	}

	@Override
	public Map<String, Set<String>> propertyDependencies() {
		return Maps.newHashMap();
	}

	@Override
	public Set<MethodDescriptor> eventMethods() {
		return Sets.newHashSet();
	}

	@Override
	public SimpleProperty tryToCreateProperty(String propertyName) {
		if("property".equals(propertyName)) {
			PropertyDescriptor descriptor = createPropertyDescriptor(Boolean.class, propertyName, true, true);
			AbstractGetSet<?> getSet = new AbstractGetSet<Boolean>(descriptor) {
				@Override
				public Boolean getValue() {
					return presentationModel.isProperty();
				}
				
				@Override
				public void setValue(Boolean newValue) {
					presentationModel.setProperty(newValue);
				}
			};
			
			return new SimpleProperty(this, descriptor, getSet);
		}
		
		return null;
	}

	@Override
	public DataSetProperty tryToCreateDataSetProperty(String propertyName) {
		if("source".equals(propertyName)) {
			PropertyDescriptor descriptor = createDataSetPropertyDescriptor(List.class, propertyName);
			
			AbstractGetSet<?> getSet = new AbstractGetSet<List<String>>(descriptor) {
				@Override
				public List<String> getValue() {
					return presentationModel.getSource();
				}
			};
			
			RefreshableItemPresentationModelFactory factory = new RefreshableItemPresentationModelFactory() {
				
				@Override
				public RefreshableItemPresentationModel create(int itemViewType) {
					throw new UnsupportedOperationException();
				}
			};	
			return new DataSetProperty(this, descriptor, new ListDataSet(factory, getSet), PreInitializingViews.DEFAULT);
		}
		
		return null;
	}

	@Override
	public Function tryToCreateFunction(MethodDescriptor method) {
		return null;
	}
}