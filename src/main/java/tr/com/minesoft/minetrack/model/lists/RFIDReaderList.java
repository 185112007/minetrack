package tr.com.minesoft.minetrack.model.lists;

import java.util.HashMap;

import tr.com.minesoft.minetrack.model.RFIDReader;

public class RFIDReaderList {
	private final static RFIDReaderList readerListInstance = new RFIDReaderList();

	private final HashMap<String, RFIDReader> mapOfReaders = new HashMap<>();
	private RFIDReaderList() {
	}

	public static RFIDReaderList getInstance() {
		return readerListInstance;
	}

	public HashMap<String, RFIDReader> getList() {
		return mapOfReaders;
	}

	public void putToMap(String id, RFIDReader r) {
		mapOfReaders.put(id, r);
	}
}
