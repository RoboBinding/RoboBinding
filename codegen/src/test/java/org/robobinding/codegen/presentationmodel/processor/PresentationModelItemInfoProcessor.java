package org.robobinding.codegen.presentationmodel.processor;

import java.io.IOException;

import org.robobinding.codegen.apt.Logger;
import org.robobinding.codegen.apt.ProcessingContext;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;

import com.helger.jcodemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class PresentationModelItemInfoProcessor extends PresentationModelProcessor {
	public PresentationModelInfo result;

	@Override
	protected void generateAllClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context,
			Logger log) throws IOException, JClassAlreadyExistsException,
			ClassNotFoundException {
		DataSetPropertyInfo dataSetProperty = presentationModelInfo.dataSetProperties().toArray(new DataSetPropertyInfo[0])[0];
		WrappedTypeElement typeElement = context.typeElementOf(dataSetProperty.itemPresentationModelTypeName());

		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
				dataSetProperty.itemPresentationModelObjectTypeName(), false);
		result = builder.build();
	}
}
