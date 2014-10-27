package org.robobinding;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.robobinding.binder.ViewHierarchyInflationErrorsException;
import org.robobinding.binder.ViewInflationErrors;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 *
 */
public class ViewInflationErrorsExpectations {
	private List<ViewInflationErrorsExpectation> expectations;
	
	public ViewInflationErrorsExpectations() {
		expectations = Lists.newArrayList();
	}

	public void add(ViewInflationErrorsExpectation expectation) {
		expectations.add(expectation);
	}

	public void meet(ViewHierarchyInflationErrorsException bindingViewInflationErrors) {
		Collection<ViewInflationErrors> allErrors = bindingViewInflationErrors.getErrors();
		assertThat(allErrors.size(), is(expectedNumInflationErrors()));

		for (int index=0; index<allErrors.size(); index++) {
			ViewInflationErrorsExpectation expectation  = expectations.get(index);
			ViewInflationErrors errors = (ViewInflationErrors)CollectionUtils.get(allErrors, index);
			expectation.meet(errors);
		}
	}
	
	private int expectedNumInflationErrors() {
		return expectations.size();
	}

}
