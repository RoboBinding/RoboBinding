package org.robobinding;

import static org.junit.Assert.fail;
import static org.robobinding.ViewInflationErrorsExpectation.aBindingViewInflationErrorExpectationOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;
import org.robobinding.binder.ViewHierarchyInflationErrorsException;
import org.robobinding.robolectric.DefaultTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(DefaultTestRunner.class)
public class ErrorReportingTest {
	private BinderFactory binderFactory;
	
	@Before
	public void setUp() {
		BinderFactoryBuilder binder = new BinderFactoryBuilder();
		binderFactory = binder.build();
		
	}
	
	@Test
	public void whenInflateAndBindSample1_thenThrowsExpectedErrors() {
		ViewBinder viewBinder = binderFactory.createViewBinder(RuntimeEnvironment.application);
		try{
			viewBinder.inflateAndBind(R.layout.error_reporting_sample1, new Sample1PresentationModel());
			fail("Expect an exception thrown");
		}catch(ViewHierarchyInflationErrorsException bindingViewInflationErrors) {
			ViewInflationErrorsExpectations expectations = createSample1ErrorsExpectations();
			expectations.meet(bindingViewInflationErrors);
		}
	}
	
	private ViewInflationErrorsExpectations createSample1ErrorsExpectations() {
		ViewInflationErrorsExpectations inflationErrorsExpectations = new ViewInflationErrorsExpectations();
		
		ViewInflationErrorsExpectation buttonExpectation = aBindingViewInflationErrorExpectationOf(Button.class)
				.withAttributeResolutionErrorOf("text")
				.withAttributeResolutionErrorOf("nonExistingAttribute")
				.withAttributeBindingErrorOf("visibility")
				.build();
		inflationErrorsExpectations.add(buttonExpectation);
		
		ViewInflationErrorsExpectation listViewExpectation = aBindingViewInflationErrorExpectationOf(ListView.class)
			    .withAttributeResolutionErrorOf("visibility")
			    .withAttributeBindingErrorOf("itemLayout")
			    .build();
		inflationErrorsExpectations.add(listViewExpectation);
		
		ViewInflationErrorsExpectation spinnerExpectation = aBindingViewInflationErrorExpectationOf(Spinner.class)
				.withAttributeResolutionErrorOf("visibility")
				.withMissingRequiredAttributesResolutionErrorOf("source", "dropdownLayout")
				.build();
		inflationErrorsExpectations.add(spinnerExpectation);
		
		return inflationErrorsExpectations;
	}
}
