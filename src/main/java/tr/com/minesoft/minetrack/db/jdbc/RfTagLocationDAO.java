package tr.com.minesoft.minetrack.db.jdbc;

import org.joda.time.DateTime;
import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.RfTagLocation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RfTagLocationDAO implements DAO<RfTagLocation, String> {

	@Override
	public boolean insert(RfTagLocation location) {
		boolean result = false;
		String sqlInsertQueryV2 = "INSERT INTO location (x,y,tid,full_name, date_time) VALUES (?, ?, ?, ?, ?)";

		Timestamp timeStamp = Timestamp.valueOf(location.getDateTime());
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlInsertQueryV2);

			prepStatement.setDouble(1, location.getX());
			prepStatement.setDouble(2, location.getY());
			prepStatement.setString(3, location.getTagId());
			prepStatement.setString(4, location.getFullName());
			prepStatement.setTimestamp(5, timeStamp);

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
	public boolean update(RfTagLocation t, String[] params) {
		return false;
	}

	@Override
	public boolean delete(List<String> list) {
		return false;
	}

	@Override
	public Map<String, RfTagLocation> get(String[] params) {
		return null;
	}

	@Override
	public List<RfTagLocation> get(String tid, DateTime dt1, DateTime dt2) {
		return List.of();
	}

	@Override
	public List<RfTagLocation> get(String tid, LocalDateTime dt1, LocalDateTime dt2) {

		List<RfTagLocation> result = new ArrayList<>();

		String selectQuery = "SELECT x, y, tid, full_name, date_time FROM location WHERE tid = ? and  date_time BETWEEN ? AND ?";

		Timestamp timeStamp1 = Timestamp.valueOf(dt1);
		Timestamp timeStamp2 = Timestamp.valueOf(dt2);

		Connection con = null;
		PreparedStatement prepStatement = null;
		con = PostgreSQL.getInstance().getConnection();
		try {
			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(selectQuery);

			prepStatement.setString(1, tid);
			prepStatement.setTimestamp(2, timeStamp1);
			prepStatement.setTimestamp(3, timeStamp2);

			ResultSet resultSet = prepStatement.executeQuery();

			double x,y;
			LocalDateTime dateTime;
			String fullName;

			while (resultSet.next()){
				x = Double.parseDouble(resultSet.getString("x"));
				y = Double.parseDouble(resultSet.getString("y"));
				tid = resultSet.getString("tid");
				fullName = resultSet.getString("full_name");
				dateTime = Timestamp.valueOf(resultSet.getString("date_time")).toLocalDateTime();
				result.add(RfTagLocation.builder()
								.tagId(tid)
								.fullName(fullName)
								.x(x)
								.y(y)
								.dateTime(dateTime)
						.build());
			}
			con.commit();
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			// update
			result = null;
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
}
