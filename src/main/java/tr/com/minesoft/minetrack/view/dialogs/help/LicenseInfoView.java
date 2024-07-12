package tr.com.minesoft.minetrack.view.dialogs.help;

import tr.com.minesoft.minetrack.view.dialogs.settings.LicenseView;

import java.awt.Frame;

public class LicenseInfoView extends LicenseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LicenseInfoView(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	@Override
	protected void initComponents() {
		super.initComponents();
		super.jButton2.setVisible(false);
	}

}
