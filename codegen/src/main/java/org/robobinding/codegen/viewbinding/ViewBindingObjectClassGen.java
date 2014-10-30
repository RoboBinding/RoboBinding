package org.robobinding.codegen.viewbinding;

import java.io.IOException;

import org.robobinding.codegen.SourceCodeWritable;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingObjectClassGen implements SourceCodeWritable {
	private final ViewBindingInfo viewBindingInfo;

	private final JCodeModel codeModel;
	private final JDefinedClass definedClass;
	
	private JClass customViewBindingClass;
	protected JFieldRef customViewBindingField;
	protected JFieldRef customViewBindingFieldWithoutThis;
	
	public ViewBindingObjectClassGen(ViewBindingInfo viewBindingInfo) {
		this.viewBindingInfo = viewBindingInfo;
		
		codeModel = new JCodeModel();
		try {
			definedClass = codeModel._class(viewBindingInfo.viewBindingObjectTypeName());
			/*
			 public class MyCustomViewBinding$$VB implements org.robobinding.viewbinding.ViewBinding<ImageView> {
			 */
			JClass viewBindingInterface = codeModel.ref(ViewBinding.class).narrow(viewBindingInfo.viewType());
			definedClass._implements(viewBindingInterface);
		} catch (JClassAlreadyExistsException e) {
			throw new RuntimeException(e);
		}
		
		customViewBindingClass = codeModel.ref(viewBindingInfo.viewBindingTypeName());
	}
	
	@Override
	public void writeTo(CodeWriter output) throws IOException {
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
	public void defineSimpleOneWayPropertyClasses() throws JClassAlreadyExistsException {
		for(SimpleOneWayPropertyInfo info : viewBindingInfo.simpleOneWayPropertyInfoList()) {
			JDefinedClass definedAttributeClass = definedClass._class(JMod.PUBLIC|JMod.STATIC, info.getAttributeTypeName());
			info.setAttributeType(definedAttributeClass);
			
			JClass oneWayPropertyInterface = codeModel.ref(OneWayPropertyViewAttribute.class).narrow(
					viewBindingInfo.viewType(), info.getPropertyType());
			definedAttributeClass._implements(oneWayPropertyInterface);
			
			JMethod method = definedAttributeClass.method(JMod.PUBLIC, codeModel.VOID, "updateView");
			method.annotate(Override.class);
			JVar viewParam = method.param(viewBindingInfo.viewType(), "view");
			JVar newValueParam = method.param(info.getPropertyType(), "newValue");
			
			JBlock body = method.body();
			body.invoke(viewParam, info.getPropertySetter()).arg(newValueParam);
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
		JClass bindingAttributeMappingsType = codeModel.ref(BindingAttributeMappings.class).narrow(viewBindingInfo.viewType());
		JVar mappingsParam = method.param(bindingAttributeMappingsType, "mappings");
		
		JBlock body = method.body();
		for(SimpleOneWayPropertyInfo info : viewBindingInfo.simpleOneWayPropertyInfoList()) {
			body.invoke(mappingsParam, "mapOneWayProperty")
				.arg(info.getAttributeType().dotclass())
				.arg(info.getPropertyName());
		}
		body.invoke(customViewBindingFieldWithoutThis, "mapBindingAttributes").arg(mappingsParam);
	}
}
