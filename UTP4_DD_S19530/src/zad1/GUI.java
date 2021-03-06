package zad1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class GUI extends JFrame implements ActionListener {
    private Locale selectedLocale = new Locale("pl", "PL");
    private JTable table;
    JLabel dropdownLabel;

    private Travel[] data;

    private static final long serialVersionUID = 1L;

    public GUI(Travel[] data) {
        this.data = data;

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        this.table = new JTable();
        this.table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(this.table);

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setBorder(new EmptyBorder(0, 8, 0, 8));
        dropdownPanel.setLayout(new BorderLayout());
        this.dropdownLabel = new JLabel();
        JComboBox<String> languageDropdown = new JComboBox<String>(
                this.getDropdownItems()
        );
        languageDropdown.setSelectedIndex(this.getInitialSelectedItem());
        languageDropdown.addActionListener(this);
        dropdownPanel.add(this.dropdownLabel, "West");
        dropdownPanel.add(languageDropdown, "Center");

        this.getContentPane().add(dropdownPanel, "North");
        this.getContentPane().add(scrollPane, "Center");

        this.updateData(this.selectedLocale);
        this.setup();
    }

    private void setup() {
        this.setTitle("JTable Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();
        this.setSize(500, 200);
        this.setVisible(true);
    }

    private HashMap<String, Locale> getSupportedMap() {
        HashMap<String, Locale> supportedLocales = new HashMap<String, Locale>();

        for (int i = 0; i < Constants.supportedLocales.length; i++) {
            Locale supportedLocale = Constants.supportedLocales[i];
            String langName = supportedLocale.getDisplayLanguage(supportedLocale);

            supportedLocales.put(langName, supportedLocale);
        }

        return supportedLocales;
    }

    private String[] getDropdownItems() {
        String[] supportedLocales = new String[Constants.supportedLocales.length];

        for (int i = 0; i < Constants.supportedLocales.length; i++) {
            Locale supportedLocale = Constants.supportedLocales[i];
            String langName = supportedLocale.getDisplayLanguage(supportedLocale);

            supportedLocales[i] = langName;
        }

        return supportedLocales;
    }


    private int getInitialSelectedItem() {
        String[] dropdownItems = this.getDropdownItems();
        String selectedLangName = this.selectedLocale.getDisplayLanguage(this.selectedLocale);

        for (int i = 0; i < dropdownItems.length; i++) {
            String dropdownItem = dropdownItems[i];

            if (dropdownItem.equals(selectedLangName)) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String selectedItem = (String)cb.getSelectedItem();

        this.selectedLocale = this.getSupportedMap().get(selectedItem);
        this.updateData(this.selectedLocale);
    }

    public void updateData(Locale locale) {
        DbTableModel tableModel = new DbTableModel(this.data, locale);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        this.table.setModel(tableModel);
        this.dropdownLabel.setText(bundle.getString("language"));
    }
}