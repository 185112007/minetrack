package tr.com.minesoft.minetrack.model.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.model.Machine;
import tr.com.minesoft.minetrack.model.Tracked;

public class MachineList {
	private final static MachineList machineListInstance = new MachineList();

	private final HashMap<String, Machine> mapOfMachines;

	// constructor
	private MachineList() {
		mapOfMachines = Objects.requireNonNull(DAOHelper.getMachineDAO()).get(null);
	}

	public static MachineList getInstance() {
		return machineListInstance;
	}

	public HashMap<String, Machine> getList() {
		return mapOfMachines;
	}

	public boolean add(Machine machine) {
		if (Objects.requireNonNull(DAOHelper.getMachineDAO()).insert(machine)) {
			TrackedList.getInstance().add(new Tracked(machine.getFname(), machine.getLname(), machine.getTagId()));
			mapOfMachines.put(machine.getTagId(), machine);
			return true;
		}
		return false;
	}

	public boolean remove(ArrayList<String> tagIdList) {
		if (Objects.requireNonNull(DAOHelper.getMachineDAO()).delete(tagIdList)) {
			TrackedList.getInstance().remove(tagIdList);
			for (String key : tagIdList) {
				mapOfMachines.remove(key);
			}
			return true;
		}
		return false;
	}

	public boolean update(Machine machine, String oldTid) {
		if (Objects.requireNonNull(DAOHelper.getMachineDAO()).update(machine, null)) {
			TrackedList.getInstance().update(new Tracked(machine.getFname(), machine.getLname(), machine.getTagId()));
			mapOfMachines.remove(oldTid);
			mapOfMachines.put(machine.getTagId(), machine);
			return true;
		}
		return false;
	}
}
