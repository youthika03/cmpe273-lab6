package edu.sjsu.cmpe.cache.client;

public class Client {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting Cache Client...");
		CacheServiceInterface cache = new DistributedCacheService("http://localhost:3000", "http://localhost:3001", "http://localhost:3002");

		cache.put(1, "a");
		System.out.println("put(1 => a)");

		cache.put(2, "b");
		System.out.println("put(2 => b)");

		cache.put(3, "c");
		System.out.println("put(3 => c)");

		cache.put(4, "d");
		System.out.println("put(4 => d)");

		cache.put(5, "e");
		System.out.println("put(5 => e)");

		cache.put(6, "f");
		System.out.println("put(6=> f)");

		cache.put(7, "g");
		System.out.println("put(7 => g)");

		cache.put(8, "h");
		System.out.println("put(8 => h)");

		cache.put(9, "i");
		System.out.println("put(9 => i)");

		cache.put(10, "j");
		System.out.println("put(10 => j)");

		for (int i = 1; i <= 10; i++) {
			String value = cache.get(i);
			System.out.println("get(" + i + ") => " + value);
		}
		System.out.println("Existing Cache Client...");

	}

}
