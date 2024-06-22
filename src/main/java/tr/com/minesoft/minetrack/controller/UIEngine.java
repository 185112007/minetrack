package tr.com.minesoft.minetrack.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.model.DataTerminal;
import tr.com.minesoft.minetrack.model.License;
import tr.com.minesoft.minetrack.model.lists.RFIDReaderList;
import tr.com.minesoft.minetrack.threads.ThreadManager;
import tr.com.minesoft.minetrack.view.UI;

public final class UIEngine implements ActionListener {
    private final UI parent;

    public UIEngine(final UI parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {

        String name;
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();
            name = btn.getName();
        } else {
            JMenuItem clickedMenu = (JMenuItem) e.getSource();
            name = clickedMenu.getName();
        }

        switch (name) {
            case "addlayer":
                parent.openFileDialog();
                break;
            case "savefile":
                break;
            case "exit":
                parent.exitApp();
                break;
            case "terminal":
                parent.showTerminalStatus(DataTerminal.getInstance().getState());
                break;
            case "rfid":
                parent.showReaderStatus(RFIDReaderList.getInstance().getList());
                break;
            case "emlpoyee":
                parent.addEmployeeView();
                break;
            case "machines":
                parent.addMachineView();
                break;
            case "dailyreport":
                parent.showDailyReport();
                break;
            case "personalreport":
                parent.showPersonalReport();
                break;
            case "detailedreport":
                parent.showDetailedReport();
                break;
            case "admin":
                parent.showAdminView();
                break;
            case "signalmap":
                parent.showSettingsView();
                break;
            case "updatelicense":
                parent.showLicenseView();
            case "welcome":
                break;
            case "usermanual":
                break;
            case "license":
                parent.showLicenseInfoView();
                break;
            case "startbtn":
                License license = License.getInstance();
                if (license.licenseExpired(new DateTime())) {
                    parent.disableStartBtn();
                    ThreadManager.startServices(parent);
                } else {
                    parent.showLicenseView();
                }
                break;
            case "stopbtn":
                parent.disableStopBtn();
                ThreadManager.stopService(parent);
                break;
            default:
                break;
        }
    }
}
