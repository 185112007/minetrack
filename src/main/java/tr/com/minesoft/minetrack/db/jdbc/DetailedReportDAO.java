package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.helpers.TimeAndRid;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;

public class DetailedReportDAO implements DAO<TimeAndRid, String> {

	@Override
	public boolean insert(TimeAndRid t) {
		return false;
	}

	@Override
	public boolean update(TimeAndRid t, String[] params) {
		return false;
	}

	@Override
	public boolean delete(List<String> list) {
		return false;
	}

	@Override
	public Map<String, TimeAndRid> get(String[] params) {
		return null;
	}

	@Override
	public List<TimeAndRid> get(String tid, DateTime dt1, DateTime dt2) {

		List<TimeAndRid> list = new ArrayList<>();

		String sqlQuery = "SELECT \"time\", rid\r\n" + 
				"  FROM signal\r\n" + 
				"  WHERE tid = '" + tid + "' AND time >= '"+dt1+"' \r\n" +
				"  and time < '" + dt2 +"'";
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = PostgreSQL.getInstance().getConnection();

			statement = con.createStatement();
			rs = statement.executeQuery(sqlQuery);
			
			while (rs.next()) {
				Timestamp timeStamp = rs.getTimestamp("time");
				DateTime dt = new DateTime(timeStamp);
				String readerID = rs.getString("rid");
				list.add(new TimeAndRid(dt, readerID));
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
		
		return list;
	}

	@Override
	public List<TimeAndRid> get(String tid, LocalDateTime dt1, LocalDateTime dt2) {
		return List.of();
	}

}
