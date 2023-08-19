package tr.com.minesoft.minetrack.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tr.com.minesoft.minetrack.helpers.MapOperations;
import tr.com.minesoft.minetrack.helpers.RidAndTid;
import tr.com.minesoft.minetrack.helpers.TrackedLayer;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.DataTerminal;
import tr.com.minesoft.minetrack.model.RFIDReader;
import tr.com.minesoft.minetrack.model.Signal;
import tr.com.minesoft.minetrack.model.Tracked;
import tr.com.minesoft.minetrack.model.lists.RFIDReaderList;
import tr.com.minesoft.minetrack.model.lists.SignalList;
import tr.com.minesoft.minetrack.model.lists.TrackedList;
import tr.com.minesoft.minetrack.view.UI;

public class ThreadManager {
	private static Thread reader;

	public static void startServices(UI parent) {
		DataTerminal.getInstance().connect();
		if (DataTerminal.getInstance().getState()) {
			SignalList.getInstance().setParent(parent);
			Timer timer = new Timer();
			// create timer task
			// en son kapıdan almıssa ve 5 dk icinde sinyal almamıssa sil
			TimerTask timerTask = new TimerTask() {

				@Override
				public void run() {
					// en son kapıdan almıssa ve 5 dk icinde sinyal almamıssa sil
					MapOperations.refreshTrackedLayer(parent);
				}
			};
			// Schedule to run after every 5 minut(240000 millisecond)
			timer.scheduleAtFixedRate(timerTask, 0, 240000);
			reader = new Thread(new ReadService());
			reader.start();
			parent.enableStopBtn();
		} else {
			parent.enableStartBtn();
		}
	}

	public static void stopService(UI parent) {
		DataTerminal.getInstance().disconnect();

		// clear signal list
		Map<RidAndTid, Signal> mapOfSignalList = SignalList.getInstance().getSignalMap();
		mapOfSignalList.clear();

		// update state of tracked
		HashMap<String, Tracked> mapOfTracked = TrackedList.getInstance().getList();
		for (String key : mapOfTracked.keySet()) {
			Tracked tr = mapOfTracked.get(key);
			tr.setState(false);
			tr.setKonum("");
			tr.setPrevPointIndex(0);
			tr.setPrevSignal(null);
		}

		// update state of readers
		HashMap<String, RFIDReader> mapOfReaders = RFIDReaderList.getInstance().getList();
		for (String key : mapOfReaders.keySet()) {
			RFIDReader r = mapOfReaders.get(key);
			r.setStatus(false);
		}

		// delete employees from employeelayer
		TrackedLayer konumLayer = MapOperations.getTrackedLayer();
		MapOperations.getList().clear();
		konumLayer.updated();

		// clear employee table
		parent.getFrame().clearTrackedModel();

		// timer.cancel();
		try {
			reader.join();
			parent.enableStartBtn();
		} catch (InterruptedException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}
}