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
import tr.com.minesoft.minetrack.model.Employee;

/**
 * @author Gafur Hayytbayev
 *
 */
public class EmployeeDAO implements DAO<Employee, Integer> {

	@Override
	public boolean insert(Employee emp) {
		boolean result = false;

		String sqlInsertQueryV2 = "INSERT INTO employee (tcno,fname,lname,role, tagid) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection con = PostgreSQL.getInstance().getConnection();
				PreparedStatement prepStatement = con.prepareStatement(sqlInsertQueryV2);){

			prepStatement.setLong(1, emp.getTcno());
			prepStatement.setString(2, emp.getFname());
			prepStatement.setString(3, emp.getLname());
			prepStatement.setString(4, emp.getRole());
			prepStatement.setInt(5, emp.getTagId());

			prepStatement.executeUpdate();

			result = true;
		} catch (SQLException e1) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e1));
		}

		return result;
	}

	@Override
	public boolean update(Employee emp, String[] params) {
		boolean result = false;

		String updateString = "UPDATE employee " + "SET fname=?, lname=?, role=?, tagid=? " + "WHERE  tcno=?";

		try (Connection con = PostgreSQL.getInstance().getConnection();
				PreparedStatement prepStatement = con.prepareStatement(updateString);){
			
			prepStatement.setString(1, emp.getFname());
			prepStatement.setString(2, emp.getLname());
			prepStatement.setString(3, emp.getRole());
			prepStatement.setInt(4, emp.getTagId());
			prepStatement.setLong(5, emp.getTcno());

			prepStatement.executeUpdate();
			
			result = true;
		} catch (SQLException e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}

		return result;
	}

	@Override
	public boolean delete(ArrayList<Integer> tagList) {
		boolean result = false;
		String table = "employee";
		String column = "tagid";
		String sqlDeleteQuery = PostgreSQL.getInstance().createDeleteQuery(tagList, table, column);
		
		try (Connection con = PostgreSQL.getInstance().getConnection();
				PreparedStatement prepStatement = con.prepareStatement(sqlDeleteQuery);){

			int index = 1;

			for (Object o : tagList) {
				prepStatement.setObject(index++, o);
			}
			prepStatement.executeUpdate();

			result = true;
		} catch (SQLException e1) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e1));			
		}

		return result;
	}

	@Override
	public HashMap<Integer, Employee> get(String[] params) {
		HashMap<Integer, Employee> employeeMap = new HashMap<>();

		String sqlQuery = "SELECT tcno, fname, lname, role, tagid FROM employee";
		
		try (Connection con = PostgreSQL.getInstance().getConnection();
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sqlQuery);){

			while (rs.next()) {
				long tcno = rs.getLong("tcno");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String role = rs.getString("role");
				int tagid = rs.getInt("tagid");
				employeeMap.put(tagid, new Employee(tcno, fname, lname, role, tagid));
			}

		} catch (SQLException e1) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e1));
		}

		return employeeMap;
	}

	@Override
	public ArrayList<Employee> get(int tid, DateTime dt1, DateTime dt2) {
		
		return null;
	}
}
