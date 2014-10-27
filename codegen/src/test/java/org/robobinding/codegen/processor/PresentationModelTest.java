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
public class PresentationModelTest {
	@Test
	public void whenProcessPresentationModel_thenGetExpectedResult() {
		PresentationModelInfo result = processJavaFileOf(SelfDescribedPresentationModel.class);

		assertThat(result.properties().size(), is(SelfDescribedPresentationModel.numProperties));
		assertThat(result.dataSetProperties().size(), is(SelfDescribedPresentationModel.numDataSetProperties));
		assertThat(result.eventMethods().size(), is(SelfDescribedPresentationModel.numEventMethods));
	}
	
	private PresentationModelInfo processJavaFileOf(Class<?> type) {
		PresentationModelInfoProcessor processor = new PresentationModelInfoProcessor();
		
		ASSERT.about(javaSource())
			.that(JavaFileObjects.forResource(new JavaFile(type).toURL()))
			.processedWith(processor)
			.compilesWithoutError();
		
		return processor.result;
	}
	
	@Test
	public void shouldGetPresentationModelChangeSupportMethodIgnored() {
		PresentationModelInfo result = processJavaFileOf(GetPresentationModelChangeSupportIgnored.class);
		assertThat(result.properties().size(), is(0));
	}
	
	@Test
	public void shouldInvaidPropertiesIgnored() {
		PresentationModelInfo result = processJavaFileOf(InvalidPropertiesIgnored.class);
		assertThat(result.properties().size(), is(0));
	}
	
	@Test
	public void shouldSetterOfDataSetPropertyIgnored() {
		PresentationModelInfo result = processJavaFileOf(SetterOfDataSetPropertyIgnored.class);
		assertThat(result.properties().size(), is(0));
	}
	
	@Test
	public void shouldInvalidEventMethodIgnored() {
		PresentationModelInfo result = processJavaFileOf(InvalidEventMethodIgnored.class);
		assertThat(result.eventMethods().size(), is(0));
	}
	
	@Test
	public void shouldHierarchicalPresentationModelRecognized() {
		PresentationModelInfo result = processJavaFileOf(HierarchicalPresentationModel.class);
		assertThat(result.properties().size(), is(HierarchicalPresentationModel.numProperties));
		assertThat(result.eventMethods().size(), is(HierarchicalPresentationModel.numEventMethods));
	}
}
