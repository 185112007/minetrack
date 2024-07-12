package tr.com.minesoft.minetrack.view.dialogs.report;

import lombok.Getter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.geotools.map.MapContent;
import org.geotools.swing.JMapPane;
import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.helpers.CustomLayer;
import tr.com.minesoft.minetrack.helpers.DateLabelFormatter;
import tr.com.minesoft.minetrack.helpers.FileOp;
import tr.com.minesoft.minetrack.messages.Messages;
import tr.com.minesoft.minetrack.model.RfTagLocation;
import tr.com.minesoft.minetrack.model.Tracked;
import tr.com.minesoft.minetrack.model.lists.TrackedList;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
public class SimulationReportView extends JDialog {

    private JFormattedTextField entryDate1;
    private JDatePickerImpl datePicker1;
    private JComboBox<Tracked> trackedBox;
    private final MapContent map;
    private final CustomLayer customLayer;

    public SimulationReportView(JFrame jFrame) {
        super(jFrame, Messages.getString("UI.simulationReport"), true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        this.getContentPane().add(panel);

        setDefaultDate();

        map = new MapContent();

        final JMapPane mapPane = new JMapPane(map);
        mapPane.setBackground(Color.WHITE);
        mapPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        FileOp.reuseLayers(map);
        customLayer = new CustomLayer();
        customLayer.addTrackedLayer(map, mapPane);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(createDetailsPanel(), gbc);

        //
        JPanel mapPanel = new JPanel();
        mapPanel.setPreferredSize(new Dimension(200, 300));
        mapPanel.setLayout(new BorderLayout());
        mapPanel.add(mapPane, BorderLayout.CENTER);

        //
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(mapPanel, gbc);

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JPanel(), gbc);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int width = 700;
        int height = 500;

        this.setBounds(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2, width, height);

        this.center(jFrame);
        this.pack();
        panel.revalidate();
        this.setVisible(true);
    }

    private JPanel createDetailsPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel labelEntryTime = new JLabel("Tarih");
        JLabel fnamlnamelabel = new JLabel("Ad/Soyad");

        trackedBox = new JComboBox<>();

        Map<String, Tracked> trackedList = TrackedList.getInstance().getList();
        for (String tid : trackedList.keySet()) {
            trackedBox.addItem(trackedList.get(tid));
        }

        JButton showBtn = new JButton("Göster");
        showBtn.setName("show");
        showBtn.addActionListener(this::actionListenerV1);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // label entry time
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(labelEntryTime, gbc);

        // entry text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(entryDate1, gbc);

        // entry date picker
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(datePicker1, gbc);

        // label fname/lname
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(fnamlnamelabel, gbc);

        // combobox fname/lname
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(trackedBox, gbc);

        // show button
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(showBtn, gbc);

        Border loweredbevel = BorderFactory.createLoweredBevelBorder();

        Border compound = BorderFactory.createTitledBorder(loweredbevel, "Tarih Seçiniz");

        panel.setBorder(compound);

        panel.setPreferredSize(new Dimension(650, 150));
        return panel;
    }

    private void actionListenerV1(ActionEvent event) {

        Tracked item = trackedBox.getItemAt(trackedBox.getSelectedIndex());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateTime1 = LocalDate.parse(entryDate1.getText(), fmt);
        LocalDate dateTime2 = dateTime1.plusDays(1);

        List<RfTagLocation> locations = Objects.requireNonNull(
                DAOHelper.getRfTagLocationDAO()).get(
                item.getTagId(),
                dateTime1.atStartOfDay(),
                dateTime2.atStartOfDay()
        );
        customLayer.simulateV1(locations);
    }

    private void center(JFrame jFrame) {
        Rectangle r = jFrame.getBounds();
        int x = r.x + (r.width - this.getSize().width) / 2;
        int y = r.y + (r.height - this.getSize().height) / 2;
        this.setLocation(x, y);
    }

    private void setDefaultDate() {
        UtilDateModel model1 = new UtilDateModel();
        model1.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(model1);
        datePicker1 = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        entryDate1 = datePicker1.getJFormattedTextField();
    }
}
