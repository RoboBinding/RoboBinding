package org.robobinding.codegen.viewbinding;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;

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

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof ViewBindingInfo))
			return false;

		final ViewBindingInfo that = (ViewBindingInfo) other;
		return Objects.equal(viewBindingTypeName, that.viewBindingTypeName)
				&& Objects.equal(viewBindingObjectTypeName, that.viewBindingObjectTypeName)
				&& Objects.equal(viewType, that.viewType)
				&& Objects.equal(simpleOneWayPropertyInfoList, that.simpleOneWayPropertyInfoList);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(viewBindingTypeName)
				+ Objects.hashCode(viewBindingObjectTypeName)
				+ Objects.hashCode(viewType)
				+ Objects.hashCode(simpleOneWayPropertyInfoList);
	}
}
