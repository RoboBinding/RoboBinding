package org.robobinding.viewattribute.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractVisibilityAttribute.IntegerVisibilityAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class IntegerVisibilityAttributeTest extends AbstractPropertyViewAttributeTest<View, IntegerVisibilityAttribute> {
    private AbstractVisibility visibility;

    @Test
    public void whenUpdatingValueModel_thenViewShouldReflectChanges() {
	int visibilityValue = RandomValues.anyVisibility();

	attribute.valueModelUpdated(visibilityValue);

	verify(visibility).setVisibility(visibilityValue);
    }

    @Override
    protected IntegerVisibilityAttribute createAttribute() {
	visibility = mock(AbstractVisibility.class);
        return new IntegerVisibilityAttribute(visibility);
    }
}
