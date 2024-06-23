package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.Signal;

public class SignalDAO implements DAO<Signal, String> {

	@Override
	public boolean insert(Signal signal) {
		boolean result = false;
		String sqlInsertQueryV2 = "INSERT INTO signal (time,rid,tid,rssi) VALUES (?, ?, ?, ?)";

		Timestamp timeStamp = new Timestamp(signal.getDt().getMillis());
		Connection con = null;
		PreparedStatement prepStatement = null;
		try {
			con = PostgreSQL.getInstance().getConnection();
			con.setAutoCommit(false);
			prepStatement = con.prepareStatement(sqlInsertQueryV2);

			prepStatement.setTimestamp(1, timeStamp);
			prepStatement.setString(2, signal.getRid());
			prepStatement.setString(3, signal.getTid());
			prepStatement.setInt(4, signal.getRssi());

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
	public boolean update(Signal t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(List<String> list) {
		return false;
	}

	@Override
	public Map<String, Signal> get(String[] params) {
		return null;
	}

	@Override
	public List<Signal> get(String tid, DateTime dt1, DateTime dt2) {
		return null;
	}

	@Override
	public List<Signal> get(String tid, LocalDateTime dt1, LocalDateTime dt2) {
		return List.of();
	}

}
