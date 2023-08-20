package tr.com.minesoft.minetrack.model;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.lists.SignalList;

public class DataTerminal {
    private static final String IP = "192.168.1.2";
    private static final int PORT = 4001;
    private volatile boolean state;
    private DataInputStream input;
    private Socket socket;
    private static volatile DataTerminal terminalInstance;
    private static final Object lock = new Object();

    public static DataTerminal getInstance() {
        if (terminalInstance == null) {
            synchronized (lock) {
                if (terminalInstance == null) {
                    terminalInstance = new DataTerminal();
                }
            }
        }
        return terminalInstance;
    }

    private DataTerminal() {}

    public void connect() {
        try {
            if (socket != null) {
                if (input != null) {
                    input.close();
                }
                socket.close();
                socket = null;
            }
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, PORT));
            input = new DataInputStream(socket.getInputStream());
            this.state = true;
        } catch (Exception e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            showIPv4MessageDialog();
        }
    }

    private void showIPv4MessageDialog() {
        JOptionPane.showMessageDialog(null, "Wrong IPv4 Settings" + ", " + "connect", "Local Area Connection",
                JOptionPane.ERROR_MESSAGE);
    }

    public void disconnect() {
        if (state) {
            try {
                this.state = false;
            } catch (Exception e) {
                LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            }
        }
    }

    public boolean getState() {
        return this.state;
    }

    public void read() {
        try {
            if (input.available() == 0) {
                return;
            }
            byte[] buffer = new byte[43];
            int read;
            read = input.read(buffer);
            String output = new String(buffer, 0, read);
            analyzeInput(output);
        } catch (IOException | RuntimeException e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
        }
    }

    private void analyzeInput(String str) throws RuntimeException{
        String[] splitted = str.split(",");
        System.out.println("data:");
        if (splitted.length > 2){
            String rID = splitted[1];
            int rssi = (byte)Integer.parseInt(extractRssiValue(splitted), 16);
            String tID = extractTagId(splitted);
            System.out.println("S/N: " + rID);
            System.out.println("RSSI: " + rssi);
            System.out.println("TagID: " + tID);

            SignalList.getInstance().add(
                    Signal.builder()
                            .dt(new DateTime())
                            .rid(rID)
                            .tid(tID)
                            .rssi(rssi)
                            .build()
            );
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
}
