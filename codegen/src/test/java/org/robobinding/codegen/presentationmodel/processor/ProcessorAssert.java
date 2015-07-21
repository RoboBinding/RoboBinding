package org.robobinding.codegen.presentationmodel.processor;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;
import static org.truth0.Truth.ASSERT;

import java.util.List;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;

import com.google.android.collect.Lists;
import com.google.testing.compile.JavaFileObjects;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ProcessorAssert {
	public static void compilesWithoutErrorWhenProcess(Processor processor, Class<?>... types) {
		ASSERT.about(javaSources())
			.that(asJavaFileObjects(types))
			.processedWith(processor)
			.compilesWithoutError();
	}
	
	private static Iterable<? extends JavaFileObject> asJavaFileObjects(Class<?>[] types) {
		List<JavaFileObject> result = Lists.newArrayList();
		for(Class<?> type : types) {
			result.add(JavaFileObjects.forResource(new JavaFile(type).toURL()));
		}
		
		return result;
	}
	
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
