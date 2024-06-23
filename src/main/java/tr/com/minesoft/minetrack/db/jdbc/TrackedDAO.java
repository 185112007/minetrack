package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.Tracked;

public class TrackedDAO implements DAO<Tracked, String> {

	@Override
	public boolean insert(Tracked t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Tracked t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(List<String> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Tracked> get(String[] params) {
		Map<String, Tracked> trackedMap = new LinkedHashMap<>();

		String sqlQuery = "SELECT employee.fname, employee.lname, employee.tagid\r\n" + "FROM employee\r\n"
				+ "UNION\r\n" + "SELECT machine.fname, machine.lname, machine.tagid\r\n" + "FROM machine;";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {

			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				String fname = rs.getString(1);
				String lname = rs.getString(2);
				String tagid = rs.getString(3);

				trackedMap.put(tagid, new Tracked(fname, lname, tagid));
			}

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

		return trackedMap;
	}

	@Override
	public List<Tracked> get(String tid, DateTime dt1, DateTime dt2) {
		return null;
	}

	@Override
	public List<Tracked> get(String tid, LocalDateTime dt1, LocalDateTime dt2) {
		return List.of();
	}

}
