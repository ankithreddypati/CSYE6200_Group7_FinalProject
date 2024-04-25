package View;

import Model.InvestmentTransaction;
import Service.InvestmentTransactionService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class InvestmentTransactionPanel extends JPanel {
    private InvestmentTransactionService service;
    private DefaultTableModel transactionsTableModel;
    private JFreeChart lineChart;
    private JFreeChart pieChart;

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
        JPanel panel = new JPanel(new BorderLayout());

        // Setup chart panel
        JPanel chartPanel = new JPanel(new GridLayout(1, 2));
        chartPanel.setBorder(BorderFactory.createTitledBorder("Investment Performance & Summary")); // Add a titled border
        lineChart = createLineChart();
        pieChart = createPieChart();
        chartPanel.add(new ChartPanel(lineChart));
        chartPanel.add(new ChartPanel(pieChart));
        panel.add(chartPanel, BorderLayout.CENTER);

        // Setup table panel
        JPanel transactionsTablePanel = new JPanel(new BorderLayout());
        transactionsTablePanel.setBorder(BorderFactory.createTitledBorder("Investment Transactions")); // Add a titled border
        transactionsTableModel = new DefaultTableModel(new String[]{"Date", "Amount Invested", "Current Value", "Subcategory", "Description"}, 0);
        service.getTransactions().forEach(transaction -> {
            transactionsTableModel.addRow(new Object[]{
                    new SimpleDateFormat("yyyy-MM-dd").format(transaction.getDate()),
                    transaction.getAmountInvested(),
                    transaction.getCurrentValue(),
                    transaction.getSubcategory(),
                    transaction.getDescription()
            });
        });
        JTable table = new JTable(transactionsTableModel);
        table.setRowSorter(new TableRowSorter<>(transactionsTableModel));
        JScrollPane scrollPane = new JScrollPane(table);
        transactionsTablePanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(transactionsTablePanel, BorderLayout.SOUTH);

        return panel;
    }


    private JFreeChart createLineChart() {
        TimeSeries seriesInvested = new TimeSeries("Amount Invested");
        TimeSeries seriesCurrentValue = new TimeSeries("Current Value");
        for (InvestmentTransaction transaction : service.getTransactions()) {
            seriesInvested.add(new Day(transaction.getDate()), transaction.getAmountInvested());
            seriesCurrentValue.add(new Day(transaction.getDate()), transaction.getCurrentValue());
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(seriesInvested);
        dataset.addSeries(seriesCurrentValue);
        return ChartFactory.createTimeSeriesChart(
                "Investment Performance",
                "Date",
                "Value",
                dataset,
                true,
                true,
                false
        );
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        double totalInvestment = service.calculateTotalInvestment();
        double totalCurrentValue = service.getTransactions().stream().mapToDouble(InvestmentTransaction::getCurrentValue).sum();

        if (totalCurrentValue < totalInvestment) {
            dataset.setValue("Total Investment", totalInvestment);
            dataset.setValue("Total Loss", totalInvestment - totalCurrentValue);
        } else {
            dataset.setValue("Total Investment", totalInvestment);
            dataset.setValue("Total Profit", totalCurrentValue - totalInvestment);
        }

        return ChartFactory.createPieChart(
                "Investment Summary", // chart title
                dataset, // dataset
                true, // include legend
                true, // tooltips
                false // urls
        );
    }
}
