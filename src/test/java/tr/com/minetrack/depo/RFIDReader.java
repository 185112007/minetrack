package tr.com.minetrack.depo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class RFIDReader implements Runnable
{

    public static DataInputStream in;

    public RFIDReader()
    {
	setIn();
    }

    private static void setIn()
    {
	try
	{
	    in = new DataInputStream(SocketConnection.socket.getInputStream());
	} catch (IOException e)
	{
	    System.out.println("inside RFIDReader Constructor!");
	}
    }

    @Override
    public void run()
    {
	DateTime now = DateTime.now();
	DateTime stop = now.plus(10000);
	int counter = 0;

	int beginChar;
	List<Integer> bytes = new ArrayList<Integer>();
	while (stop.isAfter(now))
	{
	    try
	    {
		System.out.println("read byte!");

		beginChar = in.read();

		if (beginChar == 0x43)
		{
		    in.read();// data length

		    bytes = readBytes();

		    int res = calcXor(bytes);
		    if (res == bytes.get(11))
		    {
			System.out.println(Integer.toHexString(res));
			counter++;
		    }
		    
		    // printBytes(bytes);
		}

	    } catch (IOException e)
	    {
		System.out.println("IOException inside while!");
	    } finally
	    {
		now = DateTime.now();
	    }
	}
	System.out.println("counter:" + counter);
    }

    int calcXor(List<Integer> arr)
    {
	int result = 0;

	for (int i = 0; i < 11; i++)
	{
	    result = result ^ arr.get(i);
	}
	return result;
    }

    List<Integer> readBytes()
    {
	List<Integer> bytes = new ArrayList<Integer>();
	bytes.add(0x43);
	bytes.add(0x09);
	for (int i = 0; i < 9; i++)
	{
	    try
	    {
		bytes.add(in.read());
	    } catch (IOException e)
	    {
		// TODO Auto-generated catch block
		System.out.println("Inside readBytes() method!");
	    }
	}
	// also read check code and end code
	try
	{
	    bytes.add(in.read());// check code
	    bytes.add(in.read());// end code
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return bytes;
    }

    void printBytes(List<Integer> bytes)
    {
	System.out.println("start:");

	for (int i = 0; i < bytes.size(); i++)
	{
	    System.out.println(i + ".byte : " + Integer.toHexString(bytes.get(i)));
	}
	System.out.println("rssi: " + Integer.toHexString(bytes.get(14)));
	System.out.println("end!");
    }
}