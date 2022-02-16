package tr.com.minetrack.depo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketConnection {
	
	public static Socket socket;
	
	public static void connect(){
		socket = new Socket();
		try{
			socket.connect(new InetSocketAddress("192.168.1.2",4001)); /* Terminal IP ve Port Number*/
			System.out.println("Connected!");
		}catch(IOException e){
			System.out.println("An exception in connection!");
		}
	}
	
	public static void disconnect(){
		try{
			socket.close();
		}catch(IOException e){
			System.out.println("An exception in disconnection!");
		}
	}
}
