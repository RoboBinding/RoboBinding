package org.robobinding.codegen.samples;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.robobinding.function.Function;
import org.robobinding.function.MethodDescriptor;
import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.SimpleProperty;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class StringItemPresentationModelPOC_IPM extends AbstractItemPresentationModelObject {
	private final StringItemPresentationModelPOC itemPresentationModel;
	public StringItemPresentationModelPOC_IPM(StringItemPresentationModelPOC itemPresentationModel) {
		super(itemPresentationModel);
		
		this.itemPresentationModel = itemPresentationModel;
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
	public Set<MethodDescriptor> eventMethods() {
		return Collections.emptySet();
	}

	@Override
	public SimpleProperty tryToCreateProperty(String propertyName) {
		itemPresentationModel.toString();
		return null;
	}

	@Override
	public DataSetProperty tryToCreateDataSetProperty(String propertyName) {
		return null;
	}

	@Override
	public Function tryToCreateFunction(MethodDescriptor methodDescriptor) {
		return null;
	}

}
