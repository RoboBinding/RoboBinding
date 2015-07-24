package org.robobinding.codegen.presentationmodel.processor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.codegen.presentationmodel.nestedIPM.ItemPresentationModelExample;
import org.robobinding.codegen.presentationmodel.nestedIPM.PresentationModelExample;
import org.robobinding.codegen.presentationmodel.sharedfactorymethod.SharedFacotryMethodPresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelProcessorTest {
	private PresentationModelProcessor processor;
	
	@Before
	public void setUp() {
		processor = new PresentationModelProcessor();
	}
	
	@Test
	public void shouldSuccessWhenProcessSample1() {
		ProcessorAssert.compilesWithoutErrorWhenProcess(PresentationModelSample1.class, processor);
	}
	
	@Test
	public void shouldSuccessWhenProcessNestedIPMExample() {
		ProcessorAssert.compilesWithoutErrorWhenProcess(processor, PresentationModelExample.class, ItemPresentationModelExample.class);
	}
	
	@Test
	public void shouldSuccessWhenProcessSharedFactoryMethodExample() {
		ProcessorAssert.compilesWithoutErrorWhenProcess(processor, SharedFacotryMethodPresentationModel.class);
	}
	
	@Test
	public void shouldGetExpectedErrors() {
		try
		{
			ProcessorAssert.failsToCompileWhenProcess(PresentationModelWithErrors.class, processor);
			fail("expect errors");
		} catch(PresentationModelErrors errors) {
			assertThat(errors.propertyErrors.size(), is(PresentationModelWithErrors.numPropertyErrors));
			assertThat(errors.propertyDependencyErrors.size(), is(PresentationModelWithErrors.numPropertyDependencyErrors));
		}
	}
}
