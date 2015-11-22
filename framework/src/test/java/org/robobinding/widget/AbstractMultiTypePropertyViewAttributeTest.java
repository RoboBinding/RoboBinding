package org.robobinding.widget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractMultiTypePropertyViewAttributeTest {
	private Map<Class<?>, Class<?>> propertyTypeToViewAttributeTypeMappings;

	@Before
	public final void populateMappings() {
		propertyTypeToViewAttributeTypeMappings = Maps.newHashMap();
		setTypeMappingExpectations();
	}

	protected abstract void setTypeMappingExpectations();

	@Test
	public void givenPropertyType_whenCreate_thenReturnExpectedPropertyViewAttributeInstance() {
		for (Class<?> propertyType : propertyTypeToViewAttributeTypeMappings.keySet()) {
			Object viewAttribute = createViewAttribute(propertyType);

			assertThat(viewAttribute, instanceOf(propertyTypeToViewAttributeTypeMappings.get(propertyType)));
		}
	}
	
	protected abstract Object createViewAttribute(Class<?> propertyType);
	
	protected TypeMappingBuilder forPropertyType(Class<?> propertyType) {
		return new TypeMappingBuilder(propertyType);
	}

	protected class TypeMappingBuilder {
		private final Class<?> propertyType;

		public TypeMappingBuilder(Class<?> propertyType) {
			this.propertyType = propertyType;
		}

		public void expectAttributeType(Class<?> attributeType) {
			propertyTypeToViewAttributeTypeMappings.put(propertyType, attributeType);
		}
	}
}
