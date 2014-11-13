package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Distributed cache service
 * 
 */
public class DistributedCacheService implements CacheServiceInterface {
	private final String cacheServerUrl[];

	public DistributedCacheService(String... serverUrl) {
		this.cacheServerUrl = serverUrl;
	}

	/**
	 * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#get(long)
	 */
	@Override
	public String get(long key) {
		HttpResponse<JsonNode> response = null;
		try {
			String cacheServerUrl = resolveURL(key);
			response = Unirest.get(cacheServerUrl + "/cache/{key}").header("accept", "application/json").routeParam("key", Long.toString(key))
					.asJson();
		} catch (UnirestException e) {
			System.err.println(e);
		}
		String value = response.getBody().getObject().getString("value");

		return value;
	}

	private String resolveURL(long key) {
		int bucket = Hashing.consistentHash(Hashing.md5().hashString("" + key), cacheServerUrl.length);

		System.out.println("bucket->" + bucket + "resolved for " + key);
		return cacheServerUrl[bucket];
	}

	/**
	 * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#put(long,
	 *      java.lang.String)
	 */
	@Override
	public void put(long key, String value) {
		HttpResponse<JsonNode> response = null;
		try {
			String cacheServerUrl = resolveURL(key);
			response = Unirest.put(cacheServerUrl + "/cache/{key}/{value}").header("accept", "application/json")
					.routeParam("key", Long.toString(key)).routeParam("value", value).asJson();
		} catch (UnirestException e) {
			System.err.println(e);
		}

		if (response.getCode() != 200) {
			System.out.println("Failed to add to the cache.");
		}
	}
}
