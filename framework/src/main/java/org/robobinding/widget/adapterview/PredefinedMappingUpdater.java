package org.robobinding.widget.adapterview;

import java.util.Collection;

import org.robobinding.PredefinedPendingAttributesForView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface PredefinedMappingUpdater {
	void updateViewMappings(Collection<PredefinedPendingAttributesForView> viewMappings);
}
