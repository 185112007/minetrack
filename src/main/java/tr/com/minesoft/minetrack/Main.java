package tr.com.minesoft.minetrack;

import javax.swing.SwingUtilities;

import org.geotools.map.MapContent;

import tr.com.minesoft.minetrack.helpers.FileOp;
import tr.com.minesoft.minetrack.view.UI;

/**
 * Main class
 * 
 * @author Gafur Hayytbayev
 *
 */

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			MapContent map = new MapContent();
			FileOp.loadFiles(map);
			new UI(map);
		});
	}
}
