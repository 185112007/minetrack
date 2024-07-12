package tr.com.minesoft.minetrack.positioning;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import org.opengis.feature.simple.SimpleFeature;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.helpers.MapOperations;
import tr.com.minesoft.minetrack.helpers.MyPoint;
import tr.com.minesoft.minetrack.helpers.PointList;
import tr.com.minesoft.minetrack.model.RfTagLocation;
import tr.com.minesoft.minetrack.model.Signal;
import tr.com.minesoft.minetrack.model.Tracked;
import tr.com.minesoft.minetrack.model.lists.RFIDReaderList;
import tr.com.minesoft.minetrack.model.lists.TrackedList;

public class Positioning {

	public static boolean findPosition(Signal signal) {
		boolean positioned = false;
		String tid = signal.getTid();
		Tracked tracked = TrackedList.getInstance().getList().get(tid);

		if (tracked != null) {
			positioned = positionWith1Reader(signal, tracked);
		}

		return positioned;
	}

	private static boolean positionWith1Reader(Signal signal, Tracked tracked) {
		boolean positioned = false;
		String rid = signal.getRid();
		int rssi = signal.getRssi();
		double x = 0, y = 0;

		GeometryFactory geomFactory = new GeometryFactory();
		List<SimpleFeature> list = MapOperations.getList();
		MyPoint mypoint = null;
		// daha once konumlandırılmısmı?
		if (tracked.isState()) {// yes
			for (SimpleFeature feature : list) {
				String id = feature.getID();
				if (Objects.equals(id, tracked.getTagId())) {
					mypoint = findClosestPointWithPrevSignal(signal, tracked);
					if (mypoint != null && mypoint.getIndex() != tracked.getPrevPointIndex()) {
						x = mypoint.getX() + (2 * Math.random() - 1) * 0.00001111d;
						y = mypoint.getY() + (2 * Math.random() - 1) * 0.00001111d;
						Point point = geomFactory
								.createPoint(new Coordinate(x, y));
						feature.setAttribute("point", point);
						feature.setAttribute("MyPoint", mypoint);
						positioned = true;
						tracked.setPrevSignal(signal);
						tracked.setPrevPointIndex(mypoint.getIndex());
						tracked.setKonum(RFIDReaderList.getInstance().getList().get(rid).getName());
					}
					break;
				}
			}
		} else { // no
			mypoint = findFirstClosestPoint(rid, rssi);
			if (mypoint != null) {
				// random 5 meters ==> + (2 * Math.random() - 1) * 0.00001111d
				x = mypoint.getX() + (2 * Math.random() - 1) * 0.00001111d;
				y = mypoint.getY() + (2 * Math.random() - 1) * 0.00001111d;
				Point point = geomFactory
						.createPoint(new Coordinate(x, y));
				list.add(SimpleFeatureBuilder.build(
						MapOperations.TYPE, new Object[] { point, tracked.getTagId(),
								tracked.getFname() + " " + tracked.getLname(), tracked, mypoint },
						"" + tracked.getTagId()));// type, object, id

				positioned = true;
				tracked.setPrevSignal(signal);
				tracked.setPrevPointIndex(mypoint.getIndex());
				tracked.setState(true);
				tracked.setKonum(RFIDReaderList.getInstance().getList().get(rid).getName());
			}
		}

		if (positioned){
			Objects.requireNonNull(DAOHelper.getRfTagLocationDAO()).insert(
					RfTagLocation.builder()
							.x(x)
							.y(y)
							.tagId(tracked.getTagId())
							.fullName(tracked.getFname() + " " + tracked.getLname())
							.dateTime(LocalDateTime.now())
							.build());
		}
		return positioned;
	}

	private static MyPoint findClosestPointWithPrevSignal(Signal signal, Tracked tr) {
		MyPoint result = null;
		int rssi = signal.getRssi();
		String rid = signal.getRid();
		Signal prevSignal = tr.getPrevSignal();
		int prevRssi = prevSignal.getRssi();
		String prevRid = prevSignal.getRid();

		if (Objects.equals(rid, prevRid) && rssi == prevRssi) {
			prevSignal = signal; // result = null, konumu degistirme
		} else {// farklı bir readerdan sinyal aldı
			prevSignal = signal;
			result = findClosestPoint(rid, rssi, tr);
		}

		return result;
	}

	private static MyPoint findClosestPoint(String rid, int rssi, Tracked tr) {
		ArrayList<MyPoint> pointList = PointList.getInstance().getList();

		MyPoint result = null;
		int minrssi, maxrssi;
		int counter = 0;

		int TF = 0;
		for (int i = tr.getPrevPointIndex(); i < pointList.size(); i++) {
			result = pointList.get(i);

			if (result.getRssiMap(rid) != null) {
				minrssi = result.getRssiMap(rid).getMin();
				maxrssi = result.getRssiMap(rid).getMax();

				if (minrssi <= rssi && rssi <= maxrssi) {
					TF = 1;
					counter = -1;
					break;
				}
			}
		}

		if (counter != -1) // nokta bulunamadı
		{
			for (int j = tr.getPrevPointIndex(); j >= 0; j--) {
				result = pointList.get(j);

				if (result.getRssiMap(rid) != null) {
					minrssi = result.getRssiMap(rid).getMin();
					maxrssi = result.getRssiMap(rid).getMax();

					if (minrssi <= rssi && rssi <= maxrssi) {
						TF = 1;
						break;
					}
				}
			}
		}
		if (TF == 0)
			result = null;

		return result;
	}

	private static MyPoint findFirstClosestPoint(String rid, int rssi) {
		ArrayList<MyPoint> pointList = PointList.getInstance().getList();

		MyPoint result = null;
		int TF = 0;
		int i = 0, minrssi, maxrssi;
		do {
			result = pointList.get(i);

			if (result.getRssiMap(rid) != null) {
				minrssi = result.getRssiMap(rid).getMin();
				maxrssi = result.getRssiMap(rid).getMax();

				if (minrssi <= rssi && rssi <= maxrssi) {
					TF = 1;
					break;
				}
			}
			i++;
		} while (i < pointList.size());

		if (TF == 0)
			result = null;
		return result;
	}
}
