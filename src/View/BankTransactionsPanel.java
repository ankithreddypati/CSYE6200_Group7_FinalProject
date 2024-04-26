package View;

import Model.BankTransaction;
import Service.BankTransactionService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import java.lang.Math;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class BankTransactionsPanel extends JPanel {
    private BankTransactionService service;
    private DefaultTableModel creditsTableModel;
    private DefaultTableModel debitsTableModel;
    private JTable creditsTable;
    private JTable debitsTable;

    public BankTransactionsPanel(BankTransactionService service) {
        super(new BorderLayout());
        this.service = service;

        setLayout(new BorderLayout());
        JPanel topPanel = setupCurrentBalanceAndLineChartPanel();
        JPanel pieChartPanel = setupPieChartPanel();
        JScrollPane creditsTable = setupCreditsTable();
        JScrollPane debitsTable = setupDebitsTable();

        // Arrange panels
        JSplitPane tablesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, creditsTable, debitsTable);
        tablesPane.setResizeWeight(0.5);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(pieChartPanel);
        bottomPanel.add(tablesPane);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton sortByDateButton = new JButton("Sort by Date");
        JButton sortByAmountButton = new JButton("Sort by Amount");
        JButton sortByCategoryButton = new JButton("Sort by Category");
        sortPanel.add(sortByDateButton);
        sortPanel.add(sortByAmountButton);
        sortPanel.add(sortByCategoryButton);
        add(sortPanel, BorderLayout.SOUTH);

        sortByDateButton.addActionListener(e -> {
            updateTableData(service.getSortedTransactionsByDate());
        });
        sortByAmountButton.addActionListener(e -> {
            updateTableData(service.getSortedTransactionsByAmount());
        });
        sortByCategoryButton.addActionListener(e -> {
            updateTableData(service.getSortedTransactionsByCategory());
        });

        updateTableData(service.getTransactions());
    }

    private void updateTableData(List<BankTransaction> transactions) {
        creditsTableModel.setRowCount(0);
        debitsTableModel.setRowCount(0);

        List<BankTransaction> credits = transactions.stream()
                .filter(t -> "Credit".equals(t.getType()))
                .collect(Collectors.toList());
        List<BankTransaction> debits = transactions.stream()
                .filter(t -> "Debit".equals(t.getType()))
                .collect(Collectors.toList());

        credits.forEach(t -> creditsTableModel.addRow(new Object[]{
                new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                t.getAmount(),
                t.getCategory(),
                t.getDescription()
        }));

        debits.forEach(t -> debitsTableModel.addRow(new Object[]{
                new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                t.getAmount(),
                t.getCategory(),
                t.getDescription()
        }));

        creditsTable.repaint();
        debitsTable.repaint();
    }

    private JPanel setupCurrentBalanceAndLineChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel balanceLabel = new JLabel("Current Bank Balance: $" + service.calculateCurrentBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(balanceLabel, BorderLayout.NORTH);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<BankTransaction> sortedTransactions = service.getSortedTransactionsByDate();
        double balance = 0;
        for (BankTransaction transaction : sortedTransactions) {
            double amount = "Credit".equals(transaction.getType()) ? transaction.getAmount() : -transaction.getAmount();
            balance += amount;
            dataset.addValue(balance, "Balance", new SimpleDateFormat("yyyy-MM-dd").format(transaction.getDate()));
        }
        JFreeChart chart = ChartFactory.createLineChart("Balance Over Time", "Date", "Balance", dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private ChartPanel setupPieChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<BankTransaction> sortedTransactions = service.getSortedTransactionsByType();

        double totalCredits = sortedTransactions.stream()
                .filter(t -> "Credit".equals(t.getType()))
                .mapToDouble(BankTransaction::getAmount)
                .sum();

        double totalDebits = sortedTransactions.stream()
                .filter(t -> "Debit".equals(t.getType()))
                .mapToDouble(BankTransaction::getAmount)
                .sum();

        dataset.setValue("Incomes", totalCredits);
        dataset.setValue("Expenses", totalDebits);

        JFreeChart chart = ChartFactory.createPieChart("Incomes vs Expenses", dataset, true, true, false);
        return new ChartPanel(chart);
    }

    private JScrollPane setupCreditsTable() {
        creditsTableModel = new DefaultTableModel(new String[]{"Date", "Amount", "Category", "Description"}, 0);
        creditsTable = new JTable(creditsTableModel);
        return new JScrollPane(creditsTable);
    }

    private JScrollPane setupDebitsTable() {
        debitsTableModel = new DefaultTableModel(new String[]{"Date", "Amount", "Category", "Description"}, 0);
        debitsTable = new JTable(debitsTableModel);

        List<BankTransaction> sortedDebits = service.getSortedTransactionsByType().stream()
                .filter(t -> "Debit".equals(t.getType()))
                .collect(Collectors.toList());

        sortedDebits.forEach(t -> debitsTableModel.addRow(new Object[]{
                new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                Math.abs(t.getAmount()),  // Display debits as positive values
                t.getCategory(),
                t.getDescription()
        }));

        return new JScrollPane(debitsTable);
    }
}