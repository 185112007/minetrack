package tr.com.minesoft.minetrack.model;

public class Machine extends Tracked {
	private final long machineNo;
	private final String role;

	public Machine(long machineNo, String fname, String lname, String role, String tagId) {
		super(fname, lname, tagId);
		this.role = role;
		this.machineNo = machineNo;
	}
	public long getMachineNo() {
		return machineNo;
	}
	public String getRole() {
		return role;
	}
}
