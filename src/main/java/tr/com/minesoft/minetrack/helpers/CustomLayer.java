package tr.com.minesoft.minetrack.helpers;

import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.Font;
import org.geotools.styling.*;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import tr.com.minesoft.minetrack.messages.Messages;
import tr.com.minesoft.minetrack.model.Signal;
import tr.com.minesoft.minetrack.model.Tracked;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CustomLayer {

	private ArrayList<SimpleFeature> list;
	private TrackedLayer trackedLayer;
	private Style style;
	private SimpleFeatureType type;
	private ListFeatureCollection collection;

	public void addTrackedLayer(MapContent map) {
		map.addLayer(createEmptyLayer());
	}

	public void refreshTrackedLayer(List<Signal> list) {

	}

	private TrackedLayer createEmptyLayer() {
		list = new ArrayList<>();
		style = SLD.createPointStyle("Star", Color.BLUE, Color.BLUE, 0.3f, 10);
		SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();

		// set the name
		b.setName("Konum");

		// add some attribute
		b.add("point", Point.class);
		b.add("tagid", Integer.class);
		b.add("name", String.class);
		b.add("track", Tracked.class);
		b.add("MyPoint", MyPoint.class);

		// add a geometry property
		b.setCRS(DefaultGeographicCRS.WGS84);
		b.add("location", Point.class);

		// build the type
		type = b.buildFeatureType();

		collection = new ListFeatureCollection(type, list);

		// label for konum
		StyleBuilder styleBuilder = new StyleBuilder();
		String attributeName = "name";
		Font font = styleBuilder.createFont("Times New Roman", false, true, 13.0);
		TextSymbolizer textSymb = styleBuilder.createTextSymbolizer(Color.black, font, attributeName);
		Rule rule = styleBuilder.createRule(textSymb);
		// end
		// apply rule
		style.featureTypeStyles().getFirst().rules().add(rule);
		trackedLayer = new TrackedLayer(collection, style);
		trackedLayer.setTitle(Messages.getString("MapOperations.10"));
		return trackedLayer;
	}
}
