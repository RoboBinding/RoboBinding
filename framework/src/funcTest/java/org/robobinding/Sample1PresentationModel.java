package org.robobinding;

import java.util.List;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Sample1PresentationModel {
	private boolean property;
	private List<String> source;

	public Sample1PresentationModel() {
		source = Lists.newArrayList("aa", "bb", "cc");
	}

	public boolean isProperty() {
		return property;
	}

	public void setProperty(boolean property) {
		this.property = property;
	}

	@ItemPresentationModel(StringItemPresentationModel.class)
	public List<String> getSource() {
		return source;
	}

	public static class StringItemPresentationModel implements org.robobinding.itempresentationmodel.ItemPresentationModel<String> {
		@Override
		public void updateData(String bean, ItemContext itemContext) {
		}
	}
}