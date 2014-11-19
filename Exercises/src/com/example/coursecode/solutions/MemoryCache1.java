/**
 * 
 */
package com.example.coursecode.solutions;

import android.support.v4.util.LruCache;

/**
 * @author guyt
 *
 */
public class MemoryCache1 {
	
	private LruCache<String, CacheEntry> cache;
	
	private static class CacheEntry {
		public long expirationTime;
		public String value;
		
		public CacheEntry(String value, long expirationTime) {
			this.value = value;
			this.expirationTime = expirationTime;
		}
		
	}
	
	public MemoryCache1(int maxSize) {
		cache = new LruCache<String, CacheEntry>(maxSize);		
	}
	
	/** cache the value of key in memory until 'expirationTime'. */
	public void put(String key, long expirationTime, String value) {
		CacheEntry entry = new CacheEntry(value, expirationTime);
		cache.put(key, entry);
	}
	
	/** @return value string or null if does not exist / expired. */
	public String get(String key) {
		CacheEntry cacheEntry = cache.get(key);
		if (cacheEntry == null) {
			return null; // evicted or never stored.
		}
		if (cacheEntry.expirationTime <= System.currentTimeMillis()) {
			cache.remove(key);  // stale, remove it.
			return null;
		}
		return cacheEntry.value;
	}
	
}
