package org.robobinding.widget.expandablelistview;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class GroupCollapseEvent {
    private final int groupPosition;

    GroupCollapseEvent(int groupPosition) {
        this.groupPosition = groupPosition;
    }
}
