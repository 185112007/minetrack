/**
 * 
 */
package tr.com.minesoft.minetrack.db;

import java.util.ResourceBundle;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.helpers.TimeAndRid;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.Account;
import tr.com.minesoft.minetrack.model.Employee;
import tr.com.minesoft.minetrack.model.License;
import tr.com.minesoft.minetrack.model.Machine;
import tr.com.minesoft.minetrack.model.Signal;
import tr.com.minesoft.minetrack.model.SignalMap;
import tr.com.minesoft.minetrack.model.Tracked;

/**
 * @author Gafur Hayytbayev
 *
 */
public class DAOHelper {
	static class PropertyHandler {
		public static String getProperty(String key) {
			return ResourceBundle.getBundle("dao").getString(key);
		}
	}

	@SuppressWarnings("unchecked")
	public static DAO<Employee, String> getEmployeeDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Employee, String>) Class.forName(PropertyHandler.getProperty("dao.impl.employee"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Signal, String> getSignalDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Signal, String>) Class.forName(PropertyHandler.getProperty("dao.impl.signal"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Tracked, String> getTrackedDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Tracked, String>) Class.forName(PropertyHandler.getProperty("dao.impl.tracked"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Account, Integer> getAccountDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Account, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.account"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<License, Integer> getLicenseDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<License, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.license"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<SignalMap, String> getSignalMapDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<SignalMap, String>) Class.forName(PropertyHandler.getProperty("dao.impl.signalmap"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<Machine, String> getMachineDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<Machine, String>) Class.forName(PropertyHandler.getProperty("dao.impl.machine"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<DateTime, Integer> getDailyReportDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<DateTime, Integer>) Class.forName(PropertyHandler.getProperty("dao.impl.daily"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static DAO<TimeAndRid, String> getDetailedReportDAO() {
		try {
			/**
			 * dao.properties icinde yer alan dao.impl anahtarinin degerini okuyarak, DAO
			 * interface sinifini implements etmis bir nesne olusturur.
			 */
			return ((DAO<TimeAndRid, String>) Class.forName(PropertyHandler.getProperty("dao.impl.detailed"))
					.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
		}
		return null;
	}
}
