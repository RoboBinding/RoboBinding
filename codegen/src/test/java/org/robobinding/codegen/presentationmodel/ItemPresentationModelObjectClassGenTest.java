package org.robobinding.codegen.presentationmodel;

import org.junit.Test;
import org.robobinding.codegen.SourceCodeAssert;
import org.robobinding.codegen.presentationmodel.ItemPresentationModelObjectClassGen;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;
import org.robobinding.codegen.presentationmodel.typemirror.PresentationModelInfoBuilder;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelObjectClassGenTest {
	@Test
	public void shouldDefineConstructorWithChangeSupport() {
		PresentationModelInfo presentationModelInfo = createPresentationModelInfoFor(DefineConstructor.class);
		ItemPresentationModelObjectClassGen gen = new ItemPresentationModelObjectClassGen(presentationModelInfo);
		gen.defineFields();
		gen.defineConstructor();

		assertOutputSameTextFile(gen, "DefineConstructor_IPM.java.txt");
	}

	private PresentationModelInfo createPresentationModelInfoFor(Class<?> type) {
		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(type, 
				type.getName()+"_IPM", false);
		return builder.build();
	}

	private void assertOutputSameTextFile(ItemPresentationModelObjectClassGen gen, String textFileName) {
		SourceCodeAssert.assertOutputSameTextFile(gen, textFileName);
	}
	
}
