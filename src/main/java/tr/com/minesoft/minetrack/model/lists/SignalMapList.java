package tr.com.minesoft.minetrack.model.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.model.SignalMap;

public class SignalMapList {

	private HashMap<String, SignalMap> mapOfSignalMap;
	private static volatile SignalMapList signalMapInstance = null;
	private static final Object lock = new Object();

	public static SignalMapList getInstance() {
		if (signalMapInstance == null) {
			synchronized (lock) {
				if (signalMapInstance == null) {
					signalMapInstance = new SignalMapList();
				}
			}
		}
		return signalMapInstance;
	}

	private SignalMapList() {
		mapOfSignalMap = Objects.requireNonNull(DAOHelper.getSignalMapDAO()).get(null);
	}

	public HashMap<String, SignalMap> getList() {
		mapOfSignalMap = Objects.requireNonNull(DAOHelper.getSignalMapDAO()).get(null);
		return mapOfSignalMap;
	}

	public boolean remove(ArrayList<String> pidPlusRidList) {
		if (Objects.requireNonNull(DAOHelper.getSignalMapDAO()).delete(pidPlusRidList)) {
			for (String key : pidPlusRidList) {
				mapOfSignalMap.remove(key);
			}
			return true;
		}
		return false;
	}

	public boolean add(SignalMap signalmap) {
		if (Objects.requireNonNull(DAOHelper.getSignalMapDAO()).insert(signalmap)) {
			mapOfSignalMap.put(signalmap.getPid() + "-" + signalmap.getRid(), signalmap);
			return true;
		}
		return false;
	}

	public boolean update(SignalMap signalMap) {
		if (Objects.requireNonNull(DAOHelper.getSignalMapDAO()).update(signalMap, null)) {
			mapOfSignalMap.put(signalMap.getPid() + "-" + signalMap.getRid(), signalMap);
			return true;
		}
		return false;
	}
}
