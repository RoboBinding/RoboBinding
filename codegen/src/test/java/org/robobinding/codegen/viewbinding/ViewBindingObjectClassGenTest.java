package org.robobinding.codegen.viewbinding;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.robobinding.codegen.SourceCodeAssert;
import org.robobinding.customviewbinding.CustomViewBinding;

import android.widget.ImageView;

import com.google.common.collect.Lists;
import com.sun.codemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingObjectClassGenTest {
	@Test
	public void shouldDefineConstructor() {
		ViewBindingInfo info = createViewBindingInfo(DefineConstructor.class);
		ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
		gen.defineFields();
		gen.defineConstructor();

		assertOutputSameTextFile(gen, "DefineConstructor_VB.java.txt");
	}

	private ViewBindingInfo createViewBindingInfo(Class<? extends CustomViewBinding<?>> viewBindingType) {
		return createViewBindingInfo(viewBindingType, Collections.<SimpleOneWayPropertyInfo>emptyList());
	}

	private ViewBindingInfo createViewBindingInfo(Class<? extends CustomViewBinding<?>> viewBindingType, 
			List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList) {
		String viewBindingTypeName = viewBindingType.getName();
		String viewBindingObjectTypeName = viewBindingTypeName + "_VB";
		return new ViewBindingInfo(viewBindingTypeName, viewBindingObjectTypeName,
				ImageView.class, simpleOneWayPropertyInfoList);
	}

	private void assertOutputSameTextFile(ViewBindingObjectClassGen gen, String textFileName) {
		SourceCodeAssert.assertOutputSameTextFile(gen, textFileName);
	}
	
	@Test
	public void shouldDefineSimpleOneWayPropertyClasses() throws JClassAlreadyExistsException {
		ViewBindingInfo info = createViewBindingInfo(DefineSimpleOneWayPropertyClasses.class, 
				Lists.newArrayList(new SimpleOneWayPropertyInfo(int.class, "imageAlpha"),
						new SimpleOneWayPropertyInfo(ImageView.ScaleType.class, "scaleType")));
		ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
		gen.defineSimpleOneWayPropertyClasses();

		assertOutputSameTextFile(gen, "DefineSimpleOneWayPropertyClasses_VB.java.txt");
	}

	@Test
	public void shouldDefineMapBindingAttributesMethod() throws JClassAlreadyExistsException {
		ViewBindingInfo info = createViewBindingInfo(DefineMapBindingAttributesMethod.class, 
				Lists.newArrayList(new SimpleOneWayPropertyInfo(int.class, "imageAlpha"),
						new SimpleOneWayPropertyInfo(ImageView.ScaleType.class, "scaleType")));
		ViewBindingObjectClassGen gen = new ViewBindingObjectClassGen(info);
		gen.defineFields();
		gen.defineSimpleOneWayPropertyClasses();
		gen.defineMapBindingAttributesMethod();

		assertOutputSameTextFile(gen, "DefineMapBindingAttributesMethod_VB.java.txt");
	}
}
