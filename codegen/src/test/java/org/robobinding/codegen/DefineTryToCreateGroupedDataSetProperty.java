package org.robobinding.codegen;


import org.robobinding.annotation.GroupedItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;

import java.util.List;

/**
 * Created by jihunlee on 4/27/15.
 */
public class DefineTryToCreateGroupedDataSetProperty {
    @GroupedItemPresentationModel(value=StringGroupedItemPresentationModel.class)
    public List<List<String>> getDataSetProp() {
        return null;
    }

    @GroupedItemPresentationModel(value=StringGroupedItemPresentationModel.class, factoryMethod="createStringGroupedItemPresentationModel")
    public List<List<String>> getDataSetPropWithFactoryMethod() {
        return null;
    }

    public StringItemPresentationModel createStringGroupedItemPresentationModel() {
        return null;
    }
}
