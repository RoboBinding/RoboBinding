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
package org.robobinding;

import static com.google.common.collect.Lists.newArrayList;
import static org.robobinding.CollectionUtils.isNotEmpty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.attribute.MissingRequiredAttributesException;

import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class ViewResolutionErrorsException extends RuntimeException implements ViewResolutionErrors {
    private View view;
    private List<AttributeResolutionException> attributeErrors = newArrayList();
    private List<MissingRequiredAttributesException> missingRequiredAttributeErrors = newArrayList();

    public ViewResolutionErrorsException(View view) {
	this.view = view;
    }

    @Override
    public View getView() {
	return view;
    }

    @Override
    public int numErrors() {
	return attributeErrors.size() + missingRequiredAttributeErrors.size();
    }

    @Override
    public void assertNoErrors() {
	if (hasErrors()) {
	    throw this;
	}
    }

    @Override
    public boolean hasErrors() {
	return isNotEmpty(attributeErrors) || isNotEmpty(missingRequiredAttributeErrors);
    }

    public void addAttributeError(AttributeResolutionException e) {
	attributeErrors.add(e);
    }

    public void addGroupedAttributeError(GroupedAttributeResolutionException e) {
	attributeErrors.addAll(e.getAttributeResolutionExceptions());
    }

    public void addUnrecognizedAttributes(Collection<String> attributes) {
	for (String attribute : attributes) {
	    addAttributeError(new UnrecognizedAttributeException(attribute));
	}
    }

    public void addMissingRequiredAttributeError(MissingRequiredAttributesException e) {
	missingRequiredAttributeErrors.add(e);
    }

    @Override
    public Collection<AttributeResolutionException> getAttributeErrors() {
	return Collections.unmodifiableCollection(attributeErrors);
    }

    @Override
    public Collection<MissingRequiredAttributesException> getMissingRequiredAttributeErrors() {
	return Collections.unmodifiableCollection(missingRequiredAttributeErrors);
    }

    @Override
    public Collection<Exception> getErrors() {
	List<Exception> errors = Lists.newArrayList();
	errors.addAll(attributeErrors);
	errors.addAll(missingRequiredAttributeErrors);
	return errors;
    }
}
