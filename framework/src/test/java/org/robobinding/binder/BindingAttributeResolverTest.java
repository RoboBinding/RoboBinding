package org.robobinding.binder;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.PendingAttributesForView;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class BindingAttributeResolverTest {
	@Mock
	private ByBindingAttributeMappingsResolverFinder byBindingAttributeMappingsResolverFinder;

	@Test
	public void givenTwoCandidateResolvers_whenResolve_thenBothResolversShouldInvolveResolving() {
		ByBindingAttributeMappingsResolver candidateResolver1 = mock(ByBindingAttributeMappingsResolver.class);
		ByBindingAttributeMappingsResolver candidateResolver2 = mock(ByBindingAttributeMappingsResolver.class);
		when(byBindingAttributeMappingsResolverFinder.findCandidates(any(View.class))).thenReturn(newArrayList(candidateResolver1, candidateResolver2));

		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeMappingsResolverFinder);
		PendingAttributesForView pendingAttributesForView = mock(PendingAttributesForView.class);
		when(pendingAttributesForView.isEmpty()).thenReturn(false);
		bindingAttributeResolver.resolve(pendingAttributesForView);

		verify(candidateResolver1).resolve(pendingAttributesForView);
		verify(candidateResolver2).resolve(pendingAttributesForView);
	}

	@Test
	public void givenTwoCandidateResolvers_whenResolveCompletedAtFirstResolver_thenSecondResolverShouldBeSkipped() {
		ByBindingAttributeMappingsResolver candidateResolver1 = mock(ByBindingAttributeMappingsResolver.class);
		ByBindingAttributeMappingsResolver candidateResolver2 = mock(ByBindingAttributeMappingsResolver.class);
		when(byBindingAttributeMappingsResolverFinder.findCandidates(any(View.class))).thenReturn(newArrayList(candidateResolver1, candidateResolver2));

		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeMappingsResolverFinder);
		PendingAttributesForView pendingAttributesForView = mock(PendingAttributesForView.class);
		when(pendingAttributesForView.isEmpty()).thenReturn(true);

		bindingAttributeResolver.resolve(pendingAttributesForView);

		verify(candidateResolver1).resolve(pendingAttributesForView);
		verify(candidateResolver2, never()).resolve(pendingAttributesForView);
	}
}
