package org.robobinding.widget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;

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
public abstract class AbstractMultiTypePropertyViewAttributeTest<T extends MultiTypePropertyViewAttribute<? extends View>> {
	private T attribute;
	private Map<Class<?>, Class<? extends PropertyViewAttribute<? extends View, ?>>> propertyTypeToViewAttributeMappings;

	@Before
	public final void populateMappings() {
		this.attribute = createAttribute();

		propertyTypeToViewAttributeMappings = Maps.newHashMap();
		setTypeMappingExpectations();
	}

	protected T createAttribute() {
		ParameterizedType attributeType = (ParameterizedType) getClass().getGenericSuperclass();
		return ParameterizedTypeUtils.createTypeArgument(attributeType, 0);
	}

	protected abstract void setTypeMappingExpectations();

	@Test
	public void givenPropertyType_whenCreate_thenReturnExpectedPropertyViewAttributeInstance() {
		for (Class<?> propertyType : propertyTypeToViewAttributeMappings.keySet()) {
			PropertyViewAttribute<?, ?> propertyViewAttribute = attribute.create(null, propertyType);

			assertThat(propertyViewAttribute, instanceOf(propertyTypeToViewAttributeMappings.get(propertyType)));
		}
	}

	protected TypeMappingBuilder forPropertyType(Class<?> propertyType) {
		return new TypeMappingBuilder(propertyType);
	}

	protected class TypeMappingBuilder {
		private final Class<?> propertyType;

		public TypeMappingBuilder(Class<?> propertyType) {
			this.propertyType = propertyType;
		}

		@SuppressWarnings("unchecked")
		public void expectAttribute(@SuppressWarnings("rawtypes") Class<? extends PropertyViewAttribute> attributeClass) {
			propertyTypeToViewAttributeMappings.put(propertyType, (Class<? extends PropertyViewAttribute<? extends View, ?>>) attributeClass);
		}
	}
}
