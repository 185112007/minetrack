package tr.com.minesoft.minetrack.logging.test;

import java.io.IOException;

import tr.com.minesoft.minetrack.logging.LoggerImpl;

public class TestILogger {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			try {
				throw new IOException();
			} catch (Exception e) {
				LoggerImpl.getInstance().keepLog("msg: "+i);
			}
			
		}
	}

}
