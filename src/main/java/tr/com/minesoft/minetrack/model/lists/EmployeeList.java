package tr.com.minesoft.minetrack.model.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.model.Employee;
import tr.com.minesoft.minetrack.model.Tracked;

public class EmployeeList {
	private final static EmployeeList empListInstance = new EmployeeList();
	private final HashMap<String, Employee> mapOfEmployees;

	private EmployeeList() {
		mapOfEmployees = Objects.requireNonNull(DAOHelper.getEmployeeDAO()).get(null);
	}

	public static EmployeeList getInstance() {
		return empListInstance;
	}

	public HashMap<String, Employee> getList() {
		return mapOfEmployees;
	}

	public boolean add(Employee emp) {
		if (Objects.requireNonNull(DAOHelper.getEmployeeDAO()).insert(emp)) {
			TrackedList.getInstance().add(new Tracked(emp.getFname(), emp.getLname(), emp.getTagId()));
			mapOfEmployees.put(emp.getTagId(), emp);
			return true;
		}
		return false;
	}

	public boolean remove(ArrayList<String> tagIdList) {
		if (Objects.requireNonNull(DAOHelper.getEmployeeDAO()).delete(tagIdList)) {
			TrackedList.getInstance().remove(tagIdList);
			for (String key : tagIdList) {
				mapOfEmployees.remove(key);
			}
			return true;
		}
		return false;
	}

	public boolean update(Employee employee, String oldTid) {
		if (Objects.requireNonNull(DAOHelper.getEmployeeDAO()).update(employee, null)) {
			TrackedList.getInstance()
					.update(new Tracked(employee.getFname(), employee.getLname(), employee.getTagId()));
			mapOfEmployees.remove(oldTid);
			mapOfEmployees.put(employee.getTagId(), employee);
			return true;
		}
		return false;
	}
}
