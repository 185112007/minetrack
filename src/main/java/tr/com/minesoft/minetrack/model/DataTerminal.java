package tr.com.minesoft.minetrack.model;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import tr.com.minesoft.minetrack.db.jdbc.SignalRepo;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.service.SignalModelService;

public class DataTerminal {
    private final String ip = "192.168.1.2";
    private final int portNumber = 4001;
    private volatile boolean state;
    private DataInputStream input;
    private Socket socket;
    private static volatile DataTerminal terminalInstance;;
    private static Object lock = new Object();

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
            socket.connect(new InetSocketAddress(ip, portNumber));
            input = new DataInputStream(socket.getInputStream());
            this.state = true;
        } catch (Exception e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            showIPv4MessageDialog("connect");
        }
    }

    private void showIPv4MessageDialog(String msg) {
        JOptionPane.showMessageDialog(null, "Wrong IPv4 Settings" + ", " + msg, "Local Area Connection",
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
        } catch (IOException e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
        }
    }

    private void analyzeInput(String str){
        String[] splitted = str.split(",");
        if (splitted.length > 2){
            SignalModel signal = new SignalModel();
                    signal.setDt(DateTime.now());
                    signal.setRid(splitted[1]);
                    signal.setTid(extractTagId(splitted));
                    signal.setRssi(extractRssiValue(splitted));

            SignalModelService.save(signal);
            System.out.println("DateTime: " + signal.getDt());
            System.out.println("S/N: " + signal.getRid());
            System.out.println("RSSI: " + signal.getRssi());
            System.out.println("TagID: " + signal.getTid());
        }
        System.out.println();
    }

    private String extractTagId(String[] splitted) {
        if (splitted[2].length() > 14){
            return splitted[2].substring(4, 14);
        }
        return "0";
    }

    private int extractRssiValue(String[] splitted) {
        if (splitted[2].length() > 2){
            String hexRssi = splitted[2].substring(0, 2);
            return (short)Integer.parseInt("FF" + hexRssi, 16);
        }
        return 0;
    }
}