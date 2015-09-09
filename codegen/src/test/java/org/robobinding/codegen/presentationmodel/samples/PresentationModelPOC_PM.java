package org.robobinding.codegen.presentationmodel.samples;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.robobinding.widget.view.AbstractViewEvent;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelPOC_PM extends AbstractPresentationModelObject {
	private final PresentationModelPOC presentationModel;
	
	private static final String PROP1 = "prop1";
	private static final String PROP2 = "prop2";
	private static final String DATA_SET_PROP = "dataSetProp";
	private static final String DATA_SET_PROP_WITH_FACTORY_METHOD = "dataSetPropWithFactoryMethod";
	private static final String ON_CLICK = "onClick";
	private static final String ON_CLICK_WITH_EVENT = "onClickWithEvent";

	
	public PresentationModelPOC_PM(PresentationModelPOC presentationModel) {
		super(presentationModel);
		
		this.presentationModel = presentationModel;
	}

	@Override
	public Set<String> propertyNames() {
		return Sets.newHashSet(PROP1, PROP2);
	}
	
	@Override
	public Set<String> dataSetPropertyNames() {
		return Sets.newHashSet(DATA_SET_PROP, DATA_SET_PROP_WITH_FACTORY_METHOD);
	}
	
	@Override
	public Map<String, Set<String>> propertyDependencies() {
		Map<String, Set<String>> dependencies = Maps.newHashMap();
		dependencies.put(PROP1, Sets.newHashSet(PROP2));
		return dependencies;
	}

	@Override
	public Set<MethodDescriptor> eventMethods() {
		return Sets.newHashSet(
				createMethodDescriptor(ON_CLICK),
				createMethodDescriptor(ON_CLICK_WITH_EVENT, AbstractViewEvent.class));
	}
	
	@Override
	public SimpleProperty tryToCreateProperty(String name) {
		if(name.equals(PROP1)) {
			PropertyDescriptor descriptor = createPropertyDescriptor(String.class, name, true, true);
			
			AbstractGetSet<?> getSet = new AbstractGetSet<String>(descriptor) {
				@Override
				public String getValue() {
					return presentationModel.getProp1();
				}
				
				@Override
				public void setValue(String newValue) {
					presentationModel.setProp1(newValue);
				}
			};
			
			return new SimpleProperty(this, descriptor, getSet);
		} 
		
		if(name.equals(PROP2)) {
			PropertyDescriptor descriptor = createPropertyDescriptor(Integer.class, name, true, true);
			
			AbstractGetSet<?> getSet = new AbstractGetSet<Integer>(descriptor) {
				@Override
				public Integer getValue() {
					return presentationModel.getProp2();
				}
				
				@Override
				public void setValue(Integer newValue) {
					presentationModel.setProp2(newValue);
				}
			};
			
			return new SimpleProperty(this, descriptor, getSet);
		} 

		return null;
	}

	@Override
	public DataSetProperty tryToCreateDataSetProperty(String name) {
		if(name.equals(DATA_SET_PROP)) {
			PropertyDescriptor descriptor = createDataSetPropertyDescriptor(List.class, name);
			
			AbstractGetSet<?> getSet = new AbstractGetSet<List<String>>(descriptor) {
				@Override
				public List<String> getValue() {
					return presentationModel.getDataSetProp();
				}
			};
			
			RefreshableItemPresentationModelFactory factory = new RefreshableItemPresentationModelFactory() {
				
				@Override
				public RefreshableItemPresentationModel create(Object item) {
					return new StringItemPresentationModelPOC_IPM(new StringItemPresentationModelPOC());
				}
			};	
			
			return new DataSetProperty(this, descriptor, new ListDataSet(factory, getSet));
		}
		
		if(name.equals(DATA_SET_PROP_WITH_FACTORY_METHOD)) {
			PropertyDescriptor descriptor = createDataSetPropertyDescriptor(List.class, name);
			
			AbstractGetSet<?> getSet = new AbstractGetSet<List<String>>(descriptor) {
				@Override
				public List<String> getValue() {
					return presentationModel.getDataSetPropWithFactoryMethod();
				}
			};
			
			RefreshableItemPresentationModelFactory factory = new RefreshableItemPresentationModelFactory() {
				@Override
				public RefreshableItemPresentationModel create(Object item) {
					return new StringItemPresentationModelPOC_IPM(presentationModel.newStringItemPresentationModel());
				}
			};
			
			return new DataSetProperty(this, descriptor, new ListDataSet(factory, getSet));
		} 
		
		return null;
	}

	@Override
	public Function tryToCreateFunction(MethodDescriptor methodDescriptor) {
		if(methodDescriptor.equals(createMethodDescriptor(ON_CLICK))) {
			return new Function() {
				
				@Override
				public Object call(Object... args) {
					presentationModel.onClick();
					return null;
				}
			};
		}
		
		if(methodDescriptor.equals(createMethodDescriptor(ON_CLICK_WITH_EVENT, AbstractViewEvent.class))){
			return new Function() {
				
				@Override
				public Object call(Object... args) {
					boolean result = presentationModel.onLongClickWithEvent((AbstractViewEvent)args[0]);
					return (Boolean)result;
				}
			};
		}
		
		return null;
	}
}
