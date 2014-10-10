package org.robobinding.codegen;

import org.junit.Test;
import org.robobinding.codegen.typemirror.PresentationModelInfoBuilder;

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
		GenAsserts.assertOutputSameTextFile(gen, textFileName);
	}
	
}
