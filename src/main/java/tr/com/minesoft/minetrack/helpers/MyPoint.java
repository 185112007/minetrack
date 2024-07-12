package tr.com.minesoft.minetrack.helpers;

import java.util.HashMap;

public class MyPoint {

	private double x;
	private double y;
	private int index;
	private HashMap<String, MinMaxRssi> rssiMap = new HashMap<>();

	public MyPoint(double x, double y, int index) {
		this.x = x;
		this.y = y;
		this.setIndex(index);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public MinMaxRssi getRssiMap(String readerID) {
		return rssiMap.get(readerID);
	}

	public void setRssiMap(String readerID, int minrssi, int maxrssi) {
		rssiMap.put(readerID, new MinMaxRssi(minrssi, maxrssi));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
