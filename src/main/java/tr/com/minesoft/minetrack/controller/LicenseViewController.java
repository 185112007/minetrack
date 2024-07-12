package tr.com.minesoft.minetrack.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import tr.com.minesoft.minetrack.view.dialogs.settings.LicenseView;

public class LicenseViewController implements ActionListener {

	private final LicenseView parent;

	/**
	 * 
	 */
	public LicenseViewController(final LicenseView view) {
		this.parent = view;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		JButton jButton = (JButton) e.getSource();

		String name = jButton.getName();
		switch (name) {
		case "close":
			parent.closeThisDialog();
			break;
		case "changeLicense":
			parent.showUpdateLicenseView();
			break;
		default:
			//System.out.println("default case");
		}
	}

}
