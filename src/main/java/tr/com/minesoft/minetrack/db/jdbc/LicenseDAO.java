package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import tr.com.minesoft.minetrack.model.License;

public class LicenseDAO implements DAO<License, Integer> {

	@Override
	public boolean insert(License t) {
		return false;
	}

	@Override
	public boolean update(License t, String[] params) {
		boolean result = false;

		Connection con = null;
		PreparedStatement prepStatement = null;
		String updateString = "UPDATE license " + "SET licensevalue=? " + "WHERE licensekey=?";

		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);

			prepStatement = con.prepareStatement(updateString);

			prepStatement.setString(1, t.getDate());
			prepStatement.setString(2, t.getKey());

			prepStatement.executeUpdate();
			con.commit();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			result = false;
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(excep));
				}
			}
		} finally {
			if (prepStatement != null) {
				try {
					prepStatement.close();
				} catch (SQLException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}

		return result;
	}

	@Override
	public boolean delete(List<Integer> list) {
		return false;
	}

	@Override
	public Map<Integer, License> get(String[] params) {
		Map<Integer, License> licenseMap = new HashMap<>();

		String sqlQuery = "SELECT * FROM license";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				String encodedKey = rs.getString("licensekey");
				String encrypted = rs.getString("licensevalue");
				licenseMap.put(0, new License(encodedKey, encrypted));
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

		return licenseMap;
	}

	@Override
	public List<License> get(String tid, DateTime dt1, DateTime dt2) {
		return null;
	}

	@Override
	public List<License> get(String tid, LocalDateTime dt1, LocalDateTime dt2) {
		return List.of();
	}

}
