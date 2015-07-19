package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.codegen.apt.TypeElementFilter;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelFilter implements TypeElementFilter {
	@Override
	public boolean include(WrappedTypeElement element) {
		checkIsConcreteClass(element);
		return isNotItemPresentationModel(element);
	}
	
	private void checkIsConcreteClass(WrappedTypeElement element) {
		if(element.isNotConcreteClass()){
			throw new RuntimeException(
					MessageFormat.format("@{0} can only be used to annotate a concrete PresentationModel, '{1}' is not.", 
					PresentationModel.class.getName(), element.qName()));
		}
	}

	private boolean isNotItemPresentationModel(WrappedTypeElement element) {
		return !element.isAssignableTo(ItemPresentationModel.class);
	}
}
