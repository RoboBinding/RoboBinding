package org.robobinding.property;

import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModelFactory;

import java.util.List;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class ListGroupedDataSet extends AbstractGroupedDataSet {
	public ListGroupedDataSet(RefreshableGroupedItemPresentationModelFactory factory, AbstractGetSet<?> getSet) {
		super(factory, getSet);
	}
	
	@Override
	public int size() {
		if (isDataSetNull())
			return 0;

		List<Object> list = getDataSet();
		return list.size();
	}

	@Override
	public int sizeOfGroup(int groupPosition) {
		if (isDataSetNull())
			return 0;

		List<List<Object>> list = getDataSet();
		return list.get(groupPosition).size();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		List<List<Object>> list = getDataSet();
		return list.get(groupPosition).get(childPosition);
	}
}
