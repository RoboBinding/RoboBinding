package org.robobinding;

import static org.junit.Assert.fail;
import static org.robobinding.ViewInflationErrorsExpectation.aBindingViewInflationErrorExpectationOf;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;
import org.robobinding.binder.ViewHierarchyInflationErrorsException;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.presentationmodel.AbstractPresentationModel;
import org.robobinding.robolectric.DefaultTestRunner;
import org.robolectric.Robolectric;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.common.collect.Lists;

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
		ViewBinder viewBinder = binderFactory.createViewBinder(Robolectric.application);
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

	public static class Sample1PresentationModel extends AbstractPresentationModel {
		private boolean property;
		private List<String> source;

		public Sample1PresentationModel() {
			source = Lists.newArrayList("aa", "bb", "cc");
		}

		public boolean isProperty() {
			return property;
		}

		public void setProperty(boolean property) {
			this.property = property;
		}

		@ItemPresentationModel(StringItemPresentationModel.class)
		public List<String> getSource() {
			return source;
		}
	}

	public static class StringItemPresentationModel implements org.robobinding.itempresentationmodel.ItemPresentationModel<String> {
		@Override
		public void updateData(String bean, ItemContext itemContext) {
		}
	}
}
