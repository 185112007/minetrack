package tr.com.minesoft.minetrack.threads;

import tr.com.minesoft.minetrack.model.DataTerminal;

public class ReadService implements Runnable {
	@Override
	public void run() {
		while (DataTerminal.getInstance().getState()) {
			DataTerminal.getInstance().read();
		}
	}

}
