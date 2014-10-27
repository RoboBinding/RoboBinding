package org.robobinding.property;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractDataSet implements PropertyChangeListener {
	private final RefreshableItemPresentationModelFactory factory;
	private final AbstractGetSet<Object> getSet;
	
	private boolean isDataSetNotInitialized;
	private Object dataSet;

	@SuppressWarnings("unchecked")
	public AbstractDataSet(RefreshableItemPresentationModelFactory factory, AbstractGetSet<?> getSet) {
		this.factory = factory;
		this.getSet = (AbstractGetSet<Object>)getSet;

		isDataSetNotInitialized = true;
	}

	@SuppressWarnings("unchecked")
	protected <DataSetType> DataSetType getDataSet() {
		if (isDataSetNotInitialized) {
			updateDataSet();
			isDataSetNotInitialized = false;
		}
		return (DataSetType) dataSet;
	}

	protected void updateDataSet() {
		dataSet = getSet.getValue();
	}

	protected boolean isDataSetNull() {
		return getDataSet() == null;
	}

	public RefreshableItemPresentationModel newRefreshableItemPresentationModel() {
		return factory.create();
	}

	@Override
	public void propertyChanged() {
		updateDataSet();
	}
	
	public abstract int size();
	public abstract Object getItem(int position);
}
