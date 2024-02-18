package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;

public class PostgreSQL {
	private String url = "jdbc:postgresql://localhost:5432/minetrack";
	private final String dbuser = "postgres";
	private final String dbpass = "admin";
	private static Connection con;
	private static volatile PostgreSQL sqlInstance = null;
	private static final Object lock = new Object();

	public static PostgreSQL getInstance() {
		if (sqlInstance == null) {
			synchronized (lock) {
				if (sqlInstance == null) {
					sqlInstance = new PostgreSQL();
				}
			}
		}
		return sqlInstance;
	}

	private PostgreSQL() {
		System.out.println("init()...");
		init();
	}

	private void init() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, dbuser, dbpass);
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
	}

	public Connection getConnection() {
		try {
			if (con.isClosed()) {
				con = DriverManager.getConnection(url, dbuser, dbpass);
			}
		} catch (SQLException | NullPointerException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return con;
	}

	public <T> String createDeleteQuery(ArrayList<T> list, String table, String column) {
		StringBuilder builder = new StringBuilder();

		builder.append("?,".repeat(list.size()));

		return "DELETE FROM " + table + " WHERE " + column + " IN ("
				+ builder.deleteCharAt(builder.length() - 1).toString() + ")";
	}
}
