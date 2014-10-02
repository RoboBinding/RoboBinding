package org.robobinding.codegen.samples;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.robobinding.function.Function;
import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.SimpleProperty;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class StringItemPresentationModel_IPM extends AbstractItemPresentationModelObject {
	public StringItemPresentationModel_IPM(StringItemPresentationModel itemPresentationModel, PresentationModelChangeSupport changeSupport) {
		super(itemPresentationModel, changeSupport);
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
	public Map<String, Set<String>> propertyDependencies() {
		return Collections.emptyMap();
	}

	@Override
	public Set<Method> eventMethods() {
		return Collections.emptySet();
	}

	@Override
	public SimpleProperty tryToCreateProperty(String propertyName) {
		return null;
	}

	@Override
	public DataSetProperty tryToCreateDataSetProperty(String propertyName) {
		return null;
	}

	@Override
	public Function tryToCreateFunction(Method method) {
		return null;
	}

}
