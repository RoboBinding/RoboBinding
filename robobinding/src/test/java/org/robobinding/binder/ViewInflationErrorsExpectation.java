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
package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.robobinding.AttributeResolutionException;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.binder.ViewInflationErrors;
import org.robobinding.binder.ViewBindingErrors;
import org.robobinding.viewattribute.AttributeBindingException;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewInflationErrorsExpectation {
    private View view;
    private List<AttributeResolutionErrorExpectation> attributeResolutionErrorExpectations;
    private List<MissingRequiredAttributesResolutionErrorExpectation> missingRequiredAttributesResolutionErrorExpectations;
    private List<AttributeBindingErrorExpectation> attributeBindingErrorExpectations;

    private ViewInflationErrorsExpectation(ViewInflationErrorsExpectation.Builder builder) {
	this.view = builder.view;
	this.attributeResolutionErrorExpectations = builder.attributeResolutionErrorExpectations;
	this.missingRequiredAttributesResolutionErrorExpectations = builder.missingRequiredAttributesResolutionErrorExpectations;
	this.attributeBindingErrorExpectations = builder.attributeBindingErrorExpectations;
    }

    public static Builder aBindingViewInflationErrorExpectationOf(View view) {
	return new Builder(view);
    }

    public void meet(ViewInflationErrors error) {
	assertThat(error.getView(), sameInstance(view));

	assertResolutionErrors(error.getResolutionErrors());
	assertBindingErrors(error.getBindingErrors());
    }

    private void assertResolutionErrors(ViewResolutionErrors error) {
	assertThat(getViewName() + " has unmatched resolution errors.", error.numErrors(), is(expectedNumResolutionErrors()));

	assertAttributeResolutionErrors(error.getAttributeErrors());
	assertMissingRequiredAttributesResolutionErrors(error.getMissingRequiredAttributeErrors());
    }

    private int expectedNumResolutionErrors() {
	return attributeResolutionErrorExpectations.size() + missingRequiredAttributesResolutionErrorExpectations.size();
    }

    private void assertAttributeResolutionErrors(Collection<AttributeResolutionException> attributeResolutionErrors) {
	assertThat(getViewName() + " has unmatched attribute resolution errors.", attributeResolutionErrors.size(),
		is(expectedNumAttributeResolutionErrors()));

	int index = 0;
	for (AttributeResolutionException error : attributeResolutionErrors) {
	    AttributeResolutionErrorExpectation errorExpectation = attributeResolutionErrorExpectations.get(index);
	    errorExpectation.meet(error);
	    index++;
	}
    }

    private int expectedNumAttributeResolutionErrors() {
	return attributeResolutionErrorExpectations.size();
    }

    private void assertMissingRequiredAttributesResolutionErrors(Collection<MissingRequiredAttributesException> missingRequiredAttributeErrors) {
	assertThat(getViewName() + " has unmatched missing required attributes resolution errors.", missingRequiredAttributeErrors.size(),
		is(expectedNumMissingRequiredAttributeErrors()));

	int index = 0;
	for (MissingRequiredAttributesException error : missingRequiredAttributeErrors) {
	    MissingRequiredAttributesResolutionErrorExpectation errorExpectation = missingRequiredAttributesResolutionErrorExpectations.get(index);
	    errorExpectation.meet(error);
	    index++;
	}
    }

    private int expectedNumMissingRequiredAttributeErrors() {
	return missingRequiredAttributesResolutionErrorExpectations.size();
    }

    private void assertBindingErrors(ViewBindingErrors error) {
	try {
	    assertThat(getViewName() + " has unmatched binding errors.", error.numErrors(), is(expectedNumAttributeBindingErrors()));
	} catch (AssertionError e) {
	    throw e;
	}

	int index = 0;
	for (AttributeBindingException bindingError : error.getAttributeErrors()) {
	    AttributeBindingErrorExpectation errorExpectation = attributeBindingErrorExpectations.get(index);
	    errorExpectation.meet(bindingError);
	    index++;
	}
    }

    private int expectedNumAttributeBindingErrors() {
	return attributeBindingErrorExpectations.size();
    }

    private String getViewName() {
	return view.getClass().getSimpleName();
    }

    public static class Builder {
	private View view;
	private List<AttributeResolutionErrorExpectation> attributeResolutionErrorExpectations;
	private List<MissingRequiredAttributesResolutionErrorExpectation> missingRequiredAttributesResolutionErrorExpectations;
	private List<AttributeBindingErrorExpectation> attributeBindingErrorExpectations;

	private Builder(View view) {
	    this.view = view;
	    attributeResolutionErrorExpectations = Lists.newArrayList();
	    missingRequiredAttributesResolutionErrorExpectations = Lists.newArrayList();
	    attributeBindingErrorExpectations = Lists.newArrayList();
	}

	public Builder withAttributeResolutionErrorOf(String attributeName) {
	    attributeResolutionErrorExpectations.add(new AttributeResolutionErrorExpectation(attributeName));
	    return this;
	}

	public Builder withMissingRequiredAttributesResolutionErrorOf(String... attributeNames) {
	    missingRequiredAttributesResolutionErrorExpectations.add(new MissingRequiredAttributesResolutionErrorExpectation(attributeNames));
	    return this;
	}

	public Builder withAttributeBindingErrorOf(String attributeName) {
	    attributeBindingErrorExpectations.add(new AttributeBindingErrorExpectation(attributeName));
	    return this;
	}

	public ViewInflationErrorsExpectation build() {
	    return new ViewInflationErrorsExpectation(this);
	}
    }

    public static class AttributeResolutionErrorExpectation {
	private String attributeName;

	public AttributeResolutionErrorExpectation(String attributeName) {
	    this.attributeName = attributeName;
	}

	public void meet(AttributeResolutionException e) {
	    assertThat(e.getAttributeName(), is(attributeName));
	}
    }

    public static class MissingRequiredAttributesResolutionErrorExpectation {
	private Set<String> missingAttributes;

	public MissingRequiredAttributesResolutionErrorExpectation(String[] missingAttributes) {
	    this.missingAttributes = Sets.newHashSet(missingAttributes);
	}

	public void meet(MissingRequiredAttributesException e) {
	    assertThat(Sets.newHashSet(e.getMissingAttributes()), is(missingAttributes));
	}
    }

    public static class AttributeBindingErrorExpectation {
	private String attributeName;

	public AttributeBindingErrorExpectation(String attributeName) {
	    this.attributeName = attributeName;
	}

	public void meet(AttributeBindingException e) {
	    assertThat(e.getAttributeName(), is(attributeName));
	}
    }
}