/*Implementation of Fibonacci sequence using a HashMap for memoization. */

import java.util.HashMap;

public class Fibonacci {
	int callsToFib;
	int result;

	HashMap<Integer, Integer> hashMem = new HashMap<>();

	public Fibonacci(int n){
		this.callsToFib = 0;
		this.result = fib(n);
	}
	
	private int fib(int n) {
		callsToFib++;
		if (n == 0) {
			return 0;
		} else if (n == 1) {
			return 1;
		} else {
			if (! hashMem.containsKey(n)) {
				hashMem.put(n, fib(n - 1) + fib(n - 2));
			}
			return hashMem.get(n);
		}
	}

}
