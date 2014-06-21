package org.robobinding.widget.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.view.VisibilityAttribute.BooleanVisibilityAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BooleanVisibilityAttributeTest extends AbstractPropertyViewAttributeTest<View, BooleanVisibilityAttribute<View>> {
    private AbstractVisibility visibility;

    @Test
    public void whenUpdateToVisible_thenMakeVisibleIsCalled() {
	boolean visible = true;

	attribute.updateView(view, visible);

	verify(visibility).makeVisible();
    }

    @Test
    public void whenUpdateToGone_thenMakeGoneIsCalled() {
	boolean gone = false;

	attribute.updateView(view, gone);

	verify(visibility).makeGone();
    }

    @Override
    protected BooleanVisibilityAttribute<View> createAttribute() {
	visibility = mock(AbstractVisibility.class);
        return new BooleanVisibilityAttribute<View>(visibility);
    }
}
