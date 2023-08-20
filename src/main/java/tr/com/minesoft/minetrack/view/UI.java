package tr.com.minesoft.minetrack.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.geotools.map.MapContent;
import org.geotools.swing.data.JFileDataStoreChooser;

import tr.com.minesoft.minetrack.controller.UIEngine;
import tr.com.minesoft.minetrack.helpers.Login;
import tr.com.minesoft.minetrack.helpers.impl.AddLayerImpl;
import tr.com.minesoft.minetrack.messages.Messages;
import tr.com.minesoft.minetrack.model.RFIDReader;
import tr.com.minesoft.minetrack.view.dialogs.AddEmployeeView;
import tr.com.minesoft.minetrack.view.dialogs.AddMachineView;
import tr.com.minesoft.minetrack.view.dialogs.AdminView;
import tr.com.minesoft.minetrack.view.dialogs.ContactView;
import tr.com.minesoft.minetrack.view.dialogs.DailyReportView;
import tr.com.minesoft.minetrack.view.dialogs.DetailedReportView;
import tr.com.minesoft.minetrack.view.dialogs.LicenseInfoView;
import tr.com.minesoft.minetrack.view.dialogs.LicenseView;
import tr.com.minesoft.minetrack.view.dialogs.PersonalReportView;
import tr.com.minesoft.minetrack.view.dialogs.SettingsView;
import tr.com.minesoft.minetrack.view.frames.MineTrackFrame;
import tr.com.minesoft.minetrack.view.msgdialogs.ReaderStatus;
import tr.com.minesoft.minetrack.view.msgdialogs.TerminalStatus;

public final class UI {
	private final MineTrackFrame mapFrame;
	private final MapContent map;
	private final JMenuBar menuBar;
	private JMenuItem signalMapSubmenu;
	private JMenuItem updateLicenseSubmenu;
	private JButton btnStart;
	private JButton btnStop;

	public UI(MapContent map) {
		this.map = map;
		this.mapFrame = new MineTrackFrame();
		this.menuBar = new JMenuBar();
		init();
	}

