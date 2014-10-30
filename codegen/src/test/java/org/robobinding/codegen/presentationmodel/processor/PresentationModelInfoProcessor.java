package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.presentationmodel.PresentationModelInfo;
import org.robobinding.codegen.presentationmodel.processor.PresentationModelProcessor;
import org.robobinding.codegen.typewrapper.ProcessingContext;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelInfoProcessor extends PresentationModelProcessor {
	public PresentationModelInfo result;
	
	@Override
	protected void generateClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context) {
		this.result = presentationModelInfo;
	}
}
