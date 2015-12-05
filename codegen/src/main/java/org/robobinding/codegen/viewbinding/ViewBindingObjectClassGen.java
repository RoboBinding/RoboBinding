package org.robobinding.codegen.viewbinding;

import java.io.IOException;
import java.util.List;

import org.robobinding.codegen.SourceCodeWritable;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JFieldRef;
import com.helger.jcodemodel.JFieldVar;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingObjectClassGen implements SourceCodeWritable {
	private final List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList;
	private final List<TwoWayPropertyInfo> twoWayPropertyInfoList;

	private final JCodeModel codeModel;
	private final JDefinedClass definedClass;
	
	private AbstractJClass customViewBindingClass;
	private AbstractJClass viewClass;
	protected JFieldRef customViewBindingField;
	protected JFieldRef customViewBindingFieldWithoutThis;
	
	public ViewBindingObjectClassGen(ViewBindingInfo viewBindingInfo) {
		this.simpleOneWayPropertyInfoList = viewBindingInfo.simpleOneWayPropertyInfoList();
		this.twoWayPropertyInfoList = viewBindingInfo.twoWayPropertyInfoList();
		
		codeModel = new JCodeModel();
		try {
			definedClass = codeModel._class(viewBindingInfo.viewBindingObjectTypeName());
			/*
			 public class MyCustomViewBinding$$VB implements org.robobinding.viewbinding.ViewBinding<ImageView> {
			 */
			viewClass = codeModel.ref(viewBindingInfo.viewType());
			AbstractJClass viewBindingInterface = codeModel.ref(ViewBinding.class).narrow(viewClass);
			definedClass._implements(viewBindingInterface);
		} catch (JClassAlreadyExistsException e) {
			throw new RuntimeException(e);
		}
		
		customViewBindingClass = codeModel.ref(viewBindingInfo.viewBindingTypeName());
	}
	
	@Override
	public void writeTo(AbstractCodeWriter output) throws IOException {
		codeModel.build(output);
	}
	
	/**
	 * Note: not private in order to improve performance.
	 * 
	 * final CustomViewBindingType customViewBinding;
	 */
	public void defineFields() {
		JFieldVar var = definedClass.field(JMod.FINAL, customViewBindingClass, "customViewBinding");
		customViewBindingField = JExpr.refthis(var.name());
		customViewBindingFieldWithoutThis = JExpr.ref(var.name());
	}
	
	/**
	 * 
    	public MyCustomViewBinding$$VB(MyCustomViewBinding customViewBinding) {
			this.customViewBinding = customViewBinding;
		}
	 *
	 */
	public void defineConstructor() {
		JMethod constructor = definedClass.constructor(JMod.PUBLIC);
		JVar customViewBindingParam = constructor.param(customViewBindingClass, "customViewBinding");
		
		JBlock block = constructor.body();
		block.assign(customViewBindingField, customViewBindingParam);

	}

	/**
	 * 
    	private static class ImageAlphaAttribute implements OneWayPropertyViewAttribute<ImageView, Integer> {
    		@Override
    		public void updateView(ImageView view, Integer newValue) {
    			view.setImageAlpha(newValue);
    		}
    	}
     *
	 */
	public void defineSimpleOneWayPropertyClasses() {
		for(SimpleOneWayPropertyInfo propInfo : simpleOneWayPropertyInfoList) {
			JDefinedClass definedBindingAttributeClass = propInfo.defineBindingClass(new ClassDefinitionCallback() {
				@Override
				public JDefinedClass define(String typeName) {
					try{
						return definedClass._class(JMod.PUBLIC|JMod.STATIC, typeName);
					}catch(JClassAlreadyExistsException e) {
						throw new RuntimeException("Class '"+typeName+"' already exists", e);
					}
				}
			});

			
			AbstractJClass propertyClass = codeModel.ref(propInfo.propertyType());
			AbstractJClass oneWayPropertyInterface = codeModel.ref(OneWayPropertyViewAttribute.class).narrow(
					viewClass, propertyClass);
			definedBindingAttributeClass._implements(oneWayPropertyInterface);
			
			JMethod method = definedBindingAttributeClass.method(JMod.PUBLIC, codeModel.VOID, "updateView");
			method.annotate(Override.class);
			JVar viewParam = method.param(viewClass, "view");
			JVar newValueParam = method.param(propertyClass, "newValue");
			
			JBlock body = method.body();
			body.invoke(viewParam, propInfo.propertySetter()).arg(newValueParam);
		}
	}
	
	/**
	 * 
    	@Override
    	public void mapBindingAttributes(BindingAttributeMappings<ImageView> mappings) {
    		mappings.mapOneWayProperty(ImageAlphaAttribute.class, "imageAlpha");
    		mappings.mapOneWayProperty(MaxWidthAttribute.class, "maxWidth");
    		mappings.mapOneWayProperty(MaxHeightAttribute.class, "maxHeight");
    		
    		customViewBinding.mapBindingAttributes(mappings);
    	}
	 * 
	 */
	public void defineMapBindingAttributesMethod() {
		JMethod method = definedClass.method(JMod.PUBLIC, codeModel.VOID, "mapBindingAttributes");
		method.annotate(Override.class);
		AbstractJClass bindingAttributeMappingsType = codeModel.ref(BindingAttributeMappings.class).narrow(viewClass);
		JVar mappingsParam = method.param(bindingAttributeMappingsType, "mappings");
		
		JBlock body = method.body();
		for(SimpleOneWayPropertyInfo info : simpleOneWayPropertyInfoList) {
			body.invoke(mappingsParam, "mapOneWayProperty")
				.arg(info.getBindingClass().dotclass())
				.arg(info.propertyName());
		}
		for (TwoWayPropertyInfo info : twoWayPropertyInfoList) {
			AbstractJClass propertyClass = codeModel.ref(info.propertyTypeName());
			body.invoke(mappingsParam, "mapTwoWayProperty")
				.arg(propertyClass.dotclass())
				.arg(info.propertyName());
		}
		body.invoke(customViewBindingFieldWithoutThis, "mapBindingAttributes").arg(mappingsParam);
	}
}
