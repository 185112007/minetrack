/**
 * 
 */
package tr.com.minesoft.minetrack.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.Machine;

/**
 * @author Gafur Hayytbayev
 *
 */
public class MachineDAO implements DAO<Machine, String> {

	@Override
	public boolean insert(Machine t) {
		boolean result = false;

		String sqlInsertQueryV2 = "INSERT INTO machine (mno,fname,lname, role, tagid) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection con = PostgreSQL.getInstance().getConnection();
				PreparedStatement prepStatement = con.prepareStatement(sqlInsertQueryV2);){

			prepStatement.setLong(1, t.getMachineNo());
			prepStatement.setString(2, t.getFname());
			prepStatement.setString(3, t.getLname());
			prepStatement.setString(4, t.getRole());
			prepStatement.setString(5, t.getTagId());

			prepStatement.executeUpdate();

			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			result = false;
		}

		return result;
	}

	@Override
	public boolean update(Machine m, String[] params) {
		boolean result = false;

		String updateString = "UPDATE machine " + "SET fname=?, lname=?, role=?, tagid=? " + "WHERE mno=?";

		try (Connection con = PostgreSQL.getInstance().getConnection();
				PreparedStatement prepStatement = con.prepareStatement(updateString);){
			
			prepStatement.setString(1, m.getFname());
			prepStatement.setString(2, m.getLname());
			prepStatement.setString(3, m.getRole());
			prepStatement.setString(4, m.getTagId());
			prepStatement.setLong(5, m.getMachineNo());

			prepStatement.executeUpdate();
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			result = false;
		}
		return result;
	}

	@Override
	public boolean delete(ArrayList<String> list) {
		boolean result = false;
		String table = "machine";
		String column = "tagid";
		String sqlDeleteQuery = PostgreSQL.getInstance().createDeleteQuery(list, table, column);
		
		try (Connection con = PostgreSQL.getInstance().getConnection();
				PreparedStatement prepStatement = con.prepareStatement(sqlDeleteQuery);){

			int index = 1;

			for (Object o : list) {
				prepStatement.setObject(index++, o);
			}

			prepStatement.executeUpdate();

			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
			result = false;
			
		}

		return result;
	}

	@Override
	public HashMap<String, Machine> get(String[] params) {
		HashMap<String, Machine> machineMap = new HashMap<>();

		String sqlQuery = "SELECT mno, fname, lname, role, tagid FROM machine";
		
		try (Connection con = PostgreSQL.getInstance().getConnection();
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sqlQuery);){
			
			while (rs.next()) {
				long mno = rs.getLong("mno");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String role = rs.getString("role");
				String tagid = rs.getString("tagid");
				machineMap.put(tagid, new Machine(mno, fname, lname, role, tagid));
			}

		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}

		return machineMap;
	}

	@Override
	public ArrayList<Machine> get(String tid, DateTime dt1, DateTime dt2) {
		return null;
	}

}
