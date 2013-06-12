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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.PendingAttributesForView;
import org.robobinding.viewattribute.BindingAttributeProvider;
import org.robobinding.viewattribute.ViewAttribute;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@SuppressWarnings("unchecked")
public class BindingAttributeResolverTest {
    private BindingAttributeResolverForTest bindingAttributeResolver;
    private List<BindingAttributeProvider<? extends View>> candidateBindingAttributeProviders;

    @Before
    public void setUp() {
	candidateBindingAttributeProviders = Lists.newArrayList();
	bindingAttributeResolver = new BindingAttributeResolverForTest(candidateBindingAttributeProviders);
    }

    @Test
    public void givenTwoCandidateBindingAttributeProviders_whenResolve_thenBothProvidersShouldInvolveResolving() {
	BindingAttributeProvider<View> bindingAttributeProvider1 = addNewCandidateBindingAttributeProvider();
	BindingAttributeProvider<View> bindingAttributeProvider2 = addNewCandidateBindingAttributeProvider();
	PendingAttributesForView pendingAttributesForView = mock(PendingAttributesForView.class);
	when(pendingAttributesForView.isEmpty()).thenReturn(false);

	bindingAttributeResolver.resolve(pendingAttributesForView);

	bindingAttributeResolver.assertBindingAttributeProvidersInvolvedResolving(bindingAttributeProvider1, bindingAttributeProvider2);
    }

    @Test
    public void givenTwoCandidateBindingAttributeProviders_whenResolveCompletedAtFirstProvider_thenSecondProviderShouldBeSkipped() {
	BindingAttributeProvider<View> bindingAttributeProvider1 = addNewCandidateBindingAttributeProvider();
	addNewCandidateBindingAttributeProvider();
	PendingAttributesForView pendingAttributesForView = mock(PendingAttributesForView.class);
	when(pendingAttributesForView.isEmpty()).thenReturn(true);

	bindingAttributeResolver.resolve(pendingAttributesForView);

	bindingAttributeResolver.assertBindingAttributeProvidersInvolvedResolving(bindingAttributeProvider1);
    }

    private BindingAttributeProvider<View> addNewCandidateBindingAttributeProvider() {
	BindingAttributeProvider<View> bindingAttributeProvider = mock(BindingAttributeProvider.class);
	candidateBindingAttributeProviders.add(bindingAttributeProvider);
	return bindingAttributeProvider;
    }

    private static class BindingAttributeResolverForTest extends BindingAttributeResolver {
	private Set<BindingAttributeProvider<View>> actualBindingAttributeProvidersInvolvedResolving;

	public BindingAttributeResolverForTest(List<BindingAttributeProvider<? extends View>> candidateBindingAttributeProviders) {
	    providersResolver = mock(BindingAttributeProvidersResolver.class);
	    when(providersResolver.getCandidateProviders(any(View.class))).thenReturn(candidateBindingAttributeProviders);

	    actualBindingAttributeProvidersInvolvedResolving = Sets.newHashSet();
	}

	@Override
	Collection<ViewAttribute> resolveByBindingAttributeProvider(PendingAttributesForView pendingAttributesForView,
		BindingAttributeProvider<View> bindingAttributeProvider) {
	    actualBindingAttributeProvidersInvolvedResolving.add(bindingAttributeProvider);
	    return Collections.emptyList();
	}

	public void assertBindingAttributeProvidersInvolvedResolving(
		BindingAttributeProvider<View>... expectedBindingAttributeProvidersInvolvedResolving) {
	    assertThat(Sets.newHashSet(expectedBindingAttributeProvidersInvolvedResolving), 
		    equalTo(actualBindingAttributeProvidersInvolvedResolving));
	}

    }
}
