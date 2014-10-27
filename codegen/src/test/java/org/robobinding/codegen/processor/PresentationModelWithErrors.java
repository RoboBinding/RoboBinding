package org.robobinding.codegen.processor;

import java.util.List;

import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class PresentationModelWithErrors {
	public static final int numPropertyDependencyErrors = 3;
	public static final int numPropertyErrors = 3;
	
	@DependsOnStateOf("propDependsOnSelf")
	public boolean getPropDependsOnSelf() {
		return false;
	}
	
	public String getExistingProp() {
		return null;
	}
	
	public void getNonProp() {
	}
	
	@DependsOnStateOf({"nonExistingProp", "existingProp"})
	public String getPropDependsOnNonExistingProp() {
		return null;
	}
	
	@DependsOnStateOf({"nonProp", "existingProp"})
	public String getPropDependsOnNonProp() {
		return null;
	}
	
	public int getInconsistentProp() {
		return 0;
	}
	
	public void setInconsistentProp(String param) {
		
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class, factoryMethod="nonExistingFactoryMethod")
	public List<String> getDataSetPropertyWithNonExistingFactoryMethod() {
		return null;
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class, factoryMethod="nonFactoryMethod")
	public List<String> getDataSetPropertyWithNonFactoryMethod() {
		return null;
	}
	
	public Object nonFactoryMethod() {
		return null;
	}
}
