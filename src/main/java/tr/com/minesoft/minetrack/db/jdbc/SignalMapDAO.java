package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.SignalMap;

public class SignalMapDAO implements DAO<SignalMap, String> {

	@Override
	public boolean insert(SignalMap t) {
		boolean result = false;
		String sqlInsertQueryV2 = "INSERT INTO signalmap (pid,rid,minrssi, maxrssi) VALUES (?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlInsertQueryV2);

			prepStatement.setInt(1, t.getPid());
			prepStatement.setString(2, t.getRid());
			prepStatement.setInt(3, t.getMinrssi());
			prepStatement.setInt(4, t.getMaxrssi());

			prepStatement.executeUpdate();

			con.commit();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			// update
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
					con.setAutoCommit(true);
					prepStatement.close();
				} catch (SQLException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
		}
		return result;
	}

	@Override
	public boolean update(SignalMap t, String[] params) {
		boolean result = false;
		String sqlInsertQueryV2 = "UPDATE signalmap SET minrssi=?, maxrssi=? WHERE pid=? and rid=?";
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlInsertQueryV2);

			prepStatement.setInt(1, t.getMinrssi());
			prepStatement.setInt(2, t.getMaxrssi());
			prepStatement.setInt(3, t.getPid());
			prepStatement.setString(4, t.getRid());

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
					con.setAutoCommit(true);
					prepStatement.close();
				} catch (SQLException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
		}
		return result;
	}

	@Override
	public boolean delete(List<String> list) {
		boolean result = false;
		String table = "signalmap";
		String column = "pid";
		List<Integer> pidList = cutPidFrom(list);
		String sqlDeleteQuery = PostgreSQL.getInstance().createDeleteQuery(pidList, table, column);
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlDeleteQuery);

			int index = 1;

			for (Object o : pidList) {
				prepStatement.setObject(index++, o);
			}

			prepStatement.executeUpdate();

			con.commit();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			// update
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
					con.setAutoCommit(true);
					prepStatement.close();
				} catch (SQLException e) {
					LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
				}
			}
		}

		return result;
	}

	@Override
	public Map<String, SignalMap> get(String[] params) {
		Map<String, SignalMap> signalMap = new LinkedHashMap<>();

		String sqlQuery = "SELECT pid, rid, minrssi, maxrssi FROM signalmap";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			while (rs.next()) {
				int pid = rs.getInt("pid");
				String rid = rs.getString("rid");
				int minrssi = rs.getInt("minrssi");
				int maxrssi = rs.getInt("maxrssi");
				signalMap.put("" + pid + "-" + rid, new SignalMap(pid, rid, minrssi, maxrssi));
			}

		} catch (SQLException | NullPointerException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		} finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException | NullPointerException e) {
				LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			}
		}

		return signalMap;
	}

	public static List<Integer> cutPidFrom(List<String> pidPlusRidList) {
		List<Integer> pidList = new ArrayList<>();

		for (String str : pidPlusRidList) {
			String[] split = str.split("-");
			pidList.add(Integer.parseInt(split[0]));
		}

		return pidList;
	}

	@Override
	public List<SignalMap> get(String tid, DateTime dt1, DateTime dt2) {
		return null;
	}

	@Override
	public List<SignalMap> get(String tid, LocalDateTime dt1, LocalDateTime dt2) {
		return List.of();
	}
}
