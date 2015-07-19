package org.robobinding.codegen.presentationmodel.processor;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

import javax.annotation.processing.Processor;

import com.google.testing.compile.JavaFileObjects;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ProcessorAssert {
	public static void compilesWithoutErrorWhenProcess(Class<?> type, Processor processor) {
		ASSERT.about(javaSource())
			.that(JavaFileObjects.forResource(new JavaFile(type).toURL()))
			.processedWith(processor)
			.compilesWithoutError();
	}

	public static void failsToCompileWhenProcess(Class<?> type, Processor processor) {
		try {
		ASSERT.about(javaSource())
			.that(JavaFileObjects.forResource(new JavaFile(type).toURL()))
			.processedWith(processor)
			.failsToCompile();
		}catch(RuntimeException e) {
			throw (RuntimeException)e.getCause();
		}
	}
	
	private ProcessorAssert() {}
}
