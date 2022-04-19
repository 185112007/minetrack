/**
 * 
 */
package tr.com.minesoft.minetrack.model.lists;

import java.util.ArrayList;
import java.util.HashMap;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.model.Machine;
import tr.com.minesoft.minetrack.model.Tracked;

/**
 * @author Gafur Hayytbayev
 *
 */
public class MachineList {
	private final static MachineList machineListInstance = new MachineList();

	private HashMap<Integer, Machine> mapOfMachines;

	// constructor
	private MachineList() {
		mapOfMachines = DAOHelper.getMachineDAO().get(null);
	}

	public static MachineList getInstance() {
		return machineListInstance;
	}

	public HashMap<Integer, Machine> getList() {
		return mapOfMachines;
	}

	public boolean add(Machine machine) {
		if (DAOHelper.getMachineDAO().insert(machine)) {
			TrackedList.getInstance().add(new Tracked(machine.getFname(), machine.getLname(), machine.getTagId()));
			mapOfMachines.put(machine.getTagId(), machine);
			return true;
		}
		return false;
	}

	public boolean remove(ArrayList<Integer> tagIdList) {
		if (DAOHelper.getMachineDAO().delete(tagIdList)) {
			TrackedList.getInstance().remove(tagIdList);
			for (int key : tagIdList) {
				mapOfMachines.remove(key);
			}
			return true;
		}
		return false;
	}

	public boolean update(Machine machine, int oldTid) {
		if (DAOHelper.getMachineDAO().update(machine, null)) {
			TrackedList.getInstance().update(new Tracked(machine.getFname(), machine.getLname(), machine.getTagId()));
			mapOfMachines.remove(oldTid);
			mapOfMachines.put(machine.getTagId(), machine);
			return true;
		}
		return false;
	}
}
