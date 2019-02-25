package StdLib;

import static java.lang.System.*;

public final class StopWatch {
	
	private static long start_time;
	
	public static void startTime() {
		start_time = nanoTime();//Using nanotime is more accurate than currentTimeMillis.
	}

	public static double elapsedTime() {
		long now = nanoTime();
		return (now - start_time) / 1000000000.0;
	}
}
