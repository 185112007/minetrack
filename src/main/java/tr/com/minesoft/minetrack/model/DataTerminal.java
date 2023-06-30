package tr.com.minesoft.minetrack.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.lists.RFIDReaderList;
import tr.com.minesoft.minetrack.model.lists.SignalList;

public class DataTerminal {
    // instance variables
    private final String ip = "192.168.0.7";
    private final int portNumber = 26;

    private volatile boolean state;

    private DataInputStream input;
    private DataOutputStream output;                                                                                                                        // passif icin

    private Socket socket;

    // static variables for write command, passif icin
    private static final String START = "aa";
    private static final String START2 = "aa0";
    private static final String END = "03";

    /**
     * tek nesne
     */
    private static volatile DataTerminal terminalInstance;// new DataTerminal("192.168.1.2", 4001);

    /**
     * Double check locking
     *
     * @return
     */
    private static Object lock = new Object();

    public static DataTerminal getInstance() {
        if (terminalInstance == null) {
            // double checked locking
            synchronized (lock) {
                if (terminalInstance == null) {
                    terminalInstance = new DataTerminal();
                }
            }
        }

        return terminalInstance;
    }

    // contructor
    private DataTerminal() {

    }

    // connection method
    public void connect() {

        try {
            if (socket != null) {
                if (input != null) {
                    input.close();
                }
                if (output != null) {                                                                                                                // passif icin
                    output.close();
                }
                socket.close();
                socket = null;
            }
            if (socket == null) // ilk basildiginda buraya gir
            {
                socket = new Socket();

                socket.connect(new InetSocketAddress(ip, portNumber)); /* Terminal IP ve Port Number */
                input = new DataInputStream(socket.getInputStream());

                output = new DataOutputStream(socket.getOutputStream());                                                                            // passif icin

                this.state = true;
            } else if (input == null || output == null) { // passif icin
                showIPv4MessageDialog("connect");
                disconnect();
            }

        } catch (Exception e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            showIPv4MessageDialog("connect");
        }
    }

    private void showIPv4MessageDialog(String msg) {

        JOptionPane.showMessageDialog(null, "Wrong IPv4 Settings" + ", " + msg, "Local Area Connection",
                JOptionPane.ERROR_MESSAGE);

    }

    // disconnection method
    public void disconnect() {
        if (state) {
            try {
                this.state = false;
            } catch (Exception e) {
                LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            }
        }
    }

    // getter for state
    public boolean getState() {
        return this.state;
    }

    // write and read methods passif icin
    public void write() {

        HashMap<Integer, RFIDReader> mapOfReader = RFIDReaderList.getInstance().getList();

        for (RFIDReader r : mapOfReader.values()) {
            if (state) {
                String readerID = Integer.toString(r.getId());
                String ask = START + readerID + END;
                if (ask.length() % 2 != 0) {
                    ask = START2 + readerID + END;
                }
                byte[] arrayOfBytes = javax.xml.bind.DatatypeConverter.parseHexBinary(ask);
                try {
                    output.write(arrayOfBytes);
                    output.flush();

                    Thread.sleep(50);
                    Thread.yield();
                } catch (IOException | InterruptedException e) {
                    LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
                }
            }
        }
    }

    public void read() {
        int beginChar;
        List<Integer> bytes;

        try {
            if (input.available() == 0) {
                return;
            }

//            beginChar = input.read();
//            System.out.println(Integer.toHexString(beginChar));

            byte[] buffer = new byte[43];
            int read;
            read = input.read(buffer);
            String output = new String(buffer, 0, read);
//            System.out.print(output);
//            System.out.flush();

            analyzeInput(output);

//            if (beginChar == 0x40) {
//                input.read();// datalength
//                bytes = readBytesX40();
//                SignalList.getInstance().add(bytes);
//            } else if (beginChar == 0x43) {
//                input.read();// datalength
//                bytes = readBytes();
//                if (calcXorAndCheck(bytes)) {
//                    SignalList.getInstance().add(bytes);
//                }
//            } else if (beginChar == 0x7E) {
//                input.read();// data length 14byte
//                bytes = read433();
//                SignalList.getInstance().add(bytes);
//            }
        } catch (IOException e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
        }
    }

