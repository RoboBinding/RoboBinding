package org.robobinding.widget.adapterview;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.robobinding.itempresentationmodel.ViewTypeSelectable;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;
import org.robobinding.util.RandomValues;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MultiItemLayoutSelectorTest {
	@Rule
	public ExpectedException thrownException = ExpectedException.none();

	private List<Integer> itemLayoutIds;
	
	@Before
	public void setUp() {
		itemLayoutIds = Lists.newArrayList(5, 6);
	}
	
	@Test
	public void shouldViewTypeCountEqualsNumItemLayouts() {
		MultiItemLayoutSelector layoutSelector = new MultiItemLayoutSelector(itemLayoutIds, null);
		
		assertThat(layoutSelector.getViewTypeCount(), equalTo(itemLayoutIds.size()));
	}
	
	@Test
	public void shouldReturnUserSelectedViewType() {
		int userSelectedViewType = RandomValues.nextInt(itemLayoutIds.size());
		ViewTypeSelectable viewTypeSelector = withUserSelectedViewType(userSelectedViewType);
		
		MultiItemLayoutSelector layoutSelector = new MultiItemLayoutSelector(itemLayoutIds, viewTypeSelector);
		int actualViewType = layoutSelector.getItemViewType(anyItem(), anyPosition());
		
		assertThat(actualViewType, equalTo(userSelectedViewType));
	}
	
	@SuppressWarnings("unchecked")
	private ViewTypeSelectable withUserSelectedViewType(int userSelectedViewType) {
		ViewTypeSelectable viewTypeSelector = mock(ViewTypeSelectable.class);
		when(viewTypeSelector.selectViewType(any(ViewTypeSelectionContext.class))).thenReturn(userSelectedViewType);
		return viewTypeSelector;
	}
	
	private Object anyItem() {
		return null;
	}
	
	private int anyPosition() {
		return 0;
	}
	
	@Test
	public void shouldThrowExceptionGivenInvalidUserSelectedViewType() {
		thrownException.expect(RuntimeException.class);
		thrownException.expectMessage("invalid selected view type");
		
		int invalidUserSelectedViewType = itemLayoutIds.size() + RandomValues.anyInteger();
		ViewTypeSelectable viewTypeSelector = withUserSelectedViewType(invalidUserSelectedViewType);
		
		MultiItemLayoutSelector layoutSelector = new MultiItemLayoutSelector(itemLayoutIds, viewTypeSelector);
		layoutSelector.getItemViewType(anyItem(), anyPosition());
	}
	
	@Test
	public void shouldReturnUserSelectedItemlayout() {
		int userSelectedViewType = RandomValues.nextInt(itemLayoutIds.size());
		ViewTypeSelectable viewTypeSelector = withUserSelectedViewType(userSelectedViewType);
		
		MultiItemLayoutSelector layoutSelector = new MultiItemLayoutSelector(itemLayoutIds, viewTypeSelector);
		int actualItemLayout = layoutSelector.selectLayout(userSelectedViewType);
		
		assertThat(actualItemLayout, equalTo(itemLayoutIds.get(userSelectedViewType)));
	}
}
