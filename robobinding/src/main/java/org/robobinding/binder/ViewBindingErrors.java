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

import static org.robobinding.CollectionUtils.isNotEmpty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.AttributeGroupBindingException;

import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class ViewBindingErrors extends RuntimeException {
    private View view;
    private List<AttributeBindingException> attributeErrors;

    public ViewBindingErrors(View view) {
	this.view = view;
	attributeErrors = Lists.newArrayList();
    }

    public View getView() {
	return view;
    }

    public void assertNoErrors() {
	if (hasErrors()) {
	    throw this;
	}
    }

    public boolean hasErrors() {
	return isNotEmpty(attributeErrors);
    }

    public int numErrors() {
	return attributeErrors.size();
    }

    void addAttributeError(AttributeBindingException attributeError) {
	attributeErrors.add(attributeError);
    }

    public void addAttributeGroupError(AttributeGroupBindingException e) {
	attributeErrors.addAll(e.getChildAttributeErrors());
    }

    public Collection<AttributeBindingException> getAttributeErrors() {
	return Collections.unmodifiableCollection(attributeErrors);
    }
}
