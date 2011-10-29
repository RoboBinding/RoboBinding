/**
 * CachedFunctionsTest.java
 * Oct 29, 2011 Copyright Cheng Wei and Robert Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.function;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import robobinding.function.CachedFunctions;
import robobinding.function.Function;
import robobinding.function.CachedFunctions.FunctionDescriptor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CachedFunctionsTest
{
	private CachedFunctions cachedFunctions;
	@Before
	public void setUp()
	{
		FunctionsBean functionsBean = new FunctionsBean();
		cachedFunctions = new CachedFunctions(functionsBean);
	}
	@Test
	public void whenFindExistingFunctionWithMatchedParameterTypes_thenReturnCorrectFunction()
	{
		Function function = cachedFunctions.find(FunctionsBean.FUNCTION1, new Class<?>[]{Boolean.class});
		
		assertCorrectFunction(function, FunctionsBean.FUNCTION1, new Class<?>[]{Boolean.class});
	}
	private void assertCorrectFunction(Function actualFunction, String expectedFunctionName, Class<?>[] expectedParameterTypes)
	{
		Assert.assertNotNull(actualFunction);
		Function expectedFunction = findCachedFunction(expectedFunctionName, expectedParameterTypes);
		Assert.assertTrue(expectedFunction == actualFunction);
	}
	@Test
	public void whenFindExistingFunctioWithUnmatchedParameterTypes_thenReturnNull()
	{
		Function function = cachedFunctions.find(FunctionsBean.FUNCTION1, new Class<?>[]{Boolean.class, Object.class});
		
		Assert.assertNull(function);
	}
	@Test
	public void whenFindNonExistingFunction_thenReturnNull()
	{
		Function function = cachedFunctions.find("nonExistingFunction", new Class<?>[0]);
		
		Assert.assertNull(function);
	}
	@Test
	public void whenFindExistingFunction_thenFunctionCached()
	{
		cachedFunctions.find(FunctionsBean.FUNCTION2, new Class<?>[0]);
		
		assertFunctionCached(FunctionsBean.FUNCTION2, new Class<?>[0]);
	}
	private void assertFunctionCached(String functionName, Class<?>... parameterTypes)
	{
		Function functionCached = findCachedFunction(functionName, parameterTypes);
		Assert.assertNotNull(functionCached);
	}
	private Function findCachedFunction(String functionName, Class<?>... parameterTypes)
	{
		CachedFunctions.FunctionDescriptor functionDescriptor = new CachedFunctions.FunctionDescriptor(functionName, parameterTypes);
		Map<FunctionDescriptor, Function> functionCache = cachedFunctions.functionCache;
		Function expectedFunction = functionCache.get(functionDescriptor);
		return expectedFunction;
	}
	public static class FunctionsBean
	{
		public static final String FUNCTION1 = "function1";
		public static final String FUNCTION2 = "function2";
		public void function1(boolean b)
		{
		}
		public void function2()
		{
		}
	}
}
