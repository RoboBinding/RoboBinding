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
package org.robobinding.viewattribute.adapterview;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robobinding.viewattribute.AbstractGroupedViewAttributeTest;

import android.widget.ListView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class SubViewAttributesTest extends AbstractGroupedViewAttributeTest<SubViewAttributesForTest> {
    private final String presentationModelAttribute = "source";
    private final Attribute presentationModel = attribute("source={presentationModel_property}");

    private final String layoutAttribute = "layout";
    private final Attribute staticLayout = attribute("layout=@layout/staticLayout");

    private final String visibilityAttribute = "visibility";
    private final Attribute visibility = attribute("visibility={visibility_property}");

    @Mock
    private SubViewAttributesStrategy<ListView> subViewAttributesStrategy;

    @Override
    @Before
    public void initialize() {
	MockitoAnnotations.initMocks(this);

	when(subViewAttributesStrategy.layoutAttribute()).thenReturn(layoutAttribute);
	when(subViewAttributesStrategy.subViewPresentationModelAttribute()).thenReturn(presentationModelAttribute);
	when(subViewAttributesStrategy.visibilityAttribute()).thenReturn(visibilityAttribute);

        super.initialize();
    }

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
       return new SubViewAttributesForTest(subViewAttributesStrategy);
    }
}
