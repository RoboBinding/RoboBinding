package org.robobinding.codegen.typewrapper;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface Logger {
	void info(String message);
	void error(Throwable e);
}
