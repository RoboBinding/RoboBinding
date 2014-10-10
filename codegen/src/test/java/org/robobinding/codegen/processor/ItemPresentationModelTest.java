package org.robobinding.codegen.processor;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.truth0.Truth.ASSERT;

import org.junit.Test;
import org.robobinding.codegen.PresentationModelInfo;

import com.google.testing.compile.JavaFileObjects;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelTest {
	@Test
	public void shouldAllDataSetPropertiesTreatedAsProperties() {
		PresentationModelInfo result = processJavaFileOf(PresentationModelForDataSetProp.class);
		
		assertThat(result.properties().size(), is(1));
		assertThat(result.dataSetProperties().size(), is(0));
	}
	
	private PresentationModelInfo processJavaFileOf(Class<?> type) {
		PresentationModelItemInfoProcessor processor = new PresentationModelItemInfoProcessor();
		
		ASSERT.about(javaSource())
			.that(JavaFileObjects.forResource(new JavaFile(type).toURL()))
			.processedWith(processor)
			.compilesWithoutError();
		
		return processor.result;
	}
}
