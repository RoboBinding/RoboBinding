package org.robobinding.codegen.presentationmodel.processor;

import java.io.IOException;

import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;
import org.robobinding.codegen.presentationmodel.processor.PresentationModelInfoBuilder;
import org.robobinding.codegen.presentationmodel.processor.PresentationModelProcessor;
import org.robobinding.codegen.typewrapper.AbstractTypeElementWrapper;
import org.robobinding.codegen.typewrapper.ProcessingContext;

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
		AbstractTypeElementWrapper typeElement = context.typeElementOf(dataSetProperty.itemPresentationModelTypeName());

		PresentationModelInfoBuilder builder = new PresentationModelInfoBuilder(typeElement, 
				dataSetProperty.itemPresentationModelObjectTypeName(), false);
		result = builder.build();
	}
}
