package tr.com.minesoft.minetrack.helpers;

import lombok.Getter;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.Font;
import org.geotools.styling.*;
import org.geotools.swing.MapPane;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import tr.com.minesoft.minetrack.messages.Messages;
import tr.com.minesoft.minetrack.model.RfTagLocation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
public class CustomLayer {

    private List<SimpleFeature> features;
    private TrackedLayer trackedLayer;
    private SimpleFeatureType type;
    private MapContent map;
    private MapPane mapPane;

    private final ExecutorService executor;

    private static final int SLEEP_IN_MS = 500;

    public CustomLayer() {
        executor = Executors.newFixedThreadPool(1);
    }

    public void addTrackedLayer(MapContent map, MapPane mapPane) {
        this.map = map;
        this.mapPane = mapPane;
        map.addLayer(createEmptyLayer());
    }

    public void simulateV1(List<RfTagLocation> rfTagLocations) {
        executor.submit(() -> {
            GeometryFactory geomFactory = new GeometryFactory();
            features.clear();
            mapPane.reset();

            for (RfTagLocation location : rfTagLocations) {
                if (features.isEmpty()) {
                    createNewFeature(location, geomFactory);
                    System.out.println("created new feature");
                    mapPane.reset();
                } else {
                    updateFeature(location, geomFactory);
                    System.out.println("updated feature");
                    mapPane.reset();
                }
                mapPane.reset();
                try {
                    TimeUnit.MILLISECONDS.sleep(CustomLayer.SLEEP_IN_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            mapPane.reset();
        });
    }

    private void updateFeature(RfTagLocation location, GeometryFactory geomFactory) {
        SimpleFeature feature;
        Point point;
        feature = features.getFirst();
        point = geomFactory.createPoint(
                new Coordinate(
                        location.getX(),
                        location.getY()
                )
        );
        feature.setAttribute("point", point);
    }

    private void createNewFeature(RfTagLocation location, GeometryFactory geomFactory) {
        Point point;
        point = geomFactory.createPoint(
                new Coordinate(
                        location.getX(),
                        location.getY()
                )
        );
        features.add(SimpleFeatureBuilder.build(
                type,
                new Object[]{
                        point,
                        location.getTagId(),
                        location.getFullName()
                },
                location.getTagId()));
    }

    private TrackedLayer createEmptyLayer() {
        features = new ArrayList<>();
        Style style = SLD.createPointStyle("Star", Color.BLUE, Color.BLUE, 0.3f, 10);
        SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();

        // set the name
        featureTypeBuilder.setName("Konum");

        // add some attribute
        featureTypeBuilder.add("point", Point.class);
        featureTypeBuilder.add("tagid", String.class);
        featureTypeBuilder.add("name", String.class);

        // add a geometry property
        featureTypeBuilder.setCRS(DefaultGeographicCRS.WGS84);

        // build the type
        type = featureTypeBuilder.buildFeatureType();

        ListFeatureCollection collection = new ListFeatureCollection(type, features);

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
