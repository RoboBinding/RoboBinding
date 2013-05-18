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
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.BindingAttributeProvidersResolver;
import org.robobinding.viewattribute.BindingAttributeProvider;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
@SuppressWarnings("unchecked")
public class BindingAttributeProvidersResolverTest {
    private BindingAttributeProvidersResolver bindingAttributeProvidersResolver;

    private BindingAttributeProvider<ViewWithNoParent> providerForViewWithNoParent;
    private BindingAttributeProvider<ViewWithParents> providerForViewWithParents;
    private BindingAttributeProvider<GrandparentView> providerForGrandparentView;

    @Before
    public void setUp() {
	providerForViewWithNoParent = mock(BindingAttributeProvider.class);
	providerForViewWithParents = mock(BindingAttributeProvider.class);
	providerForGrandparentView = mock(BindingAttributeProvider.class);

	bindingAttributeProvidersResolver = new BindingAttributeProvidersResolverForTest();
    }

    @Test
    public void givenViewWithNoParent_whenGetCandidateProviders_thenOnlyProviderForViewShouldBeReturned() {
	ViewWithNoParent viewWithNoParent = new ViewWithNoParent();

	Iterable<BindingAttributeProvider<? extends View>> candidateProviders = bindingAttributeProvidersResolver
		.getCandidateProviders(viewWithNoParent);

	assertThat(candidateProviders, equalTo(newProvidersInOrder(providerForViewWithNoParent)));
    }

    @Test
    public void givenViewWithParents_whenGetCandidateProviders_thenProvidersForViewAndItsParentsShouldBeReturned() {
	ViewWithParents viewWithParents = new ViewWithParents();

	Iterable<BindingAttributeProvider<? extends View>> candidateProviders = bindingAttributeProvidersResolver
		.getCandidateProviders(viewWithParents);

	assertThat(candidateProviders, equalTo(newProvidersInOrder(providerForViewWithParents, providerForGrandparentView)));
    }

    private Iterable<BindingAttributeProvider<? extends View>> newProvidersInOrder(BindingAttributeProvider<? extends View>... providers) {
	return Lists.newArrayList(providers);
    }

    private class BindingAttributeProvidersResolverForTest extends BindingAttributeProvidersResolver {
	public BindingAttributeProvidersResolverForTest() {
	    bindingAttributeProvidersMap = Maps.newHashMap();
	    bindingAttributeProvidersMap.put(ViewWithNoParent.class, providerForViewWithNoParent);
	    bindingAttributeProvidersMap.put(ViewWithParents.class, providerForViewWithParents);
	    bindingAttributeProvidersMap.put(GrandparentView.class, providerForGrandparentView);
	}
    }

    private static class ViewWithNoParent extends View {
	public ViewWithNoParent() {
	    super(mock(Context.class));
	}
    }

    private static class GrandparentView extends View {
	public GrandparentView() {
	    super(mock(Context.class));
	}
    }

    private static class ParentView extends GrandparentView {

    }

    private static class ViewWithParents extends ParentView {

    }
}
