package tr.com.minesoft.minetrack.model.lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.helpers.RidAndTid;
import tr.com.minesoft.minetrack.logging.LoggerImpl;
import tr.com.minesoft.minetrack.logging.util.ExceptionToString;
import tr.com.minesoft.minetrack.model.RFIDReader;
import tr.com.minesoft.minetrack.model.Signal;
import tr.com.minesoft.minetrack.model.Tracked;
import tr.com.minesoft.minetrack.positioning.Positioning;
import tr.com.minesoft.minetrack.view.UI;

public class SignalList {
    private UI parent;
    private static final SignalList signalListInstance = new SignalList();

    private static final Map<RidAndTid, Signal> signalmap = new ConcurrentHashMap<>();
    // String = "rid" + "-" + "tid"

    public static SignalList getInstance() {
        return signalListInstance;
    }

    public Map<RidAndTid, Signal> getSignalMap() {
        return signalmap;
    }

//	public void add(List<Integer> bytes) {
//
//		String rid1 = Integer.toHexString(bytes.get(0));
//		String rid2 = Integer.toHexString(bytes.get(1));
//		String rid3 = Integer.toHexString(bytes.get(2));
//
//		if (rid1.length() < 2)
//			rid1 = "0" + rid1;
//		if (rid2.length() < 2)
//			rid2 = "0" + rid2;
//		if (rid3.length() < 2)
//			rid3 = "0" + rid3;
//		String rid = rid1 + rid2 + rid3;
//		int ridInt;
//		RFIDReader currentReader;
//		try {
//			ridInt = Integer.parseInt(rid);
//			if (!RFIDReaderList.getInstance().getList().containsKey(ridInt))// if this reader id is wrong
//				return;
//			else
//				currentReader = RFIDReaderList.getInstance().getList().get(ridInt);
////			 System.out.println("ReaderID:" + rid);
//		} catch (Exception e) {
//			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
//			return;
//		}
//
//		int rssi = -Integer.parseInt(Integer.toHexString(bytes.get(3)), 16);
//		// int rssi = getRssiFromArray();
//		// bytes.get(4); tag status
//
//		String tag1 = Integer.toHexString(bytes.get(5));
//		String tag2 = Integer.toHexString(bytes.get(6));
//		String tag3 = Integer.toHexString(bytes.get(7));
//		String tag4 = Integer.toHexString(bytes.get(8));
//
//		if (tag1.length() < 2)
//			tag1 = "0" + tag1;
//		if (tag2.length() < 2)
//			tag2 = "0" + tag2;
//		if (tag3.length() < 2)
//			tag3 = "0" + tag3;
//		if (tag4.length() < 2)
//			tag4 = "0" + tag4;
//		String tagString = tag1 + tag2 + tag3 + tag4;
//
//		int tagid;
//		try {
//			tagid = Integer.parseInt(tagString);
//		} catch (Exception e) {
//			LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
//			return;
//		}
//
//		boolean istracked = TrackedList.getInstance().getList().containsKey(tagid);// kayıtlı personel veya makine
//		// listesinde var mı?
//		boolean positioned;
//		DateTime dt = new DateTime();
//		if (istracked) {
//			Signal signal = new Signal(rssi, dt, ridInt, tagid);
//
//			if (Objects.requireNonNull(DAOHelper.getSignalDAO()).insert(signal)) {
//				RidAndTid key = new RidAndTid(ridInt, tagid);
//				signalmap.put(key, signal);
//				positioned = Positioning.findPosition(signal);
//
//				if (positioned) {
//					System.out.println("positioned");
//					HashMap<String, Tracked> mapOfTracked = TrackedList.getInstance().getList();
//					parent.getFrame().setTrackedModel(mapOfTracked);
//				}
//			}
//		}
//		currentReader.setDt(new DateTime());
//		currentReader.setStatus(true);
//	}

    public void add(Signal signal) {
        boolean istracked = TrackedList.getInstance().getList().containsKey(signal.getTid());
        if (istracked) {
            if (Objects.requireNonNull(DAOHelper.getSignalDAO()).insert(signal)) {
                RidAndTid key = new RidAndTid(signal.getRid(), signal.getTid());
                signalmap.put(key, signal);
                boolean positioned = Positioning.findPosition(signal);

                if (positioned) {
                    System.out.println("positioned");
                    HashMap<String, Tracked> mapOfTracked = TrackedList.getInstance().getList();
                    parent.getFrame().setTrackedModel(mapOfTracked);
                }
            }
        }
        RFIDReader currentReader;
        try {
            if (!RFIDReaderList.getInstance().getList().containsKey(signal.getRid()))// if this reader id is wrong
                return;
            else
                currentReader = RFIDReaderList.getInstance().getList().get(signal.getRid());
        } catch (Exception e) {
            LoggerImpl.getInstance().keepLog(ExceptionToString.convert(e));
            return;
        }
        currentReader.setDt(new DateTime());
        currentReader.setStatus(true);
    }

    public UI getParent() {
        return parent;
    }

    public void setParent(UI parent) {
        this.parent = parent;
    }
}
