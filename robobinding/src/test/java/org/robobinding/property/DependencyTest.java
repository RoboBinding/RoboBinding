package org.robobinding.property;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.DependsOnStateOf;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependencyTest {
    private ObservableTestBean observableTestBean;
    private PresentationModelPropertyChangeListener listener;

    @Before
    public void setUp() {
	observableTestBean = new ObservableTestBean();
	listener = new MockPresentationModelPropertyChangeListener();
    }

    @Test
    public void whenCreateWithValidDependentProperties_thenSuccessful() {
	createDependency(ObservableTestBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateWithDependingOnSelf_thenThrowException() {
	createDependency(ObservableTestBean.PROPERTY_DEPENDING_ON_SELF);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateWithSomeNonExistingDependentProperties_thenThrowException() {
	createDependency(ObservableTestBean.PROPERTY_WITH_SOME_NONEXISTING_DEPENDENT_PROPERTIES);
    }

    public void givenDependency_whenAddListenerToDependentProperties_thenListenersAddedCorrectly() {
	Dependency dependency = createDependency(ObservableTestBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);

	dependency.addListenerToDependentProperties(listener);

	assertHasListenersToProperties(ObservableTestBean.DEPENDENT_PROPERTY, ObservableTestBean.PROPERTY);
    }

    private void assertHasListenersToProperties(String... propertyNames) {
	for (String propertyName : propertyNames) {
	    Assert.assertTrue(observableTestBean.hasPropertyChangeListener(propertyName, listener));
	}
    }

    @Test
    public void givenDependencyAndListenerToItsDependentProperties_whenRemoveListener_thenListenerRemovedOffDependentProperties() {
	Dependency dependency = createDependency(ObservableTestBean.PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES);
	dependency.addListenerToDependentProperties(listener);

	dependency.removeListenerOffDependentProperties(listener);

	assertHasNoListenersToProperties(ObservableTestBean.DEPENDENT_PROPERTY, ObservableTestBean.PROPERTY);
    }

    private void assertHasNoListenersToProperties(String... propertyNames) {
	for (String propertyName : propertyNames) {
	    Assert.assertFalse(observableTestBean.hasPropertyChangeListener(propertyName, listener));
	}
    }

    @Test
    public void givenDependencyWithDuplicatedDependentProperties_whenAddListener_thenListenerAddedToDependentPropertyOnceOnly() {
	Dependency dependency = createDependency(ObservableTestBean.PROPERTY_WITH_DUPLICATED_DEPENDENT_PROPERTIES);

	dependency.addListenerToDependentProperties(listener);

	assertListenerRegisteredNumTimes(ObservableTestBean.DEPENDENT_PROPERTY, 1);
    }

    private void assertListenerRegisteredNumTimes(String propertyName, int numTimes) {
	observableTestBean.isPropertyChangeListenerRegisteredNumTimes(propertyName, listener, numTimes);
    }

    private Dependency createDependency(String propertyName) {
	PropertyAccessor<Boolean> propertyAccessor = PropertyAccessorUtils.createPropertyAccessor(observableTestBean.getClass(), propertyName);
	ObservableBean observableBean = new ObservableBean(observableTestBean);
	Dependency dependency = new Dependency(observableBean, propertyAccessor, getAvailablePropertyNames());
	return dependency;
    }

    private List<String> getAvailablePropertyNames() {
	return PropertyUtils.getPropertyNames(ObservableTestBean.class);
    }

    static class ObservableTestBean implements ObservableProperties {
	public static final String PROPERTY = "property";
	public static final String PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES = "propertyWithValidDependentProperties";
	public static final String DEPENDENT_PROPERTY = "dependentProperty";
	public static final String PROPERTY_WITH_DUPLICATED_DEPENDENT_PROPERTIES = "propertyWithDuplicatedDependentProperties";
	public static final String PROPERTY_WITH_SOME_NONEXISTING_DEPENDENT_PROPERTIES = "propertyWithSomeNonExistingDependentProperties";
	public static final String PROPERTY_DEPENDING_ON_SELF = "propertyDependingOnSelf";

	private PresentationModelPropertyChangeSupport propertyChangeSupport;

	public ObservableTestBean() {
	    propertyChangeSupport = new PresentationModelPropertyChangeSupport(this);
	}

	public boolean getProperty() {
	    return true;
	}

	@DependsOnStateOf({ DEPENDENT_PROPERTY, PROPERTY })
	public boolean getPropertyWithValidDependentProperties() {
	    return true;
	}

	public boolean getDependentProperty() {
	    return true;
	}

	@DependsOnStateOf({ DEPENDENT_PROPERTY, DEPENDENT_PROPERTY })
	public boolean getPropertyWithDuplicatedDependentProperties() {
	    return true;
	}

	@DependsOnStateOf({ PROPERTY, "nonExistingProperty1" })
	public boolean getPropertyWithSomeNonExistingDependentProperties() {
	    return true;
	}

	@DependsOnStateOf(PROPERTY_DEPENDING_ON_SELF)
	public boolean getPropertyDependingOnSelf() {
	    return true;
	}

	@Override
	public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	    propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	public boolean hasPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	    PresentationModelPropertyChangeListeners propertyChangeListeners = propertyChangeSupport.getPropertyChangeListeners(propertyName);
	    return propertyChangeListeners.contains(listener);
	}

	public boolean isPropertyChangeListenerRegisteredNumTimes(String propertyName, PresentationModelPropertyChangeListener listener,
		int expectedNumTimes) {
	    PresentationModelPropertyChangeListeners propertyChangeListeners = propertyChangeSupport.getPropertyChangeListeners(propertyName);

	    int actualNumTimes = 0;
	    for (PresentationModelPropertyChangeListener propertyChangeListener : propertyChangeListeners.listeners) {
		if (propertyChangeListener == listener) {
		    actualNumTimes++;
		}
	    }
	    return actualNumTimes == expectedNumTimes;
	}
    }
}
