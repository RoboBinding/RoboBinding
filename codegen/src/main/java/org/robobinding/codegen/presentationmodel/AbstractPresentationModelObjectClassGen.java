package org.robobinding.codegen.presentationmodel;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.robobinding.binder.ItemPresentationModelObjectLoader;
import org.robobinding.codegen.SourceCodeWritable;
import org.robobinding.function.Function;
import org.robobinding.function.MethodDescriptor;
import org.robobinding.itempresentationmodel.ItemViewFactory;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;
import org.robobinding.property.AbstractGetSet;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.PropertyDescriptor;
import org.robobinding.property.SimpleProperty;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JConditional;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldRef;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractPresentationModelObjectClassGen implements SourceCodeWritable {
	protected final PresentationModelInfo presentationModelInfo;

	protected final JCodeModel codeModel;
	protected final JDefinedClass definedClass;
	protected JFieldRef presentationModelField;
	protected JFieldRef presentationModelFieldWithoutThis;
	
	private AbstractJClass setClassWithString;
	private AbstractJClass mapClassWithStringAndStringSet;
	private AbstractJClass setClassWithMethodDescriptor;
	private AbstractJClass propertyDescriptorClass;
	private AbstractJClass simplePropertyClass;
	private AbstractJClass getSetClass;
	private AbstractJClass wildcardGetSetClass;
	private AbstractJClass dataSetPropertyClass;
	private AbstractJClass refreshableItemPresentationModelFactoryClass;
	private AbstractJClass itemViewFactoryClass;
	private AbstractJClass objectClass;
	private AbstractJClass itemPresentationModelObjectLoaderClass;

	public AbstractPresentationModelObjectClassGen(PresentationModelInfo presentationModelInfo) {
		this.presentationModelInfo = presentationModelInfo;
		
		codeModel = new JCodeModel();
		
		setClassWithString = codeModel.ref(Set.class).narrow(String.class);
		mapClassWithStringAndStringSet = codeModel.ref(Map.class).narrow(codeModel.ref(String.class), setClassWithString);
		setClassWithMethodDescriptor = codeModel.ref(Set.class).narrow(MethodDescriptor.class);
		propertyDescriptorClass = codeModel.ref(PropertyDescriptor.class);
		simplePropertyClass = codeModel.ref(SimpleProperty.class);
		getSetClass = codeModel.ref(AbstractGetSet.class);
		wildcardGetSetClass = getSetClass.narrow(codeModel.wildcard());
		dataSetPropertyClass = codeModel.ref(DataSetProperty.class);
		refreshableItemPresentationModelFactoryClass = codeModel.ref(RefreshableItemPresentationModelFactory.class);
		itemViewFactoryClass = codeModel.ref(ItemViewFactory.class);
		objectClass = codeModel.ref(Object.class);
		itemPresentationModelObjectLoaderClass = codeModel.ref(ItemPresentationModelObjectLoader.class);

		try {
			definedClass = codeModel._class(presentationModelInfo.getPresentationModelObjectTypeName());
		} catch (JClassAlreadyExistsException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void writeTo(AbstractCodeWriter output) throws IOException {
		codeModel.build(output);
	}
	
	public abstract void defineFields();
	
	public abstract void defineConstructor();
	/*
	 	@Override
	 	public Set<String> propertyNames() {
	 		return Sets.newHasSet("prop1", "prop2", ...);
	 	}

	 */
	public void definePropertyNames() {
		JMethod method = declarePublicMethodOverride("propertyNames", setClassWithString);
		
		method.body()._return(newHashSetInvocation(presentationModelInfo.propertyNames()));
	}
	
	private JMethod declarePublicMethodOverride(String methodName, AbstractJClass returnType) {
		return declarePublicMethodOverride(definedClass, methodName, returnType);
	}
	
	private static JMethod declarePublicMethodOverride(JDefinedClass definedClass, String methodName, AbstractJClass returnType) {
		JMethod method = definedClass.method(JMod.PUBLIC, returnType, methodName);
		method.annotate(Override.class);
		return method;
	}
	
	private JInvocation newHashSetInvocation(Set<String> stringSet) {
		JInvocation invocation = codeModel.ref(Sets.class).staticInvoke("newHashSet");
		for(String str : stringSet) {
			invocation.arg(str);
		}
		return invocation;
	}
	/*
	  	@Override
	 	public Set<String> dataSetPropertyNames() {
	 		return Sets.newHasSet("dataSetProp1", "dataSetProp2");
	 	}
	 
	 */
	public void defineDataSetPropertyNames() {
		JMethod method = declarePublicMethodOverride("dataSetPropertyNames", setClassWithString);

		method.body()._return(newHashSetInvocation(presentationModelInfo.dataSetPropertyNames()));
	}
	/*
	  	@Override
	  	public Map<String, Set<String>> propertyDependencies() {
	 		Map<String, Set<String>> dependencies = Maps.newHashMap();
	 		dependencies.put(PROP1, Sets.newHashSet(PROP2));
	 		return dependencies;
	 	}
	 
	 */
	public void definePropertyDependencies() {
		JMethod method = declarePublicMethodOverride("propertyDependencies", mapClassWithStringAndStringSet);
		
		JBlock body = method.body();
		
		JVar dependenciesVar = body.decl(mapClassWithStringAndStringSet, "dependencies",
				codeModel.ref(Maps.class).staticInvoke("newHashMap"));
		
		for(PropertyDependencyInfo propertyDependencyInfo : presentationModelInfo.propertyDependencies()) {
			body.add(dependenciesVar.invoke("put")
					.arg(propertyDependencyInfo.property())
					.arg(newHashSetInvocation(propertyDependencyInfo.dependentProperties())));
		}
		
		body._return(dependenciesVar);
	}
	/*
	  	@Override
	 	public Set<MethodDescriptor> eventMethods() {
	 		return Sets.newHashSet(
	 			createMethodDescriptor(ON_CLICK),
	 			createMethodDescriptor(ON_CLICK_WITH_EVENT, AbstractViewEvent.class));
	 	}
	 
	 */
	public void defineEventMethods() {
		JMethod method = declarePublicMethodOverride("eventMethods", setClassWithMethodDescriptor);
		
		JInvocation invocation = codeModel.ref(Sets.class).staticInvoke("newHashSet");
		for(EventMethodInfo eventMethod : presentationModelInfo.eventMethods()) {
			JInvocation getMethodInvocation = JExpr.invoke("createMethodDescriptor").arg(eventMethod.name());
			if(eventMethod.hasEventArg()) {
				getMethodInvocation.arg(codeModel.ref(eventMethod.eventArgType()).dotclass());
			}
			invocation.arg(getMethodInvocation);
		}

		method.body()._return(invocation);
	}
	/*
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
	 */
	public void defineTryToCreateProperty() {
		try{
		
		JMethod method = declarePublicMethodOverride("tryToCreateProperty", SimpleProperty.class);
		JVar nameParam = method.param(String.class, "name");
		
		JBlock body = method.body();
		
		for(PropertyInfo propertyInfo : presentationModelInfo.properties()) {
			JConditional conditional = body._if(nameParam.invoke("equals").arg(propertyInfo.name()));
			JBlock conditionalBody = conditional._then();
			//create PropertyDescriptor.
			AbstractJClass propertyClass = codeModel.ref(propertyInfo.typeName());
			
			JInvocation createPropertyDescriptor = JExpr.invoke("createPropertyDescriptor")
					.arg(propertyClass.dotclass())
					.arg(nameParam)
					.arg(JExpr.lit(propertyInfo.isReadable()))
					.arg(JExpr.lit(propertyInfo.isWritable()));
			JVar descriptorVar = conditionalBody.decl(propertyDescriptorClass, "descriptor", createPropertyDescriptor);
			//create AbstractGetSet.
			//JClass narrowedGetSet = getSetClass.narrow(codeModel.ref(propertyInfo.typeName()));
			AbstractJClass narrowedGetSet = getSetClass.narrow(propertyClass);
			JDefinedClass anonymousGetSet = codeModel.anonymousClass(narrowedGetSet);
			
			if(propertyInfo.isReadable()) {
				JMethod getter = declarePublicMethodOverride(anonymousGetSet, "getValue", propertyClass);
				getter.body()._return(presentationModelFieldWithoutThis.invoke(propertyInfo.getter()));
			}
			
			if(propertyInfo.isWritable()) {
				JMethod setter = declarePublicMethodOverride(anonymousGetSet, "setValue", Void.TYPE);
				JVar newValueParam = setter.param(propertyClass, "newValue");
				setter.body().add(presentationModelFieldWithoutThis.invoke(propertyInfo.setter()).arg(newValueParam));
			}
			JVar getSetVar = conditionalBody.decl(wildcardGetSetClass, "getSet", 
					JExpr._new(anonymousGetSet).arg(descriptorVar));
			//return SimpleProperty.
			conditionalBody._return(JExpr._new(simplePropertyClass).arg(JExpr._this()).arg(descriptorVar).arg(getSetVar));
		}
		
		body._return(JExpr._null());
		
		} catch(Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	private JMethod declarePublicMethodOverride(String methodName, Class<?> returnType) {
		return declarePublicMethodOverride(definedClass, methodName, returnType);
	}
	
	private static JMethod declarePublicMethodOverride(JDefinedClass definedClass, String methodName, Class<?> returnType) {
		JMethod method = definedClass.method(JMod.PUBLIC, returnType, methodName);
		method.annotate(Override.class);
		return method;
	}
	/*
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
				public RefreshableItemPresentationModel create() {
					return new StringItemPresentationModel_IPM(new StringItemPresentationModel());
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
				public RefreshableItemPresentationModel create() {
					return new StringItemPresentationModel_IPM(presentationModel.newStringItemPresentationModel());
				}
			};
			
			return new DataSetProperty(this, descriptor, new ListDataSet(factory, getSet));
		} 
		
		return null;
	}
	 */
	public void defineTryToCreateDataSetProperty() {
		JMethod method = declarePublicMethodOverride("tryToCreateDataSetProperty", DataSetProperty.class);
		JVar nameParam = method.param(String.class, "name");
		
		JBlock body = method.body();
		
		for(DataSetPropertyInfo propertyInfo : presentationModelInfo.dataSetProperties()) {
			JConditional conditional = body._if(nameParam.invoke("equals").arg(propertyInfo.name()));
			JBlock conditionalBody = conditional._then();
			//create createDataSetPropertyDescriptor.
			AbstractJClass propertyClass = codeModel.ref(propertyInfo.type());
			JInvocation createDataSetPropertyDescriptor = JExpr.invoke("createDataSetPropertyDescriptor")
					.arg(propertyClass.dotclass())
					.arg(nameParam);
			
			JVar descriptorVar = conditionalBody.decl(propertyDescriptorClass, "descriptor", createDataSetPropertyDescriptor);
			//create AbstractGetSet.
			AbstractJClass narrowedGetSet = getSetClass.narrow(codeModel.ref(propertyInfo.type()));
			JDefinedClass anonymousGetSet = codeModel.anonymousClass(narrowedGetSet);
			
			JMethod getter = declarePublicMethodOverride(anonymousGetSet, "getValue", propertyClass);
			getter.body()._return(presentationModelFieldWithoutThis.invoke(propertyInfo.getter()));
			
			JVar getSetVar = conditionalBody.decl(wildcardGetSetClass, "getSet", 
					JExpr._new(anonymousGetSet).arg(descriptorVar));
			//JVar getSetVar = conditionalBody.decl(getSetClass, "getSet", 
			//		JExpr._new(anonymousGetSet.narrow(propertyClass)).arg(descriptorVar));
			//create RefreshableItemPresentationModelFactory.
			JDefinedClass anonymousFactory = codeModel.anonymousClass(RefreshableItemPresentationModelFactory.class);
			
			JMethod create = declarePublicMethodOverride(anonymousFactory, "create", RefreshableItemPresentationModel.class);
			JVar item = create.param(objectClass, "item");

			AbstractJClass itemPresentationModelObjectClass = codeModel.ref(propertyInfo.itemPresentationModelObjectTypeName());
			JInvocation newItemPresentationModelObject;
			if (propertyInfo.isCreatedByFactoryMethod() && !propertyInfo.itemPresentationModelTypeName().equals(propertyInfo.factoryMethodReturnTypeName())) {
				newItemPresentationModelObject = itemPresentationModelObjectLoaderClass.staticInvoke("load");
			} else {
				newItemPresentationModelObject = JExpr._new(itemPresentationModelObjectClass);
			}
			if (propertyInfo.isCreatedByFactoryMethod()) {
				JInvocation callFactotyMethod = presentationModelFieldWithoutThis.invoke(propertyInfo.factoryMethod());
				if(propertyInfo.factoryMethodHasArg()) {
					callFactotyMethod.arg(item);
				}
				newItemPresentationModelObject.arg(callFactotyMethod);
			} else {
				newItemPresentationModelObject.arg(JExpr._new(codeModel.ref(propertyInfo.itemPresentationModelTypeName())));
			}
			create.body()._return(newItemPresentationModelObject);
			
			JVar factoryVar = conditionalBody.decl(refreshableItemPresentationModelFactoryClass, "factory", JExpr._new(anonymousFactory));

			String viewFactoryTypeName = propertyInfo.itemViewFactoryTypeName();
			JVar viewFactoryVar = null;
			if (viewFactoryTypeName != null && !viewFactoryTypeName.isEmpty()) {
				viewFactoryVar = conditionalBody.decl(itemViewFactoryClass, "viewFactory", JExpr._new(codeModel.ref(viewFactoryTypeName)));
			}
			//return DataSetProperty.
			if (viewFactoryVar == null) {
				conditionalBody._return(JExpr._new(dataSetPropertyClass)
						.arg(JExpr._this())
						.arg(descriptorVar)
						.arg(JExpr._new(codeModel.ref(propertyInfo.dataSetImplementationType())).arg(factoryVar).arg(getSetVar)));
			} else {
				conditionalBody._return(JExpr._new(dataSetPropertyClass)
						.arg(JExpr._this())
						.arg(descriptorVar)
						.arg(JExpr._new(codeModel.ref(propertyInfo.dataSetImplementationType())).arg(factoryVar).arg(viewFactoryVar).arg(getSetVar)));
			}
		}
		
		body._return(JExpr._null());
	}
	/*
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
	 */
	public void defineTryToCreateFunction() {
		JMethod method = declarePublicMethodOverride("tryToCreateFunction", Function.class);
		JVar methodDescriptorParam = method.param(MethodDescriptor.class, "methodDescriptor");
		
		JBlock body = method.body();
		
		for(EventMethodInfo eventMethodInfo : presentationModelInfo.eventMethods()) {
			JInvocation getMethod = JExpr.invoke("createMethodDescriptor").arg(eventMethodInfo.name());
			if(eventMethodInfo.hasEventArg()) {
				getMethod.arg(codeModel.ref(eventMethodInfo.eventArgType()).dotclass());
			}
			JConditional conditional = body._if(methodDescriptorParam.invoke("equals").arg(getMethod));
			JBlock conditionalBody = conditional._then();
			//create Function.
			JDefinedClass anonymousFunction = codeModel.anonymousClass(Function.class);
			
			JMethod call = declarePublicMethodOverride(anonymousFunction, "call", Object.class);
			JVar argsVar = call.varParam(Object.class, "args");
			JBlock callBody = call.body();
			//call event method.
			JInvocation onEvent = presentationModelFieldWithoutThis.invoke(eventMethodInfo.name());
			if(eventMethodInfo.hasEventArg()) {
				AbstractJClass eventArgClass = codeModel.ref(eventMethodInfo.eventArgType());
				onEvent.arg(JExpr.cast(eventArgClass, argsVar.component(JExpr.lit(0))));
			}
			//call return.
			if(eventMethodInfo.hasReturn()) {
				JVar returnVar = callBody.decl(codeModel.ref(eventMethodInfo.nonPrimitiveReturnType()), "result", onEvent);
				callBody._return(returnVar);
			} else {
				callBody.add(onEvent);
				callBody._return(JExpr._null());
			}
			//return Function.
			conditionalBody._return(JExpr._new(anonymousFunction));
		}
		
		body._return(JExpr._null());
	}
}
