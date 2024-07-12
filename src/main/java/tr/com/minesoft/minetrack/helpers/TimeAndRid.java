package tr.com.minesoft.minetrack.helpers;

import org.joda.time.DateTime;

public class TimeAndRid {

	private DateTime dt;
	private String rid;

	public TimeAndRid(DateTime time, String rid) {
		this.dt = time;
		this.rid = rid;
	}

	public DateTime getDt() {
		return dt;
	}

	public String getRid() {
		return rid;
	}
}
