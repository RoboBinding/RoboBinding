package org.robobinding.codegen.processor;

import org.robobinding.codegen.PresentationModelInfo;

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
