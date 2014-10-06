package org.robobinding.codegen.processor;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.fest.assertions.api.Assertions.fail;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.truth0.Truth.ASSERT;

import org.junit.Test;

import com.google.testing.compile.JavaFileObjects;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelErrorTest {
	@Test
	public void shouldGetExpectedErrors() {
		try
		{
			processJavaFileOf(PresentationModelWithErrors.class);
			fail("expect errors");
		} catch(PresentationModelErrors errors) {
			assertThat(errors.propertyErrors.size(), is(PresentationModelWithErrors.numPropertyErrors));
			assertThat(errors.propertyDependencyErrors.size(), is(PresentationModelWithErrors.numPropertyDependencyErrors));
		}
	}
	
	private void processJavaFileOf(Class<?> type) {
		try {
		PresentationModelInfoProcessor processor = new PresentationModelInfoProcessor();
		
		ASSERT.about(javaSource())
			.that(JavaFileObjects.forResource(new JavaFile(type).toURL()))
			.processedWith(processor)
			.failsToCompile();
		}catch(RuntimeException e) {
			throw (RuntimeException)e.getCause();
		}
	}
}