    private void analyzeInput(String str){
        String[] splitted = str.split(",");
        System.out.println("data:");
        if (splitted.length > 2){
            System.out.println("S/N: " + splitted[1]);
            System.out.println("RSSI: " + extractRssiValue(splitted));
            System.out.println("TagID: " + extractTagId(splitted));
        }

        System.out.println();
    }

    private String extractTagId(String[] splitted) {
        if (splitted[2].length() > 14){
            return splitted[2].substring(4, 14);
        }
        throw new RuntimeException("tag id not found");
    }

    private String extractRssiValue(String[] splitted) {
        if (splitted[2].length() > 2){
            return splitted[2].substring(0, 2);
        }
        throw new RuntimeException("rssi not found");
    }

    void printBytes(List<Integer> bytes, String callMethod) {

        System.out.println("start: " + callMethod);
        for (int i = 0; i < bytes.size(); i++) {
            System.out.println(i + ".byte : " + Integer.toHexString(bytes.get(i)));
        }
//		System.out.println("rssi: " + Integer.toHexString(bytes.get(14)));

        System.out.println("end!");
    }

    // helper method for reading
    private List<Integer> readBytes() {
        List<Integer> bytes = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            try {
                bytes.add(input.read());
            } catch (IOException e) {
                LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            }
        }
        // also read check code and end code
        try {
            bytes.add(input.read());// check code
            bytes.add(input.read());// end code
        } catch (IOException e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
        }
        return bytes;
    }

    private List<Integer> readBytesX40() {
        List<Integer> bytes = new ArrayList<>();

        try {
            // read reader id: 3 bytes
            for (int i = 0; i < 3; i++) {
                bytes.add(input.read());
            }

            // rssi
            int randomRSSI = generateRandomRSSI();
            bytes.add(randomRSSI);

            // tag state
            bytes.add(input.read());

            // tagid
            for (int i = 0; i < 4; i++) {
                bytes.add(input.read());
            }

            // also read check code and end code
            bytes.add(input.read());// check code
            bytes.add(input.read());// end code

        } catch (IOException e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
        }
        return bytes;
    }

    private int generateRandomRSSI() {
        Random r = new Random();
        int low = 0x00;
        int high = 0x77;
        int result = r.nextInt(high - low) + low;
//        System.out.println(Integer.toHexString(result));
        return result;
    }

    boolean calcXorAndCheck(List<Integer> arr) {
        int result = 0;

        result = result ^ 0x43;
        result = result ^ 0x09;
        for (int i = 0; i < 9; i++) {
            result = result ^ arr.get(i);
        }
        return result == arr.get(9);
    }

    private List<Integer> read433() {

        List<Integer> bytes = new ArrayList<Integer>();

        for (int i = 0; i < 8; i++) {
            try {
                bytes.add(input.read());
            } catch (IOException e) {
                LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            }
        }

        try {
            for (int i = 0; i < 5; i++) {
                input.read(); // gereksiz veriler
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            bytes.add(input.read()); // rssi
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } //

        // tid ve tstate yerlerini swap et
        int state = bytes.get(7);
        //bytes.remove(7);

        int rssi = bytes.get(8);
        //bytes.remove(8);

        int[] tid = new int[4];
        int k = 3;
        for (int i = 0; i < 4; i++) {
            tid[i] = bytes.get(k);
            bytes.remove(k);
        }

        bytes.add(3, rssi);
        bytes.add(4, state);

        k = 5;
        for (int i = 0; i < 4; i++) {
            bytes.add(k++, tid[i]);
        }

        // also read check code and end code
        int checkcode;
        try {
            checkcode = input.read();
            bytes.add(checkcode);// check code

            bytes.add(input.read());// end code
        } catch (IOException e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
        }
        return bytes;
    }
}
