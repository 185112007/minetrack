package tr.com.minetrack.depo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RFIDWriter implements Runnable{
	private static final String START = "aa";
	private static final String START2 = "aa0";
//	private static final String READERID1 = "180193";
//	private static final String READERID2 = "180182";
	private static final String READERID3 = "190272";
	private static final String END = "03";
	
	private List<String> readers = new ArrayList<String>();
	
	private DataOutputStream out;
	private byte[] arrayOfBytes;
	
	public RFIDWriter(){
		try{
			this.out = new DataOutputStream(SocketConnection.socket.getOutputStream());
		}catch(IOException e){
			System.out.println("inside RFIDWriter Constructor!");
		}
		//readers.add(READERID1);
		//readers.add(READERID2);
		readers.add(READERID3);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			for(int i=0;i<readers.size();i++){
				String readerID = readers.get(i);
				String ask = START + readerID + END;
                if(ask.length() % 2 != 0) {
                    ask = START2 + readerID + END;
                }
                arrayOfBytes = javax.xml.bind.DatatypeConverter.parseHexBinary(ask);
                if (out != null) {
                	try{
                		out.write(arrayOfBytes);
                        out.flush();
                        //System.out.println("write and flush:"+i);
                        Thread.sleep(100);
                        Thread.yield();
                	}catch(IOException e){
                		System.out.println("rfid writer ioexception!");
                	} catch (InterruptedException e) {
						System.out.println("rfid writer interrupted exception!");
					} 
                }
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("thread sleep before while loop!");
			}
		}
	}
}
