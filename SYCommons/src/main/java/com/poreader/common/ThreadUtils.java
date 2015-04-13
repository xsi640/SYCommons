package com.poreader.common;

public class ThreadUtils {
	public static void run(final Runnable runnable) {
		if (runnable == null)
			return;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				runnable.run();
			}
		});
		thread.start();
	}

	public static void delayRun(final Runnable runnable, final long milliseconds) {
		if (runnable == null)
			return;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(milliseconds);
					runnable.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
}
