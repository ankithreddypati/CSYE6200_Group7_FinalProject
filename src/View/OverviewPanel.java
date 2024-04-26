package View;

import Model.User;
import Service.BankTransactionService;
import Service.AssetLiabilityTransactionService;
import Service.InvestmentTransactionService;

import javax.swing.*;
import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

public class OverviewPanel extends JPanel {
    private BankTransactionService bankTransactionService;
    private AssetLiabilityTransactionService assetLiabilityTransactionService;
    private InvestmentTransactionService investmentTransactionService;

    public OverviewPanel(User user, BankTransactionService bankService, AssetLiabilityTransactionService assetService, InvestmentTransactionService investmentService) {
        this.bankTransactionService = bankService;
        this.assetLiabilityTransactionService = assetService;
        this.investmentTransactionService = investmentService;

        initUI();
    }

    private void initUI() {
        setBackground(new Color(0, 153, 76)); // Set the background color to green
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for layout management

        // Fetch data
        double totalBalance = bankTransactionService.calculateCurrentBalance();
        double netWorth = assetLiabilityTransactionService.calculateCurrentNetWorth();
        double totalInvestments = investmentTransactionService.calculateTotalCurrentValue();

        // Pie chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Bank Balance", totalBalance);
        dataset.setValue("Net Worth", netWorth);
        dataset.setValue("Investments", totalInvestments);

        JFreeChart chart = ChartFactory.createPieChart(
                "Total Worth Breakdown",   // chart title
                dataset,                   // dataset
                true,                      // include legend
                true,
                false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Bank Balance", new Color(24, 123, 58));
        plot.setSectionPaint("Net Worth", new Color(67, 67, 72));
        plot.setSectionPaint("Investments", new Color(79, 129, 189));
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setOpaque(false); // Transparent to show the green background

        add(chartPanel, BorderLayout.CENTER);

        // Panel to hold the stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10)); // Increased horizontal spacing to 50
        statsPanel.setOpaque(false);

        // Adding individual stat panels
        statsPanel.add(createStatPanel("TotalWorth =", totalBalance+netWorth+totalInvestments));
        statsPanel.add(createStatPanel("Total Bank Balance +", totalBalance));
        statsPanel.add(createStatPanel("Net Worth +", netWorth));
        statsPanel.add(createStatPanel("Total Investments", totalInvestments));

        add(statsPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatPanel(String label, double amount) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); // Transparent to show the green background
        JLabel titleLabel = new JLabel(label, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        JLabel amountLabel = new JLabel("$" + formatMoney(amount), SwingConstants.CENTER);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        amountLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(amountLabel, BorderLayout.CENTER);
        return panel;
    }

    private String formatMoney(double amount) {
        return String.format("%,.2f", amount);
    }
}
