package tr.com.minesoft.minetrack.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.helpers.Export;
import tr.com.minesoft.minetrack.helpers.TimeAndRid;
import tr.com.minesoft.minetrack.messages.Messages;
import tr.com.minesoft.minetrack.model.RFIDReader;
import tr.com.minesoft.minetrack.model.lists.RFIDReaderList;
import tr.com.minesoft.minetrack.model.lists.TrackedList;
import tr.com.minesoft.minetrack.view.dialogs.report.DetailedReportView;

public class DetailedReportController implements ActionListener {
	private final DetailedReportView parent;

	public DetailedReportController(final DetailedReportView parent) {
		this.parent = parent;
	}

	public void actionPerformed(final ActionEvent e) {
		JButton jButton = (JButton) e.getSource();
		DefaultTableModel model = (DefaultTableModel) parent.getTable().getModel();

		String name = jButton.getName();
		switch (name) {
		case "show":
			showPersonelReport(model);
			break;
		case "export":
			exportModel(model);
			break;
		default:
		}
	}

	private void exportModel(final DefaultTableModel model) {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter xlsFilter = new FileNameExtensionFilter(".xlsx", "Microsoft Excel Documents");
		fileChooser.addChoosableFileFilter(xlsFilter);
		fileChooser.setAcceptAllFileFilterUsed(true);
		int result = fileChooser.showSaveDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

			Export.toExcel(parent.getTable(), selectedFile);
		}
	}

	private void showPersonelReport(final DefaultTableModel model) {
		Map<String, RFIDReader> mapOfReaders = RFIDReaderList.getInstance().getList();

		model.setRowCount(0);

		// tarih sorgu yap
		DateTimeFormatter formatter = DateTimeFormat.forPattern(Messages.getString("DailyReportView.datepattern"));
		DateTime dt1 = formatter.parseDateTime(parent.getEntryDate1().getText());
		DateTime dt2 = dt1.plusDays(1); // bir sonraki gun

		int index = parent.getAdSoyadBox().getSelectedIndex();
		String nameSpaceSurname = parent.getAdSoyadBox().getItemAt(index).toString();
		String[] parts = nameSpaceSurname.split(" ");
		String fname = parts[0];
		String lname = parts[1];
		String tid = TrackedList.getInstance().getTidByNameSurname(fname, lname);

		List<TimeAndRid> list = Objects.requireNonNull(DAOHelper.getDetailedReportDAO()).get(tid, dt1, dt2);

		for (TimeAndRid o : list) {

			DateTimeFormatter toHourWithMinute = DateTimeFormat
					.forPattern(Messages.getString("DailyReportView.timepattern")); //$NON-NLS-1$
			String time = toHourWithMinute.print(o.getDt());
			String rid = o.getRid();

			RFIDReader rfidReader = mapOfReaders.get(rid);

			if (rfidReader != null){
				String readerName = rfidReader.getName();
				model.addRow(new Object[] { time, readerName });
			}
		}
	}
}