	private void init() {
		ImageIcon img = new ImageIcon("data/images/tracking.png");
		mapFrame.setIconImage(img.getImage());
		mapFrame.setMapContent(map);

		mapFrame.setTitle(Messages.getString("UI.slogan"));
		mapFrame.enableToolBar(true);
		mapFrame.enableStatusBar(true);
		mapFrame.enableLayerTable(true);
		mapFrame.enablePersonelTable(true);
		mapFrame.setJMenuBar(menuBar);

		// create instance for UI Control class
		UIEngine uiControl = new UIEngine(this);

		// file menu bar---------------------------------------------------
		JMenu fileMenu = new JMenu(Messages.getString("UI.minetrack"));

		// adding all submenus for status bar
		JMenuItem addLayerSubmenu = new JMenuItem(Messages.getString("UI.addLayer"));
		addLayerSubmenu.setName("addlayer");
		JMenuItem exitSubmenu = new JMenuItem(Messages.getString("UI.exitapp"));
		exitSubmenu.setName("exit");

		// add action listener
		addLayerSubmenu.addActionListener(uiControl);
		exitSubmenu.addActionListener(uiControl);

		fileMenu.addSeparator();
		fileMenu.add(exitSubmenu);
		menuBar.add(fileMenu);
		// -----------------------------------------------------------------

		// status menu bar--------------------------------------------------
		JMenu stateMenu = new JMenu(Messages.getString("UI.stateOf"));

		// adding all submenus
		JMenuItem dataTerminalSubmenu = new JMenuItem(Messages.getString("UI.terminal"));
		dataTerminalSubmenu.setName("terminal");
		JMenuItem rfidReaderSubmenu = new JMenuItem(Messages.getString("UI.reader"));
		rfidReaderSubmenu.setName("rfid");

		// add action listener
		dataTerminalSubmenu.addActionListener(uiControl);
		rfidReaderSubmenu.addActionListener(uiControl);

		stateMenu.add(dataTerminalSubmenu);
		stateMenu.add(rfidReaderSubmenu);

		menuBar.add(stateMenu);
		// -------------------------------------------------------------------

		// add data menu bar--------------------------------------------------
		JMenu addMenu = new JMenu(Messages.getString("UI.add"));

		// adding all submenus
		JMenuItem employeeSubmenu = new JMenuItem(Messages.getString("UI.employee"));
		employeeSubmenu.setName("emlpoyee");
		JMenuItem machinesSubmenu = new JMenuItem(Messages.getString("UI.machines"));
		machinesSubmenu.setName("machines");

		// add action listener
		employeeSubmenu.addActionListener(uiControl);
		machinesSubmenu.addActionListener(uiControl);

		addMenu.add(employeeSubmenu);
		addMenu.add(machinesSubmenu);

		menuBar.add(addMenu);
		// -------------------------------------------------------------------

		// reports menu bar--------------------------------------------------
		JMenu reportMenu = new JMenu(Messages.getString("UI.reports"));

		// adding all submenus
		JMenuItem dailyReportSubmenu = new JMenuItem(Messages.getString("UI.dailyReports"));
		dailyReportSubmenu.setName("dailyreport");
		JMenuItem personalReportSubmenu = new JMenuItem(Messages.getString("UI.personelReport"));
		personalReportSubmenu.setName("personalreport");
		JMenuItem detailedReportSubmenu = new JMenuItem(Messages.getString("UI.detailedReport"));
		detailedReportSubmenu.setName("detailedreport");

		// add action listener
		dailyReportSubmenu.addActionListener(uiControl);
		personalReportSubmenu.addActionListener(uiControl);
		detailedReportSubmenu.addActionListener(uiControl);

		reportMenu.add(dailyReportSubmenu);
		reportMenu.add(personalReportSubmenu);
		reportMenu.add(detailedReportSubmenu);

		menuBar.add(reportMenu);
		// -------------------------------------------------------------------

		// settings menu bar--------------------------------------------------
		JMenu settingsMenu = new JMenu(Messages.getString("UI.settings"));

		// adding all submenus
		JMenuItem adminSubmenu = new JMenuItem(Messages.getString("UI.admin"));
		adminSubmenu.setName("admin");
		signalMapSubmenu = new JMenuItem(Messages.getString("UI.changeSignalMap"));
		signalMapSubmenu.setName("signalmap");
		signalMapSubmenu.setEnabled(false);

		updateLicenseSubmenu = new JMenuItem(Messages.getString("UI.updateLicense"));
		updateLicenseSubmenu.setName("updatelicense");
		updateLicenseSubmenu.setEnabled(false);

		// add action listener
		adminSubmenu.addActionListener(uiControl);
		signalMapSubmenu.addActionListener(uiControl);
		updateLicenseSubmenu.addActionListener(uiControl);

		settingsMenu.add(adminSubmenu);
		settingsMenu.add(signalMapSubmenu);
		settingsMenu.add(updateLicenseSubmenu);

		menuBar.add(settingsMenu);
		// -------------------------------------------------------------------

		// help menu bar--------------------------------------------------
		JMenu helpMenu = new JMenu(Messages.getString("UI.help"));

		// adding all submenus
		JMenuItem userManualSubmenu = new JMenuItem(Messages.getString("UI.usermanual"));
		userManualSubmenu.setName("usermanual");
		JMenuItem licenseSubmenu = new JMenuItem(Messages.getString("UI.license"));
		licenseSubmenu.setName("license");
		JMenuItem aboutSubmenu = new JMenuItem(Messages.getString("UI.contact"));
		aboutSubmenu.setName("contact");

		// add action listener
		userManualSubmenu.addActionListener(uiControl);
		licenseSubmenu.addActionListener(uiControl);
		aboutSubmenu.addActionListener(uiControl);

		helpMenu.add(licenseSubmenu);

		menuBar.add(helpMenu);
		// -------------------------------------------------------------------

		// start and stop toolbar ---------------------------------------------
		JToolBar toolBar = mapFrame.getToolBar();

		ImageIcon iconStart = new ImageIcon("data/images/start_btn.png");

		btnStart = new JButton(iconStart);

		toolBar.addSeparator();
		toolBar.add(btnStart);
		btnStart.setName("startbtn");
		btnStart.setToolTipText(Messages.getString("UI.start"));
		btnStart.addActionListener(uiControl);

		ImageIcon stopIcon = new ImageIcon("data/images/stop_btn.png");
		btnStop = new JButton(stopIcon);

		// toolBar.addSeparator();
		toolBar.add(btnStop);
		btnStop.setName("stopbtn");
		btnStop.setToolTipText(Messages.getString("UI.stop"));
		btnStop.addActionListener(uiControl);
		disableStopBtn();
		// ----------------------------------------------------------------------------

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Dimension screenSize = toolkit.getScreenSize();
		Insets insets = toolkit.getScreenInsets(mapFrame.getGraphicsConfiguration());
		screenSize.width -= (insets.left + insets.right);
		screenSize.height -= (insets.top + insets.bottom);
		mapFrame.setSize(screenSize.width, screenSize.height);

		mapFrame.setLocationRelativeTo(null);
		mapFrame.setVisible(true);
	}

