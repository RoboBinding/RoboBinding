package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;
import org.robobinding.util.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@SuppressWarnings("serial")
public class PresentationModelErrors extends RuntimeException {
	private final String presentationModelTypeName;
	final List<String> propertyErrors;
	final List<String> propertyDependencyErrors;
	public PresentationModelErrors(String presentationModelTypeName) {
		this.presentationModelTypeName = presentationModelTypeName;
		
		this.propertyErrors = Lists.newArrayList();
		this.propertyDependencyErrors = Lists.newArrayList();
	}
	
	public void addPropertyDependencyError(String error) {
		propertyDependencyErrors.add(error);
	}
	
	public void addPropertyError(String error) {
		propertyErrors.add(error);
	}
	
	public void assertNoErrors() {
		if(propertyErrors.isEmpty() && propertyDependencyErrors.isEmpty()) {
			return;
		}
		
		throw this;
	}
	
	@Override
	public String getMessage() {
		StrBuilder sb = new StrBuilder();
		sb.appendln("");
		sb.appendln(MessageFormat.format("----------------{0} errors:---------------", presentationModelTypeName));
		appendErrors(sb, "property errors:", propertyErrors);
		sb.appendln("");
		appendErrors(sb, "property dependency errors:", propertyDependencyErrors);
		return sb.toString();
	}
	
	private void appendErrors(StrBuilder sb, String errorHeader, List<String> errors) {
		if(!errors.isEmpty()) {
			sb.appendln(errorHeader);
		}
		
		for(String error : errors) {
			sb.appendln(error);
		}
	}
}
