package tr.com.minesoft.minetrack.model.lists;

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

    public void add(Signal signal) {
        boolean istracked = TrackedList.getInstance().getList().containsKey(signal.getTid());
        if (istracked) {
            if (Objects.requireNonNull(DAOHelper.getSignalDAO()).insert(signal)) {
                RidAndTid key = new RidAndTid(signal.getRid(), signal.getTid());
                signalmap.put(key, signal);
                boolean positioned = Positioning.findPosition(signal);

                if (positioned) {
                    System.out.println("positioned");
                    Map<String, Tracked> mapOfTracked = TrackedList.getInstance().getList();
                    parent.getFrame().getMapPane().reset();
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
