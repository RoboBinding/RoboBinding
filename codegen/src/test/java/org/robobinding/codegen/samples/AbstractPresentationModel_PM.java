package org.robobinding.codegen.samples;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robobinding.function.Function;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
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
public class AbstractPresentationModel_PM extends AbstractPresentationModelObject {
	private final AbstractPresentationModel presentationModel;
	
	private static final String PROP1 = "prop1";
	private static final String PROP2 = "prop2";
	private static final String DATA_SET_PROP = "dataSetProp";
	private static final String DATA_SET_PROP_WITH_FACTORY_METHOD = "dataSetPropWithFactoryMethod";
	private static final String ON_CLICK = "onClick";
	private static final String ON_CLICK_WITH_EVENT = "onClickWithEvent";

	
	public AbstractPresentationModel_PM(AbstractPresentationModel presentationModel, PresentationModelChangeSupport changeSupport) {
		super(presentationModel.getClass(), changeSupport);
		
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
	public Set<Method> eventMethods() {
		return Sets.newHashSet(
				getMethod(ON_CLICK),
				getMethod(ON_CLICK_WITH_EVENT, AbstractViewEvent.class));
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
		} else if(name.equals(PROP2)) {
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
		} else{
			return null;
		}
	}

	@Override
	public DataSetProperty tryToCreateDataSetProperty(String name) {
		if(name.equals(DATA_SET_PROP)) {
			PropertyDescriptor descriptor = createDataSetPropertyDescriptor(List.class, name);
			
			RefreshableItemPresentationModelFactory factory = new RefreshableItemPresentationModelFactory() {
				
				@Override
				public RefreshableItemPresentationModel create() {
					StringItemPresentationModel itemPresentationModel = new StringItemPresentationModel();
					return new StringItemPresentationModel_IPM(itemPresentationModel, 
							new PresentationModelChangeSupport(itemPresentationModel));
				}
			};	
			
			AbstractGetSet<?> getSet = new AbstractGetSet<List<String>>(descriptor) {
				@Override
				public List<String> getValue() {
					return presentationModel.getDataSetProp();
				}
			};
			
			return new DataSetProperty(this, descriptor, new ListDataSet(factory, getSet));
		} else if(name.equals(DATA_SET_PROP_WITH_FACTORY_METHOD)) {
			PropertyDescriptor descriptor = createDataSetPropertyDescriptor(List.class, name);
			
			RefreshableItemPresentationModelFactory factory = new RefreshableItemPresentationModelFactory() {
				@Override
				public RefreshableItemPresentationModel create() {
					StringItemPresentationModel itemPresentationModel = presentationModel.newStringItemPresentationModel();
					return new StringItemPresentationModel_IPM(itemPresentationModel, 
							new PresentationModelChangeSupport(itemPresentationModel));
				}
			};
			
			AbstractGetSet<?> getSet = new AbstractGetSet<List<String>>(descriptor) {
				@Override
				public List<String> getValue() {
					return presentationModel.getDataSetPropWithFactoryMethod();
				}
			};
			
			return new DataSetProperty(this, descriptor, new ListDataSet(factory, getSet));
		} else {
			return null;
		}
	}

	@Override
	public Function tryToCreateFunction(Method method) {
		if(method.equals(getMethod(ON_CLICK))) {
			return new Function() {
				
				@Override
				public Object call(Object... args) {
					presentationModel.onClick();
					return null;
				}
			};
		} else if(method.equals(getMethod(ON_CLICK_WITH_EVENT, AbstractViewEvent.class))){
			return new Function() {
				
				@Override
				public Object call(Object... args) {
					boolean result = presentationModel.onLongClickWithEvent((AbstractViewEvent)args[0]);
					return Boolean.valueOf(result);
				}
			};
		} else {
			return null;
		}
	}
}
