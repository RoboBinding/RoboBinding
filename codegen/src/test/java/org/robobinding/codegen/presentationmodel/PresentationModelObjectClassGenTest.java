package org.robobinding.codegen.presentationmodel;

import org.junit.ClassRule;
import org.junit.Test;
import org.robobinding.codegen.SourceCodeAssert;
import org.robobinding.codegen.apt.element.AptTestHelper;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.codegen.presentationmodel.processor.PresentationModelInfoBuilder;

import com.google.testing.compile.CompilationRule;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelObjectClassGenTest {
	@ClassRule public static final CompilationRule compilation = new CompilationRule();
	
	@Test
	public void shouldDefineConstructor() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineConstructor.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineConstructor();

		assertOutputSameTextFile(gen, "DefineConstructor_PM.java.txt");
	}

	private PresentationModelInfo createPresentationModelInfoFor(Class<?> type) {
		WrappedTypeElement typeElement = new AptTestHelper(compilation).typeElementOf(type);
		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
				type.getName()+"_PM", true);
		return new OrderedPresentationModelInfo(builder.build());
	}

	private void assertOutputSameTextFile(PresentationModelObjectClassGen gen, String textFileName) {
		SourceCodeAssert.assertOutputSameTextFile(gen, textFileName);
	}

	@Test
	public void shouldDefinePropertyNames() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefinePropertyNames.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.definePropertyNames();

		assertOutputSameTextFile(gen, "DefinePropertyNames_PM.java.txt");
	}
	
	@Test
	public void shouldDefineDataSetPropertyNames() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineDataSetPropertyNames.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineDataSetPropertyNames();

		assertOutputSameTextFile(gen, "DefineDataSetPropertyNames_PM.java.txt");
	}
	
	@Test
	public void shouldDefinePropertyDependencies() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefinePropertyDependencies.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.definePropertyDependencies();
		
		assertOutputSameTextFile(gen, "DefinePropertyDependencies_PM.java.txt");
	}
	
	@Test
	public void shouldDefineEventMethods() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineEventMethods.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineEventMethods();
		
		assertOutputSameTextFile(gen, "DefineEventMethods_PM.java.txt");
	}
	
	@Test
	public void shouldDefineTryToCreateProperty() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineTryToCreateProperty.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineTryToCreateProperty();
		
		assertOutputSameTextFile(gen, "DefineTryToCreateProperty_PM.java.txt");
	}
	
	@Test
	public void shouldDefineTryToCreateDataSetProperty() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineTryToCreateDataSetProperty.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineTryToCreateDataSetProperty();
		
		assertOutputSameTextFile(gen, "DefineTryToCreateDataSetProperty_PM.java.txt");
	}
	
	@Test
	public void shouldDefineTryToCreateFunction() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineTryToCreateFunction.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineTryToCreateFunction();
		
		assertOutputSameTextFile(gen, "DefineTryToCreateFunction_PM.java.txt");
	}
	
	@Test
	public void shouldDefineShouldPreInitializeViewsTrue() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineShouldPreInitializeViewsFalse.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineShouldPreInitializeViews();
		
		assertOutputSameTextFile(gen, "DefineShouldPreInitializeViewsFalse_PM.java.txt");
	}
	
	@Test
	public void shouldDefineShouldPreInitializeViewsFalse() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineShouldPreInitializeViewsTrue.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineShouldPreInitializeViews();
		
		assertOutputSameTextFile(gen, "DefineShouldPreInitializeViewsTrue_PM.java.txt");
	}
}
