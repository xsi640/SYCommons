package com.github.xsi640.common.engines;

import com.github.xsi640.common.threads.ThreadUtils;

/**
 * 标准循环引擎
 * @author suyang
 */
public class StandardCircleEngine implements CircleEngine, Runnable {

	private boolean isStarted = false;
	private boolean stopping = false;
	private Exception lastException = null;
	private int detectSpanInSecs = 10;
	public Runnable runnable;

	public StandardCircleEngine(Runnable runnable) {
		this.runnable = runnable;
	}

	/**
	 * 开始
	 */
	public void start() {
		ThreadUtils.run(this);
		this.isStarted = true;
	}

	/**
	 * 停止
	 * 停止时不会立即停止，需等待当前运行周期完成后停止。
	 */
	public void stop() {
		this.stopping = true;
		this.isStarted = false;
	}

	public boolean getIsStart() {
		return this.isStarted;
	}

	public int getDetectSpanInSecs() {
		return this.detectSpanInSecs;
	}

	public void setDetectSpanInSecs(int detectSpanInSecs) {
		this.detectSpanInSecs = detectSpanInSecs;
	}

	public Exception getLastException() {
		return this.lastException;
	}

	public void run() {
		while (true) {
			try {
				this.runnable.run();
				Thread.sleep(this.detectSpanInSecs);
				if (this.stopping)
					break;
			} catch (Exception ex) {
				this.lastException = ex;
			}
		}
		this.stopping = false;
	}

	@Override
	protected void finalize() throws Throwable {
		this.stop();
		super.finalize();
	}
	
}
