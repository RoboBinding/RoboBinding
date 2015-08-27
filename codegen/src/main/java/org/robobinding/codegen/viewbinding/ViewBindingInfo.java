package org.robobinding.codegen.viewbinding;

import java.util.Collections;
import java.util.List;

import org.robobinding.codegen.apt.element.WrappedTypeElement;

import com.google.common.base.Objects;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingInfo {
	private final String viewBindingTypeName;
	private final String viewBindingObjectTypeName;
	private final WrappedTypeElement viewType;
	private final List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList;
	private final List<TwoWayPropertyInfo> twoWayPropertyInfoList;
	
	public ViewBindingInfo(String viewBindingTypeName, String viewBindingObjectTypeName, 
			WrappedTypeElement viewType,
			List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList,
			List<TwoWayPropertyInfo> twoWayPropertyInfoList) {
		this.viewBindingTypeName = viewBindingTypeName;
		this.viewBindingObjectTypeName = viewBindingObjectTypeName;
		this.viewType = viewType;
		this.simpleOneWayPropertyInfoList = simpleOneWayPropertyInfoList;
		this.twoWayPropertyInfoList = twoWayPropertyInfoList;
	}

	public String viewBindingTypeName() {
		return viewBindingTypeName;
	}
	
	public String viewBindingObjectTypeName() {
		return viewBindingObjectTypeName;
	}
	
	public String viewType() {
		return viewType.qName();
	}

	public List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList() {
		return Collections.unmodifiableList(simpleOneWayPropertyInfoList);
	}

	public List<TwoWayPropertyInfo> twoWayPropertyInfoList() {
		return Collections.unmodifiableList(twoWayPropertyInfoList);
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
				&& Objects.equal(simpleOneWayPropertyInfoList, that.simpleOneWayPropertyInfoList)
				&& Objects.equal(twoWayPropertyInfoList, that.twoWayPropertyInfoList);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(viewBindingTypeName)
				+ Objects.hashCode(viewBindingObjectTypeName)
				+ Objects.hashCode(viewType)
				+ Objects.hashCode(simpleOneWayPropertyInfoList)
				+ Objects.hashCode(twoWayPropertyInfoList);
	}
}
