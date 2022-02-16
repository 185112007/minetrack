package tr.com.minetrack.depo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;


public class SocketTest {

	Socket socket;
	DataInputStream input;
	DataOutputStream output;

	// static variables for write command
	private static final String START = "aa";
	private static final String START2 = "aa0";
	private static final String END = "03";

	public static void main(String[] args) {
		SocketTest test = new SocketTest();

		test.connect();

		test.write();
		
		
		test.read();

	}

	// connection method
	public void connect() {
		socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("192.168.1.2", 4001)); /* Terminal IP ve Port Number */

			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			System.out.println("connected");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An exception in connection to data terminal!");
		}
	}

	// write and read methods
	public synchronized void write() {

		String readerID = "170601";
		String ask = START + readerID + END;
		if (ask.length() % 2 != 0) {
			ask = START2 + readerID + END;
		}
		byte[] arrayOfBytes = javax.xml.bind.DatatypeConverter.parseHexBinary(ask);
		if (output != null) {
			try {
				output.write(arrayOfBytes);
				output.flush();
				Thread.sleep(100);
				Thread.yield();
			} catch (IOException e) {
				System.out.println("rfid writer ioexception!");
			} catch (InterruptedException e) {
				System.out.println("rfid writer interrupted exception!");
			}
		}
	}

	public void read() {
//		int beginChar;
//		List<Integer> bytes = new ArrayList<Integer>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));

			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			System.out.println("IOException inside read method!");
		}
	}

}