	private void setEnabledSettingsubmenu() {
		signalMapSubmenu.setEnabled(true);
		updateLicenseSubmenu.setEnabled(true);
	}

	// getters and setters--------------------------------------------
	public MineTrackFrame getFrame() {
		return mapFrame;
	}

	public void enableStartBtn() {
		btnStart.setEnabled(true);
	}

	public void disableStartBtn() {
		btnStart.setEnabled(false);
	}

	public void enableStopBtn() {
		btnStop.setEnabled(true);
	}

	public void disableStopBtn() {
		btnStop.setEnabled(false);
	}
	// end getters and setters----------------------------------------

	// methods for controls-------------------------------------------
	public void openFileDialog() {
		File file = JFileDataStoreChooser.showOpenFile("shp", null); //$NON-NLS-1$
		if (file != null) {
			// use the shapefile
			System.out.println(file.getAbsolutePath());
			new AddLayerImpl(file, map).add();
		}

	}

	public void exitApp() {
		Runtime.getRuntime().exit(0);
	}

	public void showTerminalStatus(boolean connected) {
		System.out.println(connected);
		new TerminalStatus(this, connected);
	}

	public void showReaderStatus(HashMap<String, RFIDReader> readers) {
		new ReaderStatus(this, readers);
	}
	// ----------------------------------------------------------------

	public void addEmployeeView() {
		new AddEmployeeView(this.getFrame());
	}

	public void addMachineView() {
		new AddMachineView(this.getFrame());
	}

	public void showDailyReport() {
		new DailyReportView(this.getFrame());
	}

	public void showPersonalReport() {
		new PersonalReportView(this.getFrame());
	}

	public void showAdminView() {
		if (!Login.loggedIn) {
			AdminView av = new AdminView(this.getFrame());
			if (av.isSucceeded()) {
				setEnabledSettingsubmenu();
			}
		} else {
			JOptionPane.showMessageDialog(this.getFrame(), Messages.getString("UI.entered"),
					Messages.getString("UI.login"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void showSettingsView() {
		new SettingsView(this.getFrame());
	}

	public void showLicenseView() {
		LicenseView licenseview = new LicenseView(this.getFrame(), true);
		licenseview.setVisible(true);
	}

	public void showContactView() {
		new ContactView(this.getFrame());
	}

	public void showLicenseInfoView() {
		LicenseView licenseinfoview = new LicenseInfoView(this.getFrame(), true);
		licenseinfoview.setVisible(true);
	}

	public void showDetailedReport() {
		new DetailedReportView(this.getFrame());
	}
}
