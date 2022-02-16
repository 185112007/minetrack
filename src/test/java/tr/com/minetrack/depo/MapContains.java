package tr.com.minetrack.depo;

import java.util.HashMap;

public class MapContains {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<>();
		
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		
		System.out.println(map.containsKey("1"));
	}

}
