package tr.com.minesoft.minetrack.model;

public class Account {
	private final String username;
	private final String password;
	public Account(String u, String p) {
		this.username = u;
		this.password = p;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
