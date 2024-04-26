package View;

import Model.AssetLiabilityTransaction;
import Service.AssetLiabilityTransactionService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class AssetsAndLiabilitiesTransactionPanel extends JPanel {
    private AssetLiabilityTransactionService service;
    private DefaultTableModel assetsTableModel;
    private DefaultTableModel liabilitiesTableModel;
    private JTable assetsTable;
    private JTable liabilitiesTable;

    public AssetsAndLiabilitiesTransactionPanel(AssetLiabilityTransactionService service) {
        super(new BorderLayout());
        this.service = service;

        setLayout(new BorderLayout());
        JPanel topPanel = setupNetWorthAndPieChartPanel();
        JScrollPane assetsTable = setupAssetsTable();
        JScrollPane liabilitiesTable = setupLiabilitiesTable();

        JSplitPane tablesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, assetsTable, liabilitiesTable);
        tablesPane.setResizeWeight(0.5);

        add(topPanel, BorderLayout.NORTH);
        add(tablesPane, BorderLayout.CENTER);

        // Add sort buttons
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton sortByDateButton = new JButton("Sort by Date");
        JButton sortByValueButton = new JButton("Sort by Value");
        JButton sortByCategoryButton = new JButton("Sort by Category");
        sortPanel.add(sortByDateButton);
        sortPanel.add(sortByValueButton);
        sortPanel.add(sortByCategoryButton);
        add(sortPanel, BorderLayout.SOUTH);

        // Add action listeners for sort buttons
        sortByDateButton.addActionListener(e -> {
            updateTableData(service.getSortedTransactionsByDate());
        });
        sortByValueButton.addActionListener(e -> {
            updateTableData(service.getSortedTransactionsByValue());
        });
        sortByCategoryButton.addActionListener(e -> {
            updateTableData(service.getSortedTransactionsByCategory());
        });

        // Populate the tables initially
        updateTableData(service.getTransactions());
    }

    private void updateTableData(List<AssetLiabilityTransaction> transactions) {
        assetsTableModel.setRowCount(0);
        liabilitiesTableModel.setRowCount(0);

        List<AssetLiabilityTransaction> assets = transactions.stream()
                .filter(t -> "Asset".equals(t.getCategory()))
                .collect(Collectors.toList());
        List<AssetLiabilityTransaction> liabilities = transactions.stream()
                .filter(t -> "Liability".equals(t.getCategory()))
                .collect(Collectors.toList());

        assets.forEach(t -> assetsTableModel.addRow(new Object[]{
                new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                t.getValue(),
                t.getCategory(),
                t.getSubcategory(),
                t.getDescription()
        }));

        liabilities.forEach(t -> liabilitiesTableModel.addRow(new Object[]{
                new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                t.getValue(),
                t.getCategory(),
                t.getSubcategory(),
                t.getDescription()
        }));

        assetsTable.repaint();
        liabilitiesTable.repaint();
    }

    private JPanel setupNetWorthAndPieChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel netWorthLabel = new JLabel("Current Net Worth: $" + service.calculateCurrentNetWorth());
        netWorthLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(netWorthLabel, BorderLayout.NORTH);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(service.getTotalValueByCategory("Asset"), "Assets", "");
        dataset.addValue(-(service.getTotalValueByCategory("Liability")), "Liabilities", "");

        JFreeChart chart = ChartFactory.createBarChart(
                "Assets vs Liabilities",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane setupAssetsTable() {
        String[] columns = {"Date", "Value", "Category", "Subcategory", "Description"};
        assetsTableModel = new DefaultTableModel(columns, 0);
        assetsTable = new JTable(assetsTableModel);
        return new JScrollPane(assetsTable);
    }

    private JScrollPane setupLiabilitiesTable() {
        String[] columns = {"Date", "Value", "Category", "Subcategory", "Description"};
        liabilitiesTableModel = new DefaultTableModel(columns, 0);
        liabilitiesTable = new JTable(liabilitiesTableModel);
        return new JScrollPane(liabilitiesTable);
    }
}