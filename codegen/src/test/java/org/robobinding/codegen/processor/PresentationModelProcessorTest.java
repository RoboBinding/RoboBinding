package org.robobinding.codegen.processor;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

import org.junit.Test;

import com.google.testing.compile.JavaFileObjects;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelProcessorTest {
	
	@Test
	public void shouldSuccessWhenProcessSample1() {
		processJavaFileOf(PresentationModelSample1.class);
	}
	
	private void processJavaFileOf(Class<?> type) {
		PresentationModelProcessor processor = new PresentationModelProcessor();
		
		ASSERT.about(javaSource())
			.that(JavaFileObjects.forResource(new JavaFile(type).toURL()))
			.processedWith(processor)
			.compilesWithoutError();
	}

}
