package com.example.exercises;

/**
 * Fake HttpURLConnection for writing the cache exercise.
 * 
 * @author guyt
 */
public class FakeHttpURLConnection {

	private String url;

	private FakeHttpURLConnection(String url) {
		this.url = url;
	}
	
	public static FakeHttpURLConnection urlOpenConnection(String url) {
		return new FakeHttpURLConnection(url);
	}
	
	/**
	 * For "first" url returns expiration of 5 seconds from now.
	 */
	public long getHeaderFieldDate(String field, long defaultValue) {
		long now = System.currentTimeMillis();
		final long inFiveSeconds = now + 5000;
		if (url.equals("first")) {
			if (field.equals("Expires")) {
				return inFiveSeconds;
			}			
		}
		return defaultValue;			
	}
	
}
