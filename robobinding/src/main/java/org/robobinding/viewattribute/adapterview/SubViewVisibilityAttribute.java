package org.robobinding.viewattribute.adapterview;

import org.robobinding.viewattribute.view.AbstractVisibility;
import org.robobinding.viewattribute.view.AbstractVisibilityAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewVisibilityAttribute extends AbstractVisibilityAttribute {
    public SubViewVisibilityAttribute(AbstractVisibility subViewVisibility) {
	this.visibility = subViewVisibility;
    }
}
