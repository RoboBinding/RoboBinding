package org.robobinding.codegen;

import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.testing.compile.JavaFileObjects;
/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelProcessorTest {
	@Test
	public void test() throws IOException {
		File file = new File("src/test/java/org/robobinding/codegen/samples/PresentationModelWithoutChangeSupport.java");
		ASSERT.about(javaSource())
			.that(JavaFileObjects.forResource(file.toURL()))
			.processedWith(new PresentationModelProcessor())
			.compilesWithoutError()
			.and().generatesSources(JavaFileObjects.forResource("GeneratedHelloWorld.java"));
	}
}
