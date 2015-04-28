package org.robobinding.codegen;

import org.junit.Test;
import org.robobinding.codegen.typemirror.PresentationModelInfoBuilder;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelObjectClassGenTest {
	@Test
	public void shouldDefineConstructor() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineConstructor.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineConstructor();

		assertOutputSameTextFile(gen, "DefineConstructor_PM.java.txt");
	}

	private PresentationModelInfo createPresentationModelInfoFor(Class<?> type) {
		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(type, 
				type.getName()+"_PM", true);
		return builder.build();
	}

	private void assertOutputSameTextFile(PresentationModelObjectClassGen gen, String textFileName) {
		GenAsserts.assertOutputSameTextFile(gen, textFileName);
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
	public void shouldDefineGroupedDataSetPropertyNames() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineDataSetPropertyNames.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineGroupedDataSetPropertyNames();

//		assertOutputSameTextFile(gen, "DefineGroupedDataSetPropertyNames_PM.java.txt");
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
	public void shouldDefineTryToCreateGroupedDataSetProperty() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineTryToCreateGroupedDataSetProperty.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineTryToCreateGroupedDataSetProperty();

//		assertOutputSameTextFile(gen, "DefineTryToCreateDataSetProperty_PM.java.txt");
	}


	@Test
	public void shouldDefineTryToCreateFunction() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineTryToCreateFunction.class);
		PresentationModelObjectClassGen gen = new PresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineTryToCreateFunction();
		
		assertOutputSameTextFile(gen, "DefineTryToCreateFunction_PM.java.txt");
	}
}
