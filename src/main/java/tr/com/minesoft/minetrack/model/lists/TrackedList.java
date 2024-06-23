package tr.com.minesoft.minetrack.model.lists;

import java.util.*;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.model.Tracked;

public class TrackedList {
	private static volatile TrackedList instance = null;
	private final Map<String, Tracked> mapOfTracked;
	private static final Object lock = new Object();

	private TrackedList() {
		mapOfTracked = Objects.requireNonNull(DAOHelper.getTrackedDAO()).get(null);
	}

	public static TrackedList getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new TrackedList();
				}
			}
		}
		return instance;
	}

	public Map<String, Tracked> getList() {
		return mapOfTracked;
	}

	public void add(Tracked t) {
		mapOfTracked.put(t.getTagId(), t);
	}

	public void remove(List<String> tagIdList) {
		if (Objects.requireNonNull(DAOHelper.getTrackedDAO()).delete(tagIdList)) {
			for (String key : tagIdList) {
				mapOfTracked.remove(key);
			}
		}
	}
	public void update(Tracked t) {
		mapOfTracked.put(t.getTagId(), t);
	}
	public String getTidByNameSurname(String fname, String lname) {
		String tid = "-1";
		String name, surname;

		for (Map.Entry<String, Tracked> entry : mapOfTracked.entrySet()) {
			Tracked value = entry.getValue();

			name = value.getFname();
			surname = value.getLname();
			if (name.equals(fname) && surname.equals(lname)) {
				tid = value.getTagId();
				break;
			}
		}
		return tid;
	}
}