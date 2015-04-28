package org.robobinding.codegen.samples;

import org.robobinding.function.Function;
import org.robobinding.function.MethodDescriptor;
import org.robobinding.presentationmodel.AbstractGroupedItemPresentationModelObject;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.GroupedDataSetProperty;
import org.robobinding.property.SimpleProperty;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by jihunlee on 4/27/15.
 */
public class StringGroupedItemPresentationModelPOC_GIPM extends AbstractGroupedItemPresentationModelObject {
    private final StringGroupedItemPresentationModelPOC groupedItemPresentationModel;

    public StringGroupedItemPresentationModelPOC_GIPM(
            StringGroupedItemPresentationModelPOC groupedItemPresentationModel) {
        super(groupedItemPresentationModel);

        this.groupedItemPresentationModel = groupedItemPresentationModel;
    }

    @Override
    public Set<String> propertyNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> dataSetPropertyNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> groupedDataSetPropertyNames() {
        return Collections.emptySet();
    }

    @Override
    public Map<String, Set<String>> propertyDependencies() {
        return Collections.emptyMap();
    }

    @Override
    public Set<MethodDescriptor> eventMethods() {
        return Collections.emptySet();
    }

    @Override
    public SimpleProperty tryToCreateProperty(String propertyName) {
        groupedItemPresentationModel.toString();
        return null;
    }

    @Override
    public DataSetProperty tryToCreateDataSetProperty(String propertyName) {
        return null;
    }

    @Override
    public GroupedDataSetProperty tryToCreateGroupedDataSetProperty(String propertyName) {
        return null;
    }

    @Override
    public Function tryToCreateFunction(MethodDescriptor method) {
        return null;
    }
}
