package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.apt.Logger;
import org.robobinding.codegen.apt.ProcessingContext;
import org.robobinding.codegen.presentationmodel.PresentationModelInfo;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelInfoProcessor extends PresentationModelProcessor {
	public PresentationModelInfo result;
	
	@Override
	protected void generateAllClasses(PresentationModelInfo presentationModelInfo, ProcessingContext context, Logger log) {
		this.result = presentationModelInfo;
	}
}
