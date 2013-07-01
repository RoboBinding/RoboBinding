/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.integrationtest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.robobinding.binder.BindingViewInflaterForTest.aBindingViewInflater;
import static org.robobinding.binder.MockBindingAttributeSetBuilder.aBindingAttributeSet;
import static org.robobinding.integrationtest.ViewInflationErrorsExpectation.aBindingViewInflationErrorExpectationOf;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.R;
import org.robobinding.binder.BinderImplementorForTest;
import org.robobinding.binder.BindingViewInflaterForTest;
import org.robobinding.binder.ViewHierarchyInflationErrorsException;
import org.robobinding.binder.ViewInflationErrors;
import org.robobinding.presentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.PresentationModel;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class FrameworkErrorReportingIT {
    @Test
    public void whenInflateAndBindSample1_thenExpectationIsMeet() {
	SampleAndExpectation1 sampleAndExpectation1 = new SampleAndExpectation1();
	try {
	    sampleAndExpectation1.inflateAndBind();
	    fail("Expect an exception thrown");
	} catch (ViewHierarchyInflationErrorsException bindingViewInflationErrors) {
	    sampleAndExpectation1.meet(bindingViewInflationErrors);
	}
    }

    private static class SampleAndExpectation1 extends AbstractSampleAndExpectation {
	@Override
	protected void addViewAndExpectations() {
	    addButtonAndExpectations();
	    addListViewAndExpectations();
	    addSpinnerAndExpectations();
	}

	private void addButtonAndExpectations() {
	    Button button = new Button(context);

	    bindingViewInflaterBuilder.withOnViewCreatedInvocation(button, aBindingAttributeSet()
	    // resolution phase.
		    .withAttribute("text", "{invalidSyntax").withAttribute("nonExistentAttribute", "{property}")
		    // binding phase.
		    .withAttribute("visibility", "{nonExistentProperty}").build());

	    ViewInflationErrorsExpectation errorExpectation = aBindingViewInflationErrorExpectationOf(button).withAttributeResolutionErrorOf("text")
		    .withAttributeResolutionErrorOf("nonExistentAttribute").withAttributeBindingErrorOf("visibility").build();
	    inflationErrorsExpectations.add(errorExpectation);
	}

	private void addListViewAndExpectations() {
	    ListView listView = new ListView(context);

	    bindingViewInflaterBuilder.withOnViewCreatedInvocation(listView, aBindingAttributeSet()
	    // resolution phase.
		    .withAttribute("visibility", "${invalidSyntax").withAttribute("source", "{source}")
		    // binding phase.
		    .withAttribute("itemLayout", "{propertyPointingToNoExistentLayout}").build());

	    ViewInflationErrorsExpectation errorExpectation = aBindingViewInflationErrorExpectationOf(listView)
		    .withAttributeResolutionErrorOf("visibility").withAttributeBindingErrorOf("itemLayout").build();
	    inflationErrorsExpectations.add(errorExpectation);
	}

	private void addSpinnerAndExpectations() {
	    Spinner spinner = new Spinner(context);

	    bindingViewInflaterBuilder.withOnViewCreatedInvocation(spinner, aBindingAttributeSet()
	    // binding phase.
		    .withAttribute("itemLayout", "{propertyPointingToNoExistentLayout}").build());

	    ViewInflationErrorsExpectation errorExpectation = aBindingViewInflationErrorExpectationOf(spinner)
		    .withMissingRequiredAttributesResolutionErrorOf("source", "dropdownLayout").build();
	    inflationErrorsExpectations.add(errorExpectation);
	}
    }

    private abstract static class AbstractSampleAndExpectation {
	protected final Context context;
	protected BindingViewInflaterForTest.Builder bindingViewInflaterBuilder;
	protected List<ViewInflationErrorsExpectation> inflationErrorsExpectations;

	public AbstractSampleAndExpectation() {
	    context = new Activity();
	}

	public View inflateAndBind() {
	    bindingViewInflaterBuilder = aBindingViewInflater(context);
	    inflationErrorsExpectations = Lists.newArrayList();

	    addViewAndExpectations();

	    BinderImplementorForTest binder = new BinderImplementorForTest(context, bindingViewInflaterBuilder.build());
	    return binder.inflateAndBind(R.layout.framework_error_reporting_it_sample1, new PresentationModelForTest());
	}

	protected abstract void addViewAndExpectations();

	public void meet(ViewHierarchyInflationErrorsException bindingViewInflationErrors) {
	    Collection<ViewInflationErrors> errors = bindingViewInflationErrors.getErrors();
	    assertThat(errors.size(), is(expectedNumInflationErrors()));

	    int index = 0;
	    for (ViewInflationErrorsExpectation inflationErrorExpectation : inflationErrorsExpectations) {
		inflationErrorExpectation.meet((ViewInflationErrors) CollectionUtils.get(errors, index));
		index++;
	    }
	}

	private int expectedNumInflationErrors() {
	    return inflationErrorsExpectations.size();
	}
    }

    @PresentationModel
    static class PresentationModelForTest {
	private boolean property;
	private List<String> source;

	public PresentationModelForTest() {
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
	public void updateData(int index, String bean) {
	}
    }
}
