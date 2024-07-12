package tr.com.minesoft.minetrack.helpers;

public class RidAndTid {
	private String rid;
	private String tid;

	public RidAndTid(String rid, String tid) {
		super();
		this.rid = rid;
		this.tid = tid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
}
