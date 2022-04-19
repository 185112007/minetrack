package tr.com.minesoft.minetrack.helpers.impl;

import tr.com.minesoft.minetrack.helpers.interfaces.LicenseManagerI;

public class LicenseManagerImpl implements LicenseManagerI {
	boolean activated;

	@Override
	public void activate() {

	}

	@Override
	public boolean checkLicense() {

		return activated;
	}

}
