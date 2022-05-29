package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class MainWindow extends JFrame {

    private static String[] availableLanguages = {"pl", "en"};

    private List<Record> data;

    private LanguageView languageView;

    MainWindow(List<Record> data) {
        this.data = data;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Travel Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLocation(screenSize.width / 2 - 400, screenSize.height / 2 - 300);

        languageView = new LanguageView(this);

        setContentPane(languageView);

        pack();
        setVisible(true);
    }


    private class LanguageView extends JPanel implements ActionListener {

        MainWindow mainWindow;

        List<JButton> jButtons = new ArrayList<>();

        LanguageView(MainWindow mainWindow) {
            this.mainWindow = mainWindow;

            int i = 0;

            for (String lang : MainWindow.availableLanguages) {
                JButton jButton = new JButton(lang);
                jButton.setActionCommand(lang);
                jButton.addActionListener(this);
                jButtons.add(i, jButton);
                this.add(jButtons.get(i++));
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mainWindow.setLang(e.getActionCommand());

            for (JButton jButton : jButtons) {
                this.remove(jButton);
                jButton.setEnabled(false);
            }
        }
    }

    private List<Record> getData() {
        return data;
    }

    private String lang;

    private void setLang(String actionCommand) {
        lang = actionCommand;


        String[] columns = new String[] {
                "countryCode",
                "countryName",
                "dateFrom",
                "dateTo",
                "location",
                "price",
                "currency"
        };

        Object[][] data = new Object[getData().size()][];
        for (int i = 0; i < getData().size(); i++) {
            Record record = getData().get(i);
            data[i] = record.toArray();
        }

        JTable table = new JTable(data, columns);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        table.setPreferredSize(new Dimension(800, 600));
        table.setLocation(screenSize.width / 2 - 400, screenSize.height / 2 - 300);

        remove(languageView);
        add(new JScrollPane(table));

        revalidate();
        repaint();
    }
}