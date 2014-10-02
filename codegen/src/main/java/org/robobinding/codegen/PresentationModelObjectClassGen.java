package org.robobinding.codegen;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.property.PropertyDescriptor;
import org.robobinding.property.SimpleProperty;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelObjectClassGen {
	private static final String PRESENTATION_MODEL_FIELD = "presentationModel";
	
	private JCodeModel codeModel;
	private JDefinedClass definedClass;
	private Class<?> presentationModelClass;
	private String presentationModelObjectTypeName;
	private PresentationModelInfo presentationModelInfo;
	
	private JClass setClassWithString;
	private JClass mapClassWithStringAndStringSet;
	private JClass setClassWithMethod;
	private JClass propertyDescriptorClass;
	private JClass simplePropertyClass;


	
	public PresentationModelObjectClassGen() throws JClassAlreadyExistsException {
		codeModel = new JCodeModel();
		
		setClassWithString = codeModel.ref(Set.class).narrow(String.class);
		mapClassWithStringAndStringSet = codeModel.ref(Map.class).narrow(codeModel.ref(String.class), setClassWithString);
		setClassWithMethod = codeModel.ref(Set.class).narrow(Method.class);
		propertyDescriptorClass = codeModel.ref(PropertyDescriptor.class);
		simplePropertyClass = codeModel.ref(SimpleProperty.class);

		/*
		 public class PresentationModelType_PM extends AbstractPresentationModelObject {
		 */
		definedClass = codeModel._class(presentationModelObjectTypeName);
		definedClass._extends(AbstractPresentationModelObject.class);
	}
	
	public void writeTo(CodeWriter output) throws IOException {
		codeModel.build(output);
	}
	
	/**
	 *	private final PresentationModelType presentationModel;
	 */
	public void defineFields() {
		definedClass.field(JMod.PRIVATE + JMod.FINAL, presentationModelClass, PRESENTATION_MODEL_FIELD);
	}
	
	/**
	 * 1.When PresentationModelType implements {@link HasPresentationModelChangeSupport}
	 * 
	 * 	public PresentationModelType_PM(PresentationModelType presentationModel) {
	 *		super(PresentationModelType.class, presentationModel.getPresentationModelChangeSupport());
	 *		this.presentationModel = presentationModel;
	 *	}
	 *
	 * 2.When PresentationModelType NOT implements {@link HasPresentationModelChangeSupport}
	 * 
	 * 	public PresentationModelType_PM(PresentationModelType presentationModel) {
	 *		super(PresentationModelType.class, new PresentationModelChangeSupport(presentationModel));
	 *		this.presentationModel = presentationModel;
	 *	}
	 *
	 */
	public void defineConstructor() {
		JMethod constructor = definedClass.constructor(JMod.PUBLIC);
		constructor.param(presentationModelClass, PRESENTATION_MODEL_FIELD);
		
		JBlock block = constructor.body();
		
		JInvocation superInvocation = JExpr.invoke("super");
		superInvocation.arg(JExpr.dotclass(codeModel.ref(presentationModelClass)));
		if (HasPresentationModelChangeSupport.class.isAssignableFrom(presentationModelClass)) {
			superInvocation.arg(JExpr.ref(PRESENTATION_MODEL_FIELD).invoke("getPresentationModelChangeSupport"));
		} else {
			superInvocation.arg(JExpr._new(codeModel.ref(PresentationModelChangeSupport.class))
					.arg(JExpr.ref(PRESENTATION_MODEL_FIELD)));
		}
		block.add(superInvocation);
		
		block.assign(JExpr._this().ref(PRESENTATION_MODEL_FIELD), JExpr.ref(PRESENTATION_MODEL_FIELD));
	}
	/**
	 * 	@Override
	 *	public Set<String> propertyNames() {
	 *		return Sets.newArraySet("prop1", "prop2", ...);
	 *	}

	 */
	public void definePropertyNames() {
		JMethod method = declarePublicMethodOverride("propertyNames", setClassWithString);
		
		method.body()._return(newHashSetInvocation(presentationModelInfo.propertyNames()));
	}
	
	private JMethod declarePublicMethodOverride(String methodName, JClass returnType) {
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
	/**
	 * 	@Override
	 *	public Set<String> dataSetPropertyNames() {
	 *		return Sets.newArraySet("dataSetProp1", "dataSetProp2");
	 *	}
	 *
	 */
	public void defineDataSetPropertyNames() {
		JMethod method = declarePublicMethodOverride("dataSetPropertyNames", setClassWithString);

		method.body()._return(newHashSetInvocation(presentationModelInfo.dataSetPropertyNames()));
	}
	/**
	 * @Override
	 * public Map<String, Set<String>> propertyDependencies() {
	 *		Map<String, Set<String>> dependencies = Maps.newHashMap();
	 *		dependencies.put(PROP1, Sets.newHashSet(PROP2));
	 *		return dependencies;
	 *	}
	 *
	 */
	public void definePropertyDependencies() {
		JMethod method = declarePublicMethodOverride("dataSetPropertyNames", mapClassWithStringAndStringSet);
		
		JBlock body = method.body();
		
		JVar dependenciesVar = body.decl(mapClassWithStringAndStringSet, "dependencies", 
				codeModel.ref(Maps.class).staticInvoke("newHashMap"));
		
		for(PropertyDependencyInfo propertyDependencyInfo : presentationModelInfo.propertyDependencies()) {
			dependenciesVar.invoke("put")
				.arg(propertyDependencyInfo.property())
				.arg(newHashSetInvocation(propertyDependencyInfo.dependentProperties()));
		}
		
		body._return(dependenciesVar);
	}
	/**
	 * 	@Override
	 *	public Set<Method> eventMethods() {
	 *		return Sets.newHashSet(
	 *			getMethod(ON_CLICK),
	 *			getMethod(ON_CLICK_WITH_EVENT, AbstractViewEvent.class));
	 *	}
	 *
	 */
	public void defineEventMethods() {
		JMethod method = declarePublicMethodOverride("eventMethods", setClassWithMethod);
		
		JInvocation invocation = codeModel.ref(Sets.class).staticInvoke("newHashSet");
		for(EventMethodInfo eventMethod : presentationModelInfo.eventMethods()) {
			JInvocation getMethodInvocation = JExpr.invoke("getMethod").arg(method.name());
			if(eventMethod.hasArg()) {
				getMethodInvocation.arg(codeModel.ref(eventMethod.argType()).dotclass());
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
	 */
	public void defineTryToCreateProperty() {
		JMethod method = declarePublicMethodOverride("tryToCreateProperty", SimpleProperty.class);
		JVar nameParameter = method.param(String.class, "name");
		
		JBlock body = method.body();
		
		for(PropertyInfo propertyInfo : presentationModelInfo.properties()) {
			JConditional conditional = body._if(nameParameter.invoke("equals").arg(propertyInfo.name()));
			JBlock propertyCreation = conditional._then();
			
			JInvocation createPropertyDescriptorInvocation = JExpr.invoke("createPropertyDescriptor")
					.arg(propertyInfo.typeName())
					.arg(propertyInfo.name())
					.arg(JExpr.lit(propertyInfo.isReadable()))
					.arg(JExpr.lit(propertyInfo.isWritable()));
			JVar descriptorVar = propertyCreation.decl(propertyDescriptorClass, "descriptor", createPropertyDescriptorInvocation);
			
			body._return(JExpr._new(simplePropertyClass).arg(JExpr._this()).arg(descriptorVar));
		}
		
		body._return(JExpr._null());
	}
	
	private JMethod declarePublicMethodOverride(String methodName, Class<?> returnType) {
		JMethod method = definedClass.method(JMod.PUBLIC, returnType, methodName);
		method.annotate(Override.class);
		return method;
	}
}
