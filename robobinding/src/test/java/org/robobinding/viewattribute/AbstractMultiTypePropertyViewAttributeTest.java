package org.robobinding.viewattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.robobinding.viewattribute.MockMultiTypePropertyViewAttributeConfigBuilder.aMultiTypePropertyViewAttributeConfig;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractMultiTypePropertyViewAttributeTest<T extends AbstractMultiTypePropertyViewAttribute<?>> {
    private T attribute;
    private Map<Class<?>, Class<? extends PropertyViewAttribute<? extends View>>> propertyTypeToViewAttributeMappings;

    @Before
    public final void populateMappings() {
	setupAttribute();

	propertyTypeToViewAttributeMappings = Maps.newHashMap();
	setTypeMappingExpectations();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setupAttribute() {
	ParameterizedType attributeType = (ParameterizedType) getClass().getGenericSuperclass();
	attribute = ParameterizedTypeUtils.createTypeArgument(attributeType, 0);

	MultiTypePropertyViewAttributeConfig<View> config = aMultiTypePropertyViewAttributeConfig(mock(View.class), "propertyName");

	attribute.initialize((MultiTypePropertyViewAttributeConfig) config);
    }

    protected abstract void setTypeMappingExpectations();

    @Test
    public void givenPropertyType_whenCreatePropertyViewAttribute_thenReturnExpectedInstance() {
	for (Class<?> propertyType : propertyTypeToViewAttributeMappings.keySet()) {
	    AbstractPropertyViewAttribute<?, ?> propertyViewAttribute = attribute.createPropertyViewAttribute(propertyType);

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

	public void expectAttribute(Class<? extends PropertyViewAttribute<? extends View>> attributeClass) {
	    propertyTypeToViewAttributeMappings.put(propertyType, attributeClass);
	}
    }
}
