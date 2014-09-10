package org.robobinding.property;

import java.util.Set;

import org.robobinding.annotation.DependsOnStateOf;

import com.google.common.collect.Sets;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependencyFactory {
    private final ObservableBean observableBean;
    private final DependencyValidation validation;
    
    public DependencyFactory(ObservableBean observableBean, DependencyValidation validation) {
        this.observableBean = observableBean;
        this.validation = validation;
    }

    public Dependency create(PropertyAccessor propertyAccessor) {
	    Set<String> dependentProperties = getDependentProperties(propertyAccessor);
	    validation.validate(propertyAccessor, dependentProperties);
	return new Dependency(observableBean, dependentProperties);
    }

    private Set<String> getDependentProperties(PropertyAccessor propertyAccessor) {
        DependsOnStateOf dependsOn = propertyAccessor.getAnnotation(DependsOnStateOf.class);
        return Sets.newHashSet(dependsOn.value());
    }

}
