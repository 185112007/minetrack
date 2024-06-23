package tr.com.minesoft.minetrack.helpers;

import org.geotools.feature.FeatureCollection;
import org.geotools.map.FeatureLayer;
import org.geotools.styling.Style;

public class TrackedLayer extends FeatureLayer {

	public TrackedLayer(FeatureCollection<?, ?> collection, Style style) {
		super(collection, style);
	}
}
