package org.robobinding.widget.adapterview;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface ItemLayoutSelector {
	int getViewTypeCount();

	int getItemViewType(Object item, int position);

	int selectLayout(int viewType);
}
