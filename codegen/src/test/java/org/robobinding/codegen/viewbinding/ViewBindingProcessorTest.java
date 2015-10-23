package org.robobinding.codegen.viewbinding;

import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robobinding.codegen.presentationmodel.processor.ProcessorAssert;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@RunWith(Theories.class)
public class ViewBindingProcessorTest {
	@Rule public final ExpectedException thrownException = ExpectedException.none();
	private ViewBindingProcessor processor;
	
	@Before
	public void setUp() {
		processor = new ViewBindingProcessor();
	}
	
	@DataPoints("validViewBindings")
	public static Class<?>[] validViewBindings = {
		SimpleOneWayPropertyViewBinding.class, 
		NoSimpleOneWayPropertyViewBinding.class};
	
	@Theory
	public void shouldProcessSuccessfully(@FromDataPoints("validViewBindings") Class<?> validViewBinding) {
		ProcessorAssert.compilesWithoutErrorWhenProcess(validViewBinding, processor);
	}
	
	@DataPoints("invalidViewBindings")
	public static Class<?>[] invalidViewBindings = {SimpleOneWayPropertyViewBindingInvalid1.class, 
		/*SimpleOneWayPropertyViewBindingInvalid2.class*/};
	
	@Theory
	public void shouldGetExpectedError(@FromDataPoints("invalidViewBindings") Class<?> invalidViewBinding) {
		thrownException.expect(OneWayBindingPropertyGenerationException.class);
		ProcessorAssert.failsToCompileWhenProcess(invalidViewBinding, processor);
	}
}
