/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.BindingAttributeProvider;

import android.view.View;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ByBindingAttributeMappingsResolverFinderTest {
    @Mock
    private BindingAttributeProvidersResolver providersResolver;
    
    @Test
    public void givenTwoCandidateProviders_whenFindCandidateResolvers_thenTwoResolversShouldBeReturned() {
	View view = mock(View.class);
	@SuppressWarnings("unchecked")
	Collection<BindingAttributeProvider<? extends View>> candiateProviders = Lists.<BindingAttributeProvider<? extends View>>newArrayList(
		mock(BindingAttributeProvider.class), mock(BindingAttributeProvider.class));
	when(providersResolver.getCandidateProviders(view)).thenReturn(candiateProviders);
	
	ByBindingAttributeMappingsResolverFinder finder = new ByBindingAttributeMappingsResolverFinder(providersResolver);
	Iterable<ByBindingAttributeMappingsResolver> candidateResolvers = finder.findCandidateResolvers(view);
	
	assertThat(Iterables.size(candidateResolvers), is(2));
    }
}
