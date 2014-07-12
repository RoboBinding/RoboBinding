package org.robobinding.binder;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.impl.BindingAttributeMapperAdapter;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;

import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class BindingAttributeMappingsProviderResolverTest {
    private BindingAttributeMappingsProviderResolver bindingAttributeMappingsProviderResolver;

    @Mock
    private BindingAttributeMapper<ViewWithNoParent> mapperForViewWithNoParent;
    @Mock
    private BindingAttributeMapper<ViewWithParents> mapperForViewWithParents;
    @Mock
    private BindingAttributeMapper<GrandparentView> mapperForGrandparentView;

    @Before
    public void setUp() {
	initMocks(this);

	BindingAttributeMappingsProviderMapBuilder bindingAttributeMappingsProviderMapBuilder = new BindingAttributeMappingsProviderMapBuilder();
	bindingAttributeMappingsProviderMapBuilder.put(ViewWithNoParent.class, mapperForViewWithNoParent);
	bindingAttributeMappingsProviderMapBuilder.put(ViewWithParents.class, mapperForViewWithParents);
	bindingAttributeMappingsProviderMapBuilder.put(GrandparentView.class, mapperForGrandparentView);
	bindingAttributeMappingsProviderResolver = new BindingAttributeMappingsProviderResolver(
		bindingAttributeMappingsProviderMapBuilder.build());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenViewWithNoParent_whenGetCandidateProviders_thenOnlyProviderForViewShouldBeReturned() {
	ViewWithNoParent viewWithNoParent = new ViewWithNoParent();

	Iterable<BindingAttributeMappingsProvider<? extends View>> candidateProviders = bindingAttributeMappingsProviderResolver
		.findCandidateProviders(viewWithNoParent);

	assertThat(candidateProviders, equalTo(newProvidersInOrder(mapperForViewWithNoParent)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenViewWithParents_whenGetCandidateProviders_thenProvidersForViewAndItsParentsShouldBeReturned() {
	ViewWithParents viewWithParents = new ViewWithParents();

	Iterable<BindingAttributeMappingsProvider<? extends View>> candidateProviders = bindingAttributeMappingsProviderResolver
		.findCandidateProviders(viewWithParents);

	assertThat(candidateProviders, equalTo(newProvidersInOrder(mapperForViewWithParents, mapperForGrandparentView)));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Iterable<BindingAttributeMappingsProvider<? extends View>> newProvidersInOrder(BindingAttributeMapper<? extends View>... mappers) {
	List<BindingAttributeMappingsProvider<? extends View>> providers = newArrayList();
	for (BindingAttributeMapper<? extends View> mapper : mappers) {
	    providers.add(new BindingAttributeMapperAdapter(mapper));
	}
	return providers;
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
