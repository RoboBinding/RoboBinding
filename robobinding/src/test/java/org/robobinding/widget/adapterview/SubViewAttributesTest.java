package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.grouped.AbstractGroupedViewAttributeTest;

import android.widget.ListView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class SubViewAttributesTest extends AbstractGroupedViewAttributeTest<ListView, SubViewAttributes<ListView>> {
    private final String presentationModelAttribute = "source";
    private final Attribute presentationModel = attribute("source={presentationModel_property}");

    private final String layoutAttribute = "layout";
    private final Attribute staticLayout = attribute("layout=@layout/staticLayout");

    private final String visibilityAttribute = "visibility";
    private final Attribute visibility = attribute("visibility={visibility_property}");

    @Test
    public void whenCreateAttributeWithoutPresentationModel_thenSuccessful() {
	givenAttributes(staticLayout);

	performInitialization();

	assertThatAttributesWereCreated(layoutAttribute, presentationModelAttribute);
    }

    @Test
    public void whenCreateAttributeWithPresentationModel_thenSuccessful() {
	givenAttributes(staticLayout, presentationModel);

	performInitialization();

	assertThatAttributesWereCreated(layoutAttribute, presentationModelAttribute);
    }

    @Test
    public void whenCreateAttributeWithVisibility_thenSuccessful() {
	givenAttributes(staticLayout, visibility);

	performInitialization();

	assertThatAttributesWereCreated(layoutAttribute, presentationModelAttribute, visibilityAttribute);
    }

    @Override
    protected SubViewAttributesForTest createAttributeUnderTest() {
	@SuppressWarnings("unchecked")
	SubViewAttributesStrategy<ListView> subViewAttributesStrategy = mock(SubViewAttributesStrategy.class);

	when(subViewAttributesStrategy.layoutAttribute()).thenReturn(layoutAttribute);
	when(subViewAttributesStrategy.subViewPresentationModelAttribute()).thenReturn(presentationModelAttribute);
	when(subViewAttributesStrategy.visibilityAttribute()).thenReturn(visibilityAttribute);

       return new SubViewAttributesForTest(subViewAttributesStrategy);
    }
}
