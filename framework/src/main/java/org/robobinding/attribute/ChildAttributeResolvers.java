package org.robobinding.attribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildAttributeResolvers {
	private static final ChildAttributeResolvers INSTANCE = new ChildAttributeResolvers();

	private PropertyAttributeParser propertyAttributeParser;
	private PropertyAttributeResolver propertyAttributeResolver;
	private ValueModelAttributeResolver valueModelAttributeResolver;
	private StaticResourceAttributeResolver staticResourceAttributeResolver;
	private PredefinedMappingsAttributeResolver predefinedMappingsAttributeResolver;

	private ChildAttributeResolvers() {
		propertyAttributeParser = new PropertyAttributeParser();

		propertyAttributeResolver = new PropertyAttributeResolver(propertyAttributeParser);
		valueModelAttributeResolver = new ValueModelAttributeResolver(propertyAttributeParser);
		staticResourceAttributeResolver = new StaticResourceAttributeResolver(propertyAttributeParser);
		predefinedMappingsAttributeResolver = new PredefinedMappingsAttributeResolver();
	}

	public static ChildAttributeResolver propertyAttributeResolver() {
		return INSTANCE.propertyAttributeResolver;
	}

	public static ChildAttributeResolver valueModelAttributeResolver() {
		return INSTANCE.valueModelAttributeResolver;
	}

	public static ChildAttributeResolver staticResourceAttributeResolver() {
		return INSTANCE.staticResourceAttributeResolver;
	}

	public static ChildAttributeResolver predefinedMappingsAttributeResolver() {
		return INSTANCE.predefinedMappingsAttributeResolver;
	}

	public static <T extends Enum<T>> ChildAttributeResolver enumChildAttributeResolver(Class<T> enumClass) {
		return new EnumChildAttributeResolver<T>(enumClass);
	}

	static class PropertyAttributeResolver implements ChildAttributeResolver {
		private PropertyAttributeParser propertyAttributeParser;

		public PropertyAttributeResolver(PropertyAttributeParser propertyAttributeParser) {
			this.propertyAttributeParser = propertyAttributeParser;
		}

		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue) {
			return propertyAttributeParser.parse(attribute, attributeValue);
		}

	}

	private static class ValueModelAttributeResolver implements ChildAttributeResolver {
		private PropertyAttributeParser propertyAttributeParser;

		public ValueModelAttributeResolver(PropertyAttributeParser propertyAttributeParser) {
			this.propertyAttributeParser = propertyAttributeParser;
		}

		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue) {
			return propertyAttributeParser.parseAsValueModelAttribute(attribute, attributeValue);
		}

	}

	private static class StaticResourceAttributeResolver implements ChildAttributeResolver {
		private PropertyAttributeParser propertyAttributeParser;

		public StaticResourceAttributeResolver(PropertyAttributeParser propertyAttributeParser) {
			this.propertyAttributeParser = propertyAttributeParser;
		}

		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue) {
			return propertyAttributeParser.parseAsStaticResourceAttribute(attribute, attributeValue);
		}

	}

	private static class PredefinedMappingsAttributeResolver implements ChildAttributeResolver {
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue) {
			return new PredefinedMappingsAttribute(attribute, attributeValue);
		}

	}

	static class EnumChildAttributeResolver<T extends Enum<T>> implements ChildAttributeResolver {
		private final Class<T> enumClass;

		public EnumChildAttributeResolver(Class<T> enumClass) {
			this.enumClass = enumClass;
		}

		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue) {
			return new EnumAttribute<T>(attribute, attributeValue, enumClass);
		}

	}
}
