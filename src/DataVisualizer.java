import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class DataVisualizer extends JFrame {
    private JTabbedPane tabbedPane;
    private StatsPanel statsPanelFall;
    private StatsPanel statsPanelSpring;
    private JPanel statsPanelContainer;
    private ChartPanelStats chartPanelStats;

    public DataVisualizer() throws IOException {
        LoadData loadData = new LoadData();

        String filepath_fall = "src/fall23_filtered_checkin_data.txt";
        String filepath_spring = "src/spr23_filtered_checkin_data.txt";

        List<Student> students_fall = loadData.loadStudents(filepath_fall);
        List<Student> students_spring = loadData.loadStudents(filepath_spring);

        tabbedPane = new JTabbedPane();
        TablePanel tablePanelFall = new TablePanel(students_fall);
        TablePanel tablePanelSpring = new TablePanel(students_spring);
        statsPanelFall = new StatsPanel(students_fall);
        statsPanelSpring = new StatsPanel(students_spring);

        chartPanelStats = new ChartPanelStats(students_spring);

        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("DISCLAIMER: UNOFFICIAL DATA", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.RED);

        JLabel subTitleLabel = new JLabel("UCA Tutoring Center Study Hall Data", JLabel.CENTER);
        subTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        labelPanel.add(subTitleLabel);
        labelPanel.add(titleLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.NORTH);
        statsPanelContainer = new JPanel(new BorderLayout());
        statsPanelContainer.add(statsPanelSpring, BorderLayout.CENTER);
        topPanel.add(statsPanelContainer, BorderLayout.CENTER);

        tabbedPane.addTab("Spring 2023", tablePanelSpring);
        tabbedPane.addTab("Fall 2023", tablePanelFall);

        // Set up layout
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(chartPanelStats, BorderLayout.SOUTH); // Keep the chart at the bottom

        // Update stats and chart when user clicks different tab
        tabbedPane.addChangeListener(e -> {
            statsPanelContainer.removeAll();

            if (tabbedPane.getSelectedIndex() == 0) { // Spring tab
                statsPanelSpring.updateStudents(students_spring);
                statsPanelContainer.add(statsPanelSpring, BorderLayout.CENTER);
                chartPanelStats.updateChart(students_spring); // Update chart for spring data
            } else { // Fall tab
                statsPanelFall.updateStudents(students_fall);
                statsPanelContainer.add(statsPanelFall, BorderLayout.CENTER);
                chartPanelStats.updateChart(students_fall); // Update chart for fall data
            }

            statsPanelContainer.revalidate();
            statsPanelContainer.repaint();
        });

        // Window settings
        setTitle("Student Statistics and Data");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new DataVisualizer();
    }
}