package org.robobinding.codegen.apt;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MessagerLoggerFactory {
	private final Messager messager;
	
	public MessagerLoggerFactory(Messager messager) {
		this.messager = messager;
	}
	
	public Logger loggerFor(Element element) {
		return new MessagerLogger(messager, element);
	}
}
