package tr.com.minesoft.minetrack.model;

public class Employee extends Tracked {
	private final long tcno;
	private final String role;

	public Employee(long tcno, String fname, String lname, String role, String tagId) {
		super(fname, lname, tagId);
		this.role = role;
		this.tcno = tcno;
	}
	public long getTcno() {
		return tcno;
	}
	public String getRole() {
		return role;
	}
}
