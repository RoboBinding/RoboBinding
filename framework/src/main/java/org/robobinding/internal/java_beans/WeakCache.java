package org.robobinding.internal.java_beans;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;

import org.robobinding.util.Maps;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 */
public class WeakCache<K, V> {
	private final Map<K, WeakValue<V>> map;
    private final ReferenceQueue<V> queue;

    public WeakCache() {
    	map = Maps.newHashMap();
        queue = new ReferenceQueue<V>();
	}
    
    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(Object key) {
        processQueue();
        return map.containsKey(key);
    }


    public V get(Object key) {
        return getReferenceObject(map.get(key));
    }

    public V put(K key, V value) {
        processQueue();

        WeakValue<V> oldValue = map.put(key, new WeakValue<V>(key, value, queue));
        return getReferenceObject(oldValue);
    }

    public Object remove(Object key) {
        return getReferenceObject(map.remove(key));
    }

    private final V getReferenceObject(WeakReference<V> ref) {
        return (ref == null) ? null : ref.get();
    }

    @SuppressWarnings("unchecked")
	private void processQueue() {
        WeakValue<V> wv = null;
        while ((wv = (WeakValue<V>) this.queue.poll()) != null) {
            map.remove(wv.key);
        }
    }

    private static class WeakValue<V> extends WeakReference<V> {
        private final Object key;

        public WeakValue(Object key, V value, ReferenceQueue<V> queue) {
            super(value, queue);
            this.key = key;
        }
    }
}