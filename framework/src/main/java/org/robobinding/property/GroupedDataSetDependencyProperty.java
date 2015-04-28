package org.robobinding.property;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class GroupedDataSetDependencyProperty extends GroupedDataSetPropertyValueModelWrapper {
    private final Dependency dependency;
    private final GroupedDataSetProperty groupedDataSetProperty;

    public GroupedDataSetDependencyProperty(GroupedDataSetProperty groupedDataSetProperty, Dependency dependency) {
        super(groupedDataSetProperty);
        this.groupedDataSetProperty = groupedDataSetProperty;
        this.dependency = dependency;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        dependency.addListenerToDependentProperties(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        super.removePropertyChangeListener(listener);
        dependency.removeListenerOffDependentProperties(listener);
    }

    @Override
    public String toString() {
        return groupedDataSetProperty.decriptionWithDependencies(dependency.getDependentProperties());
    }
}