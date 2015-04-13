package com.poreader.common.engines;

public interface CircleEngine {

	void start();

	void stop();

	boolean getIsStart();

	int getDetectSpanInSecs();

	void setDetectSpanInSecs(int detectSpanInSecs);

	Exception getLastException();
}
