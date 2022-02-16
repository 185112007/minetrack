package tr.com.minetrack.depo;

import java.util.ArrayList;
import java.util.List;

public class ArrayListExample
{

    public static void main(String[] args)
    {
	List<String> namesList = new ArrayList<String>();
        
        namesList.add("rid1");
        namesList.add("rid2");
        namesList.add("rid3");
        namesList.add("tid1");
        namesList.add("tid2");
        namesList.add("tid3");
        namesList.add("tid4");
        namesList.add("status");
        namesList.add("vol1");
        namesList.add("vol2");
        namesList.add("temp1");
        namesList.add("temp2");
        namesList.add("hum");
        namesList.add("rssi");
        
        System.out.println(namesList);
        
        for (int i = 0; i <= 5; i++)
	{
            namesList.remove(7);
	}
	String tmp = namesList.get(7);
	namesList.remove(7);
	namesList.add(3, tmp);
        
        System.out.println(namesList);
        
        namesList.remove(113);
    }

}
