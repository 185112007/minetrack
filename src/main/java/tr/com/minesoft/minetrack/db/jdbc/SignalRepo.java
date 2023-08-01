/**
 * 
 */
package tr.com.minesoft.minetrack.db.jdbc;

import org.joda.time.DateTime;
import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.SignalModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Gafur Hayytbayev
 *
 */
public class SignalRepo implements DAO<SignalModel, String> {

	@Override
	public boolean insert(SignalModel signal) {
		boolean result = false;
		String sqlInsertQueryV2 = "INSERT INTO signal_model (date_time,rid,tid,rssi) VALUES (?, ?, ?, ?)";

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
	public boolean update(SignalModel t, String[] params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArrayList<String> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<String, SignalModel> get(String[] params) {
		return null;
	}

	@Override
	public ArrayList<SignalModel> get(int tid, DateTime dt1, DateTime dt2) {
		// TODO Auto-generated method stub
		return null;
	}

}
