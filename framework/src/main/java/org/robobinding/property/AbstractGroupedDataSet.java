package org.robobinding.property;

import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModel;
import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModelFactory;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public abstract class AbstractGroupedDataSet implements PropertyChangeListener {
    private final RefreshableGroupedItemPresentationModelFactory factory;
    private final AbstractGetSet<Object> getSet;

    private boolean isDataSetNotInitialized;
    private Object dataSet;

    @SuppressWarnings("unchecked")
    public AbstractGroupedDataSet(RefreshableGroupedItemPresentationModelFactory factory, AbstractGetSet<?> getSet) {
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

    public RefreshableGroupedItemPresentationModel newRefreshableGroupedItemPresentationModel() {
        return factory.create();
    }

    @Override
    public void propertyChanged() {
        updateDataSet();
    }

    public abstract int size();
    public abstract int sizeOfGroup(int groupPosition);
    public abstract Object getChild(int groupPosition, int childPosition);
}