package tr.com.minetrack.app.helpers;

public class XorTest
{

    public static void main(String[] args)
    {
	int[] arr = new int[11];
	arr[0] = 0x43;
	arr[1] = 0x09;
	arr[2] = 0x19;
	arr[3] = 0x02;
	arr[4] = 0x70;
	arr[5] = 0x5d;
	arr[6] = 0x00;
	arr[7] = 0x18;
	arr[8] = 0x01;
	arr[9] = 0x05;
	arr[10] = 0x96;
	
	int result = 0;
	for (int i = 0; i < arr.length; i++)
	{
	    result = result ^ arr[i];
	}
	System.out.println(Integer.toHexString(result));
    }
    
    int calcXor(int[] arr)
    {
	int result = 0;
	
	for (int i = 0; i < 11; i++)
	{
	    result = result ^ arr[i];
	}
	return result;
    }
}
