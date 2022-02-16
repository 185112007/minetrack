package tr.com.minetrack.depo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	/**
	 * @param args
	 */
	private static final int MYTHREADS = 1;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		
		SocketConnection.connect();
		
		executor.execute(new RFIDReader());
		//executor.execute(new RFIDWriter());
		executor.shutdown();
		
		//wait until all threads are finished
		while(!executor.isTerminated()){
			
		}
		System.out.println("All Threads are finished!");
	}

}
