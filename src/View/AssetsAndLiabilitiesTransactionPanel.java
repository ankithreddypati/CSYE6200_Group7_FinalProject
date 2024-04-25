package View;

import Model.AssetLiabilityTransaction;
import Service.AssetLiabilityTransactionService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;

public class AssetsAndLiabilitiesTransactionPanel extends JPanel {
    private AssetLiabilityTransactionService service;
    private DefaultTableModel assetsTableModel;
    private DefaultTableModel liabilitiesTableModel;

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
    }

    private JPanel setupNetWorthAndPieChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel netWorthLabel = new JLabel("Current Net Worth: $" + (service.getTotalValueByCategory("Asset") + service.getTotalValueByCategory("Liability")));
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
        service.getSortedTransactionsByDate().stream()
                .filter(t -> "Asset".equals(t.getCategory()))
                .forEach(t -> assetsTableModel.addRow(new Object[]{
                        new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                        t.getValue(),
                        t.getCategory(),
                        t.getSubcategory(),
                        t.getDescription()
                }));
        //fillTableModel(assetsTableModel, "Asset");
        JTable table = new JTable(assetsTableModel);
        table.setRowSorter(new TableRowSorter<>(assetsTableModel));
        return new JScrollPane(table);
    }

    private JScrollPane setupLiabilitiesTable() {
        String[] columns = {"Date", "Value", "Category", "Subcategory", "Description"};
        liabilitiesTableModel = new DefaultTableModel(columns, 0);
        service.getSortedTransactionsByDate().stream()
                .filter(t -> "Liability".equals(t.getCategory()))
                .forEach(t -> liabilitiesTableModel.addRow(new Object[]{
                        new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                        t.getValue(),
                        t.getCategory(),
                        t.getSubcategory(),
                        t.getDescription()
                }));
        //fillTableModel(liabilitiesTableModel, "Liability");
        JTable table = new JTable(liabilitiesTableModel);
        table.setRowSorter(new TableRowSorter<>(liabilitiesTableModel));
        return new JScrollPane(table);
    }

    /*private void fillTableModel(DefaultTableModel model, String category) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        service.getTransactions().stream()
                .filter(t -> category.equals(t.getCategory()))
                .forEach(t -> model.addRow(new Object[]{
                        sdf.format(t.getDate()),
                        t.getValue(),
                        t.getCategory(),
                        t.getSubcategory(),
                        t.getDescription()
                }));
    }*/
}

