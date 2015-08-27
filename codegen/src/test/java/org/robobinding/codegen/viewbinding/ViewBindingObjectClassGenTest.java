package org.robobinding.codegen.viewbinding;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.robobinding.codegen.SourceCodeAssert;
import org.robobinding.codegen.apt.MethodElementFilter;
import org.robobinding.codegen.apt.element.AptTestHelper;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.apt.element.SetterElement;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.customviewbinding.CustomViewBinding;

import android.widget.ImageView;

import com.google.common.collect.Lists;
import com.google.testing.compile.CompilationRule;
import com.helger.jcodemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingObjectClassGenTest {
	@Rule public final CompilationRule compilation = new CompilationRule();
	private AptTestHelper aptTestHelper;
	
	@Before
	public void setUp() {
		aptTestHelper = new AptTestHelper(compilation);
	}
	
	@Test
	public void shouldDefineConstructor() {
		ViewBindingInfo info = createViewBindingInfo(DefineConstructor.class);
		ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
		gen.defineFields();
		gen.defineConstructor();

		assertOutputSameTextFile(gen, "DefineConstructor_VB.java.txt");
	}

	private ViewBindingInfo createViewBindingInfo(Class<? extends CustomViewBinding<?>> viewBindingType) {
		return createViewBindingInfo(viewBindingType, Collections.<SimpleOneWayPropertyInfo>emptyList(),
				Collections.<TwoWayPropertyInfo>emptyList());
	}

	private ViewBindingInfo createViewBindingInfo(Class<? extends CustomViewBinding<?>> viewBindingType, 
			List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList,
			List<TwoWayPropertyInfo> twoWayPropertyInfoList) {
		String viewBindingTypeName = viewBindingType.getName();
		String viewBindingObjectTypeName = viewBindingTypeName + "_VB";
		return new ViewBindingInfo(viewBindingTypeName, viewBindingObjectTypeName,
				viewElementType(), simpleOneWayPropertyInfoList, twoWayPropertyInfoList);
	}
	
	private WrappedTypeElement viewElementType() {
		return aptTestHelper.typeElementOf(ImageView.class);
	}

	private void assertOutputSameTextFile(ViewBindingObjectClassGen gen, String textFileName) {
		SourceCodeAssert.assertOutputSameTextFile(gen, textFileName);
	}
	
	@Test
	public void shouldDefineSimpleOneWayPropertyClasses() throws JClassAlreadyExistsException {
		ViewBindingInfo info = createViewBindingInfo(DefineSimpleOneWayPropertyClasses.class, 
				Lists.newArrayList(new SimpleOneWayPropertyInfo(setterOf("imageAlpha")),
						new SimpleOneWayPropertyInfo(setterOf("scaleType"))),
				Collections.<TwoWayPropertyInfo>emptyList());
		ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
		gen.defineSimpleOneWayPropertyClasses();

		assertOutputSameTextFile(gen, "DefineSimpleOneWayPropertyClasses_VB.java.txt");
	}
	
	private SetterElement setterOf(String propertyName) {
		return aptTestHelper.looseSetterOf(ImageView.class, propertyName);
	}

	@Test
	public void shouldDefineMapBindingAttributesMethod() throws JClassAlreadyExistsException {
		ViewBindingInfo info = createViewBindingInfo(DefineMapBindingAttributesMethod.class, 
				Lists.newArrayList(new SimpleOneWayPropertyInfo(setterOf("imageAlpha")),
						new SimpleOneWayPropertyInfo(setterOf("scaleType"))),
				Lists.newArrayList(new TwoWayPropertyInfo("twoWayProp", DefineMapBindingAttributesMethod.CustomTwoWayProp.class.getCanonicalName())));
		ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
		gen.defineFields();
		gen.defineSimpleOneWayPropertyClasses();
		gen.defineMapBindingAttributesMethod();

		assertOutputSameTextFile(gen, "DefineMapBindingAttributesMethod_VB.java.txt");
	}
}
