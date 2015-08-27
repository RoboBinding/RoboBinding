package org.robobinding.test;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.function.Function;
import org.robobinding.function.MethodDescriptor;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.property.AbstractGetSet;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.ListDataSet;
import org.robobinding.property.SimpleProperty;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class JavaReflectionPresentationModelObject extends AbstractPresentationModelObject {
	private final Object presentationModel;
	public JavaReflectionPresentationModelObject(Object presentationModel) {
		super(presentationModel);
		
		this.presentationModel = presentationModel;
	}
	
	@Override
	public Set<String> propertyNames() {
		List<PropertyDescriptor> descriptors = findPropertiesByAnnotation(ItemPresentationModel.class, false);
		return propertyNames(descriptors); 
	}
	
	private List<PropertyDescriptor> findPropertiesByAnnotation(Class<? extends Annotation> annotationType, boolean withAnnotation) {
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(presentationModel.getClass());
		List<PropertyDescriptor> result = Lists.newArrayList();
		for(PropertyDescriptor descriptor : descriptors) {
			if(isReadable(descriptor) 
					&& hasAnnotation(descriptor, annotationType)
					&& withAnnotation) {
				result.add(descriptor);
			} else if(!withAnnotation) {
				result.add(descriptor);
			}
		}
		return result;
	}
	
	private boolean isReadable(PropertyDescriptor descriptor) {
		return descriptor.getReadMethod() != null;
	}
	
	private boolean hasAnnotation(PropertyDescriptor descriptor, Class<? extends Annotation> annotationType) {
		Method readMethod = descriptor.getReadMethod();
		return readMethod.getAnnotation(annotationType) != null;
	}
	
	private Set<String> propertyNames(Collection<PropertyDescriptor> descriptors) {
		Set<String> names = Sets.newHashSet();
		for(PropertyDescriptor descriptor : descriptors) {
			names.add(descriptor.getName());
		}
		return names;
	}

	@Override
	public Set<String> dataSetPropertyNames() {
		List<PropertyDescriptor> descriptors = findPropertiesByAnnotation(ItemPresentationModel.class, true);
		return propertyNames(descriptors); 
	}

	@Override
	public Map<String, Set<String>> propertyDependencies() {
		List<PropertyDescriptor> descriptors = findPropertiesByAnnotation(DependsOnStateOf.class, true);
		Map<String, Set<String>> dependencies = Maps.newHashMap();
		for(PropertyDescriptor descriptor : descriptors) {
			DependsOnStateOf dependsOnStateOf = getAnnotation(descriptor, DependsOnStateOf.class);
			dependencies.put(descriptor.getName(), Sets.newHashSet(dependsOnStateOf.value()));
		}
		return dependencies;
	}
	
	private <A extends Annotation> A getAnnotation(PropertyDescriptor descriptor, Class<A> annotationType) {
		Method readMethod = descriptor.getReadMethod();
		return readMethod.getAnnotation(annotationType);
	}

	@Override
	public Set<MethodDescriptor> eventMethods() {
		Set<MethodDescriptor> descriptors = Sets.newHashSet();
		for(Method method : presentationModel.getClass().getMethods()) {
			if(isEventMethod(method)) {
				descriptors.add(new MethodDescriptor(method.getName(), method.getParameterTypes()));
			}
		}
		return descriptors;
	}

	private boolean isEventMethod(Method method) {
		if((method.getParameterTypes().length > 1)
				|| StringUtils.startsWithAny(method.getName(), "get", "is", "set")){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public SimpleProperty tryToCreateProperty(final String propertyName) {
		Set<String> propertyNames = propertyNames();
		if(propertyNames.contains(propertyName)) {
			org.robobinding.property.PropertyDescriptor descriptor = createPropertyDescriptor(propertyName);
			AbstractGetSet<?> getSet = new AbstractGetSet<Object>(descriptor) {
				@Override
				public Object getValue() {
					return getProperty(propertyName);
				}
				@Override
				public void setValue(Object newValue) {
					setProperty(propertyName, newValue);
				}
			};
			return new SimpleProperty(this, descriptor, getSet);
		}
		
		return null;
	}

	private org.robobinding.property.PropertyDescriptor createPropertyDescriptor(String propertyName) {
		PropertyDescriptor descriptor = getPropertyDescriptor(propertyName);
		return createPropertyDescriptor(descriptor.getPropertyType(), propertyName, isReadable(descriptor), isWritable(descriptor));
	}

	private PropertyDescriptor getPropertyDescriptor(String propertyName) {
		try {
			return PropertyUtils.getPropertyDescriptor(presentationModel, propertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean isWritable(PropertyDescriptor descriptor) {
		Method writeMethod = descriptor.getWriteMethod();
		return writeMethod != null;
	}

	private Object getProperty(String propertyName) {
		try {
			return PropertyUtils.getProperty(presentationModel, propertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setProperty(String propertyName, Object newValue) {
		try {
			PropertyUtils.setProperty(presentationModel, propertyName, newValue);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DataSetProperty tryToCreateDataSetProperty(final String propertyName) {
		Set<String> dataSetPropertyNames = dataSetPropertyNames();
		if(dataSetPropertyNames.contains(propertyName)) {
			org.robobinding.property.PropertyDescriptor descriptor = createDataSetPropertyDescriptor(
					getPropertyDescriptor(propertyName).getPropertyType(), propertyName);
			AbstractGetSet<?> getSet = new AbstractGetSet<Object>(descriptor) {
				@Override
				public Object getValue() {
					return getProperty(propertyName);
				}
			};
			
			RefreshableItemPresentationModelFactory factory = new RefreshableItemPresentationModelFactory() {
				
				@Override
				public RefreshableItemPresentationModel create(Object item) {
					throw new UnsupportedOperationException();
				}
			};
			return new DataSetProperty(this, descriptor, new ListDataSet(factory, getSet));
		}

		return null;
	}

	@Override
	public Function tryToCreateFunction(final MethodDescriptor methodDescriptor) {
		Set<MethodDescriptor> eventMethods = eventMethods();
		if(eventMethods.contains(methodDescriptor)) {
			return new Function() {
				
				@Override
				public Object call(Object... args) {
					Method method = MethodUtils.getAccessibleMethod(presentationModel.getClass(), methodDescriptor.getName(), methodDescriptor.getParameterTypes());
					try {
						return method.invoke(presentationModel, args);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}
			};
		}
		return null;
	}

}
