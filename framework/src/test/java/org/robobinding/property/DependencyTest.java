package org.robobinding.property;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class DependencyTest {
	private ObservableTestBean bean;
	@Mock
	private PropertyChangeListener listener;

	@Before
	public void setUp() {
		bean = new ObservableTestBean();
	}

	@Test
	public void whenAddListener_thenListenersAddedToAllDependentProperties() {
		Set<String> dependentProperties = Sets.newHashSet("prop1", "prop2");
		Dependency dependency = new Dependency(bean, dependentProperties);

		dependency.addListenerToDependentProperties(listener);

		assertListenerOnProperties(dependentProperties);
	}

	private void assertListenerOnProperties(Set<String> propertyNames) {
		for (String propertyName : propertyNames) {
			assertThat(bean.propertyChangeListenerMap.get(propertyName), sameInstance(listener));
		}
	}

	@Test
	public void givenListenerOnDependentProperties_whenRemoveListener_thenListenerRemovedOffDependentProperties() {
		Set<String> dependentProperties = Sets.newHashSet("prop1", "prop2");
		Dependency dependency = new Dependency(bean, dependentProperties);
		dependency.addListenerToDependentProperties(listener);

		dependency.removeListenerOffDependentProperties(listener);

		assertNoListenerOnProperties(dependentProperties);
	}

	private void assertNoListenerOnProperties(Set<String> propertyNames) {
		for (String propertyName : propertyNames) {
			assertFalse(bean.propertyChangeListenerMap.containsKey(propertyName));
		}
	}

	public static class ObservableTestBean implements ObservableBean {
		private final Map<String, PropertyChangeListener> propertyChangeListenerMap;

		public ObservableTestBean() {
			propertyChangeListenerMap = Maps.newHashMap();
		}

		@Override
		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeListenerMap.put(propertyName, listener);
		}

		@Override
		public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeListenerMap.remove(propertyName);
		}
	}
}
