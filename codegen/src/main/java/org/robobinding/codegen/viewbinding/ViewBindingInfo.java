package org.robobinding.codegen.viewbinding;

import java.util.Collections;
import java.util.List;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingInfo {
	private final String viewBindingTypeName;
	private final String viewBindingObjectTypeName;
	private final Class<?> viewType;
	private final List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList;
	
	public ViewBindingInfo(String viewBindingTypeName, String viewBindingObjectTypeName, 
			Class<?> viewType,
			List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList) {
		this.viewBindingTypeName = viewBindingTypeName;
		this.viewBindingObjectTypeName = viewBindingObjectTypeName;
		this.viewType = viewType;
		this.simpleOneWayPropertyInfoList = simpleOneWayPropertyInfoList;
	}
	
	public String viewBindingTypeName() {
		return viewBindingTypeName;
	}
	
	public String viewBindingObjectTypeName() {
		return viewBindingObjectTypeName;
	}
	
	public Class<?> viewType() {
		return viewType;
	}

	public List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList() {
		return Collections.unmodifiableList(simpleOneWayPropertyInfoList);
	}
}
