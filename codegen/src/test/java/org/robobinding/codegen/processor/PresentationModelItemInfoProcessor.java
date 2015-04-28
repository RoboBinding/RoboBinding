package org.robobinding.codegen.processor;

import java.io.IOException;

import org.robobinding.codegen.DataSetPropertyInfo;
import org.robobinding.codegen.PresentationModelInfo;

import com.sun.codemodel.JClassAlreadyExistsException;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class PresentationModelItemInfoProcessor extends PresentationModelProcessor {
	public PresentationModelInfo result;

	@Override
	protected void generateClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context) throws IOException, JClassAlreadyExistsException,
			ClassNotFoundException {
		DataSetPropertyInfo dataSetProperty = presentationModelInfo.dataSetProperties().toArray(new DataSetPropertyInfo[0])[0];
		TypeElementWrapper typeElement = context.typeElementOf(dataSetProperty.itemPresentationModelTypeName());

		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
				dataSetProperty.itemPresentationModelObjectTypeName(), false, false);
		result = builder.build();
	}
}
