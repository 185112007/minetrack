package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.Account;

public class AccountDAO implements DAO<Account, Integer> {

	@Override
	public boolean insert(Account t) {
		return false;
	}

	@Override
	public boolean update(Account t, String[] params) {
		return false;
	}

	@Override
	public boolean delete(List<Integer> list) {
		return false;
	}

	@Override
	public Map<Integer, Account> get(String[] params) {
		Map<Integer, Account> accountMap = new HashMap<>();

		String sqlQuery = "SELECT * FROM account";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			rs.next();
			String un = rs.getString("username");
			String pw = rs.getString("password");

			accountMap.put(0, new Account(un, pw));

		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}

		return accountMap;
	}

	@Override
	public List<Account> get(String tid, DateTime dt1, DateTime dt2) {
		return null;
	}

	@Override
	public List<Account> get(String tid, LocalDateTime dt1, LocalDateTime dt2) {
		return List.of();
	}
}
