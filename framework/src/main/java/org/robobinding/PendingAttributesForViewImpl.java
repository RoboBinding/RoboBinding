package org.robobinding;

import java.util.Collection;
import java.util.Map;

import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.util.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PendingAttributesForViewImpl implements PendingAttributesForView {
	private Object view;
	Map<String, String> attributeMappings;
	private ViewResolutionErrorsException resolutionErrors;
	private boolean isUnrecognizedAttributesAppended;

	public PendingAttributesForViewImpl(Object view, Map<String, String> attributeMappings) {
		this.view = view;
		this.attributeMappings = Maps.newHashMap(attributeMappings);
		resolutionErrors = new ViewResolutionErrorsException(view);
		isUnrecognizedAttributesAppended = false;
	}

	@Override
	public Object getView() {
		return view;
	}

	@Override
	public boolean isEmpty() {
		return attributeMappings.isEmpty();
	}

	@Override
	public ViewResolutionErrors getResolutionErrors() {
		if (!isUnrecognizedAttributesAppended) {
			resolutionErrors.addUnrecognizedAttributes(attributeMappings.keySet());
			isUnrecognizedAttributesAppended = true;
		}
		return resolutionErrors;
	}

	@Override
	public void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver) {
		if (attributeMappings.containsKey(attribute)) {
			String attributeValue = attributeMappings.get(attribute);
			try {
				attributeResolver.resolve(view, attribute, attributeValue);
			} catch (AttributeResolutionException e) {
				resolutionErrors.addAttributeError(e);
			}

			attributeMappings.remove(attribute);
		}
	}

	@Override
	public void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver) {
		if (hasOneOfAttributes(attributeGroup)) {
			Map<String, String> presentAttributeMappings = findPresentAttributeMappings(attributeGroup);
			Collection<String> presentAttributes = presentAttributeMappings.keySet();

			try {
				attributeGroupResolver.resolve(view, attributeGroup, presentAttributeMappings);
			} catch (MissingRequiredAttributesException e) {
				resolutionErrors.addMissingRequiredAttributeError(e);
			} catch (AttributeResolutionException e) {
				resolutionErrors.addAttributeError(e);
			} catch (GroupedAttributeResolutionException e) {
				resolutionErrors.addGroupedAttributeError(e);
			}

			removeAttributes(presentAttributes);
		}
	}

	private boolean hasOneOfAttributes(String[] attributes) {
		for (String attribute : attributes) {
			if (attributeMappings.containsKey(attribute)) {
				return true;
			}
		}
		return false;
	}

	private Map<String, String> findPresentAttributeMappings(String[] attributes) {
		Map<String, String> presentAttributeMappings = Maps.newHashMap();
		for (String attribute : attributes) {
			if (attributeMappings.containsKey(attribute)) {
				String attributeValue = attributeMappings.get(attribute);
				presentAttributeMappings.put(attribute, attributeValue);
			}
		}
		return presentAttributeMappings;
	}

	private void removeAttributes(Collection<String> attributes) {
		for (String attribute : attributes) {
			attributeMappings.remove(attribute);
		}
	}
}
