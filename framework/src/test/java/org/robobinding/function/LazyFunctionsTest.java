package org.robobinding.function;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class LazyFunctionsTest {
	private LazyFunctions lazyFunctions;
	@Mock
	private FunctionSupply functionSupply;
	@Before
	public void setUp() {
		Set<MethodDescriptor> methods = Sets.newHashSet();
		for(Method method : FunctionsBean.class.getMethods()) {
			methods.add(new MethodDescriptor(method.getName(), method.getParameterTypes()));
		}
		when(functionSupply.tryToCreateFunction(any(MethodDescriptor.class))).thenReturn(new MockFunction());
		lazyFunctions = new LazyFunctions(FunctionsBean.class, methods, functionSupply);
	}

	@Test
	public void whenFindExistingFunctionWithMatchedParameterTypes_thenFunctionFound() {
		Function function = lazyFunctions.find(FunctionsBean.FUNCTION1, new Class<?>[] { boolean.class });

		assertNotNull(function);
	}

	@Test
	public void whenFindExistingFunctioWithUnmatchedParameterTypes_thenReturnNull() {
		Function function = lazyFunctions.find(FunctionsBean.FUNCTION1, new Class<?>[] { Boolean.class, Object.class });

		assertNull(function);
	}

	@Test
	public void whenFindNonExistingFunction_thenReturnNull() {
		Function function = lazyFunctions.find("nonExistingFunction", new Class<?>[0]);

		Assert.assertNull(function);
	}

	@Test
	public void givenFindExistingFunctionOnce_whenFindAgain_thenReturnSameInstance() {
		Function function = lazyFunctions.find(FunctionsBean.FUNCTION2, new Class<?>[0]);

		Function cachedFunction = lazyFunctions.find(FunctionsBean.FUNCTION2, new Class<?>[0]);

		Assert.assertNotNull(function);
		Assert.assertTrue(function == cachedFunction);
	}

	public static class FunctionsBean {
		public static final String FUNCTION1 = "function1";
		public static final String FUNCTION2 = "function2";

		public void function1(boolean b) {
		}

		public void function2() {
		}
	}
}
