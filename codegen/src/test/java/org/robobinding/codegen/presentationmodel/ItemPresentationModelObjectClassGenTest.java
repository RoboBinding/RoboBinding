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
public class ItemPresentationModelObjectClassGenTest {
	@ClassRule public static final CompilationRule compilation = new CompilationRule();
	
	@Test
	public void shouldDefineConstructorWithChangeSupport() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineConstructor.class);
		ItemPresentationModelObjectClassGen gen = new ItemPresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineConstructor();

		assertOutputSameTextFile(gen, "DefineConstructor_IPM.java.txt");
	}

	private PresentationModelInfo createPresentationModelInfoFor(Class<?> type) {
		WrappedTypeElement typeElement = new AptTestHelper(compilation).typeElementOf(type);
		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
				type.getName()+"_IPM", false);
		return new OrderedPresentationModelInfo(builder.build());
	}

	private void assertOutputSameTextFile(ItemPresentationModelObjectClassGen gen, String textFileName) {
		SourceCodeAssert.assertOutputSameTextFile(gen, textFileName);
	}
	
}
