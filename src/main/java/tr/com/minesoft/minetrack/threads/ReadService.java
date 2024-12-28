package tr.com.minesoft.minetrack.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.minesoft.minetrack.model.DataTerminal;

public class ReadService implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(ReadService.class);
	private volatile boolean running = false;

	@Override
	public void run() {
		while (DataTerminal.getInstance().getState()) {
			try {
				while (!running) {
					DataTerminal.getInstance().read();
					running = true;
				}
			}catch (Exception e) {
				logger.error(e.getMessage());
			}finally {
				running = false;
			}
		}
	}
}
