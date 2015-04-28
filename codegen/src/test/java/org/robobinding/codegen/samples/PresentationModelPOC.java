package org.robobinding.codegen.samples;

import java.util.List;

import org.robobinding.annotation.GroupedItemPresentationModel;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.widget.view.AbstractViewEvent;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelPOC {
	private String prop1;
	private int prop2;
	private List<String> dataSetProp;
	private List<String> dataSetPropWithFactoryMethod;
	private List<List<String>> groupedDataSetProp;
	private List<List<String>> groupedDataSetPropWithFactoryMethod;

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}
	
	public int getProp2() {
		return prop2;
	}

	public void setProp2(int prop2) {
		this.prop2 = prop2;
	}

	@ItemPresentationModel(value = StringItemPresentationModelPOC.class)
	public List<String> getDataSetProp() {
		return dataSetProp;
	}
	
	@ItemPresentationModel(value = StringItemPresentationModelPOC.class, factoryMethod = "newStringItemPresentationModel")
	public List<String> getDataSetPropWithFactoryMethod() {
		return dataSetPropWithFactoryMethod;
	}

	@GroupedItemPresentationModel(value = StringGroupedItemPresentationModelPOC.class)
	public List<List<String>> getGroupedDataSetProp() {
		return groupedDataSetProp;
	}
	
	public StringItemPresentationModelPOC newStringItemPresentationModel() {
		return new StringItemPresentationModelPOC();
	}
	
	public void onClick() {
	}
	
	public boolean onLongClickWithEvent(AbstractViewEvent event) {
		return true;
	}

	public StringGroupedItemPresentationModelPOC newStringGroupedItemPresentationModel() { return new StringGroupedItemPresentationModelPOC();
	}
}
