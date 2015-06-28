package org.robobinding.codegen.typewrapper;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MessagerLogger implements Logger {
	private final Messager messager;
	private final Element element;
	
	public MessagerLogger(Messager messager, Element element) {
		this.messager = messager;
		this.element = element;
	}

	@Override
	public void info(String message) {
		messager.printMessage(Kind.NOTE, message, element);
	}

	@Override
	public void error(Throwable e) {
		String stackTrace = ExceptionUtils.getStackTrace(e);
		messager.printMessage(Kind.ERROR, stackTrace, element);
	}

}
