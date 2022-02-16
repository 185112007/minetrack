package tr.com.minetrack.depo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestFor433
{
    @SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args)
    {
	String ip = "192.168.1.2";
	int portNumber = 4001;
	Socket socket = new Socket();
	DataInputStream input;
	DataOutputStream output;
	
	try
	{
	    socket.connect(new InetSocketAddress(ip, portNumber)); /* Terminal IP ve Port Number */
	    input = new DataInputStream(socket.getInputStream());

	    output = new DataOutputStream(socket.getOutputStream());
	} catch (IOException e)
	{
	    System.out.println("exception in connection!");
	}
    }

}
