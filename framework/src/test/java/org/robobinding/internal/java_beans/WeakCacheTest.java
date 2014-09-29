package org.robobinding.internal.java_beans;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 *
 */
public class WeakCacheTest {
	private WeakCache<String, Object> cache;
	
	@Before
	public void setUp() {
		cache = new WeakCache<String, Object>();
	}
	
	@Test
	public void shouldBeCached() {
		String key = "key";
		Object value = new Object();
		cache.put(key, value);
		
		assertThat(cache.containsKey(key), is(true));
		assertThat(cache.get(key), is(value));
	}
	
	@Test
	public void whenStrongReferenceIsRemoved_thenValueIsNoLongInCache() {
		String key = "key";
		Object value = new Object();
		cache.put(key, value);
		
		value = null;
		forceGCOnKey(key);
		assertThat(cache.containsKey(key), is(false));
	}
	
	private void forceGCOnKey(String key) {
		@SuppressWarnings("unused")
		String[] generateHugeData = null;
		while (cache.containsKey(key)) {
			generateHugeData = new String[999999];
        }
	}
}
