package org.robobinding.function;

import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FunctionImplTest {
    @Test
    public void whenInvokeMethodWithException_thenThrowException() {
	FunctionBean bean = new FunctionBean();
	Method method = MethodUtils.getMatchingAccessibleMethod(FunctionBean.class, "f1", new Class<?>[0]);

	Function function = new FunctionImpl(bean, method);
	try {
	    function.call();
	    Assert.fail("expect an exception");
	} catch (RuntimeException e) {
	    Throwable cause = e.getCause();
	    Assert.assertEquals(bean.exception, cause);
	}
    }

    public static class FunctionBean {
	private RuntimeException exception;

	public FunctionBean() {
	    exception = new RuntimeException("i don't want to be called");
	}

	public void f1() {
	    throw exception;
	}
    }
}
