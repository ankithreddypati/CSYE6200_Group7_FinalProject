package View;

import Model.InvestmentTransaction;
import Service.InvestmentTransactionService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;

public class InvestmentTransactionPanel extends JPanel {
    private InvestmentTransactionService service;
    private DefaultTableModel transactionsTableModel;

    public InvestmentTransactionPanel(InvestmentTransactionService service) {
        super(new BorderLayout());
        this.service = service;

        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding

        JPanel topPanel = setupTotalInvestmentPanel();
        JPanel bottomPanel = setupBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    private JPanel setupTotalInvestmentPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align components to the left
        panel.setBorder(BorderFactory.createTitledBorder("Total Investment")); // Add a titled border

        JLabel totalInvestmentLabel = new JLabel("Total Investment: $" + service.calculateTotalInvestment());
        totalInvestmentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(totalInvestmentLabel);

        return panel;
    }

    private JPanel setupBottomPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 row, 2 columns, horizontal gap: 10px

        JPanel pieChartPanel = setupPieChartPanel();
        JPanel transactionsTablePanel = setupTransactionsTablePanel();

        panel.add(pieChartPanel);
        panel.add(transactionsTablePanel);

        return panel;
    }

    private JPanel setupPieChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Investment Breakdown")); // Add a titled border

        DefaultPieDataset dataset = createPieDataset();
        JFreeChart chart = createPieChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private DefaultPieDataset createPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        // You may need to modify this based on your investment data structure
        // For example, summing up investments by category
        dataset.setValue("Category 1", 1000); // Replace with actual values
        dataset.setValue("Category 2", 2000); // Replace with actual values

        return dataset;
    }

    private JFreeChart createPieChart(DefaultPieDataset dataset) {
        return ChartFactory.createPieChart("Investment Breakdown", dataset, true, true, false);
    }

    private JPanel setupTransactionsTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Investment Transactions")); // Add a titled border

        transactionsTableModel = new DefaultTableModel(new String[]{"Date", "Amount Invested", "Current Value", "Subcategory", "Description"}, 0);
        service.getSortedTransactionsByDate().forEach(t -> transactionsTableModel.addRow(new Object[]{
                new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                t.getAmountInvested(),
                t.getCurrentValue(),
                t.getSubcategory(),
                t.getDescription()
        }));
        JTable table = new JTable(transactionsTableModel);
        table.setRowSorter(new TableRowSorter<>(transactionsTableModel));
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
