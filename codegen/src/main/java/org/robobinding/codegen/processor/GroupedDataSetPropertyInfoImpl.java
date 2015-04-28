package org.robobinding.codegen.processor;

import org.robobinding.codegen.GroupedDataSetPropertyInfo;
import org.robobinding.property.*;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by jihunlee on 4/25/15.
 */
public class GroupedDataSetPropertyInfoImpl  implements GroupedDataSetPropertyInfo {
    private final String name;
    private final MethodElementWrapper getter;
    private final GroupedItemPresentationModelAnnotationMirror annotation;
    private final String groupedItemPresentationModelObjectTypeName;

    public GroupedDataSetPropertyInfoImpl(String name, MethodElementWrapper getter,
                                   GroupedItemPresentationModelAnnotationMirror annotation,
                                   String groupedItemPresentationModelObjectTypeName) {
        this.name = name;
        this.getter = getter;
        this.annotation = annotation;
        this.groupedItemPresentationModelObjectTypeName = groupedItemPresentationModelObjectTypeName;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String type() {
        return getter.returnTypeName();
    }

    @Override
    public String getter() {
        return getter.methodName();
    }

    @Override
    public Class<? extends AbstractGroupedDataSet> groupedDataSetImplementationType() {
        if(getter.isReturnTypeAssignableTo(List.class)) {
            return ListGroupedDataSet.class;
        } else {
            throw new RuntimeException(
                    MessageFormat.format("Property {0} has an unsupported grouped dataSet type", getter.toString()));
        }
    }

    @Override
    public String groupedItemPresentationModelTypeName() {
        return annotation.groupedItemPresentationModelTypeName();
    }

    @Override
    public boolean isCreatedByFactoryMethod() {
        return annotation.hasFactoryMethod();
    }

    @Override
    public String factoryMethod() {
        return annotation.factoryMethod();
    }

    @Override
    public String groupedItemPresentationModelObjectTypeName() {
        return groupedItemPresentationModelObjectTypeName;
    }
}