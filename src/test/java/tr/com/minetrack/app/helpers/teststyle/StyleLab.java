package tr.com.minetrack.app.helpers.teststyle;

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;

import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.ExternalGraphic;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.expression.Expression;

public class StyleLab {
	static StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
	static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();

	public static void main(String[] args) throws Exception {
		StyleLab me = new StyleLab();
		me.displayShapefile();
	}

	/**
	 * Prompts the user for a shapefile (unless a filename is provided on the
	 * command line; then creates a simple Style and displays the shapefile on
	 * screen
	 */
	private void displayShapefile() throws Exception {
		File file = JFileDataStoreChooser.showOpenFile("shp", null);
		if (file == null) {
			return;
		}

		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		FeatureSource<?, ?> featureSource = store.getFeatureSource();

		// Create a map content and add our shapefile to it
		MapContent map = new MapContent();
		map.setTitle("StyleLab");

		// Create a basic Style to render the features
		Style style = createStyle(file, featureSource);

		// Add the features and the associated Style object to
		// the MapContent as a new Layer
		Layer layer = new FeatureLayer(featureSource, style);
		map.addLayer(layer);

		// Now display the map
		JMapFrame.showMap(map);
	}

	/**
	 * Create a Style to display the features. If an SLD file is in the same
	 * directory as the shapefile then we will create the Style by processing this.
	 * Otherwise we display a JSimpleStyleDialog to prompt the user for preferences.
	 */
	private Style createStyle(File file, FeatureSource<?, ?> featureSource) {

		return createStyle2(featureSource);

	}

	/**
	 * Here is a programmatic alternative to using JSimpleStyleDialog to get a
	 * Style. This methods works out what sort of feature geometry we have in the
	 * shapefile and then delegates to an appropriate style creating method.
	 */
	private Style createStyle2(FeatureSource<?, ?> featureSource) {

		StyleBuilder styleBuilder = new StyleBuilder();
		Style style = styleBuilder.createStyle();
		Fill fill = styleBuilder.createFill(Color.BLUE);
		Expression size = filterFactory.literal(15.0f);
		Stroke stroke = styleBuilder.createStroke(Color.black);
		
		ExternalGraphic extGraphic = styleBuilder.createExternalGraphic("data/images/emp.png", "image/png");;
		try {
			extGraphic.setLocation(new File("data/images/emp.gif").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graphic graphic = styleBuilder.createGraphic(extGraphic, styleBuilder.createMark("circle", fill, stroke), null);
        graphic.setSize(size);

        PointSymbolizer pointSymbolizer = styleBuilder.createPointSymbolizer(graphic);

		Rule rule = styleBuilder.createRule(pointSymbolizer);
		FeatureTypeStyle featureTypeStyle = styleBuilder.createFeatureTypeStyle("Feature", rule);
		style.featureTypeStyles().add(featureTypeStyle);

		return style;
	}

}
