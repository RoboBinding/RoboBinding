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
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;

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
    private BindingAttributeMappingsProviderResolver providersResolver;
    @Mock
    private ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider;

    @Test
    public void givenTwoCandidateProviders_whenFindCandidateResolvers_thenTwoResolversShouldBeReturned() {
	View view = mock(View.class);
	@SuppressWarnings("unchecked")
	Collection<BindingAttributeMappingsProvider<? extends View>> candiateProviders = Lists.<BindingAttributeMappingsProvider<? extends View>>newArrayList(
		mock(BindingAttributeMappingsProvider.class), mock(BindingAttributeMappingsProvider.class));
	when(providersResolver.findCandidateProviders(view)).thenReturn(candiateProviders);

	ByBindingAttributeMappingsResolverFinder finder = new ByBindingAttributeMappingsResolverFinder(providersResolver, viewAttributeBinderFactoryProvider);
	Iterable<ByBindingAttributeMappingsResolver> candidateResolvers = finder.findCandidateResolvers(view);

	assertThat(Iterables.size(candidateResolvers), is(2));
    }
}
