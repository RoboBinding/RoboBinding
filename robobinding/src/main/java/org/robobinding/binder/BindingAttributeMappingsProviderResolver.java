/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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

import java.util.Queue;

import org.robobinding.viewattribute.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;

import android.view.View;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class BindingAttributeMappingsProviderResolver {
    private final BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;

    public BindingAttributeMappingsProviderResolver(BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap) {
	this.bindingAttributeMappingsProviderMap = bindingAttributeMappingsProviderMap;
    }

    public Iterable<BindingAttributeMappingsProvider<? extends View>> findCandidateProviders(View view) {
	Queue<BindingAttributeMappingsProvider<? extends View>> candidateProviders = Lists.newLinkedList();

	processViewHierarchy(view.getClass(), candidateProviders);

	return candidateProviders;
    }

    @SuppressWarnings("unchecked")
    private void processViewHierarchy(Class<? extends View> clazz, Queue<BindingAttributeMappingsProvider<? extends View>> candidateProviders) {
	BindingAttributeMappingsProvider<? extends View> provider = bindingAttributeMappingsProviderMap.find(clazz);

	if (provider != null)
	    candidateProviders.add(provider);

	if (clazz != View.class)
	    processViewHierarchy((Class<? extends View>) clazz.getSuperclass(), candidateProviders);
    }
}