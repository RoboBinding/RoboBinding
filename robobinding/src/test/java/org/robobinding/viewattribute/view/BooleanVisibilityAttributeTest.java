package org.robobinding.viewattribute.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.view.AbstractVisibilityAttribute.BooleanVisibilityAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BooleanVisibilityAttributeTest extends AbstractPropertyViewAttributeTest<View, BooleanVisibilityAttribute> {
    private AbstractVisibility visibility;

    @Test
    public void whenIsSetToVisible_thenMakeVisibleIsCalled() {
	boolean visible = true;

	attribute.valueModelUpdated(visible);

	verify(visibility).makeVisible();
    }

    @Test
    public void whenIsSetToGone_thenMakeGoneIsCalled() {
	boolean gone = false;

	attribute.valueModelUpdated(gone);

	verify(visibility).makeGone();
    }

    @Override
    protected BooleanVisibilityAttribute createAttribute() {
	visibility = mock(AbstractVisibility.class);
        return new BooleanVisibilityAttribute(visibility);
    }
}
