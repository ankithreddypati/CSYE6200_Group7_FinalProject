package View;

import Model.BankTransaction;
import Service.BankTransactionService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;

public class BankTransactionsPanel extends JPanel {
    private BankTransactionService service;
    private DefaultTableModel creditsTableModel;
    private DefaultTableModel debitsTableModel;

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
    }

    private JPanel setupCurrentBalanceAndLineChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel balanceLabel = new JLabel("Current Bank Balance: $" + service.calculateCurrentBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(balanceLabel, BorderLayout.NORTH);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double balance = 0;
        for (BankTransaction transaction : service.getTransactions()) {
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
        double totalCredits = service.getTransactions().stream()
                .filter(t -> "Credit".equals(t.getType()))
                .mapToDouble(BankTransaction::getAmount).sum();
        double totalDebits = service.getTransactions().stream()
                .filter(t -> "Debit".equals(t.getType()))
                .mapToDouble(BankTransaction::getAmount).sum();

        dataset.setValue("Incomes", totalCredits);
        dataset.setValue("Expenses", totalDebits);

        JFreeChart chart = ChartFactory.createPieChart("Incomes vs Expenses", dataset, true, true, false);
        return new ChartPanel(chart);
    }

    private JScrollPane setupCreditsTable() {
        creditsTableModel = new DefaultTableModel(new String[]{"Date", "Amount", "Category", "Description"}, 0);
        service.getSortedTransactionsByDate().stream()
                .filter(t -> "Credit".equals(t.getType()))
                .forEach(t -> creditsTableModel.addRow(new Object[]{
                        new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                        t.getAmount(),
                        t.getCategory(),
                        t.getDescription()
                }));
        JTable table = new JTable(creditsTableModel);
        table.setRowSorter(new TableRowSorter<>(creditsTableModel));
        return new JScrollPane(table);
    }

    private JScrollPane setupDebitsTable() {
        debitsTableModel = new DefaultTableModel(new String[]{"Date", "Amount", "Category", "Description"}, 0);
        service.getSortedTransactionsByDate().stream()
                .filter(t -> "Debit".equals(t.getType()))
                .forEach(t -> debitsTableModel.addRow(new Object[]{
                        new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()),
                        t.getAmount(),  // Display debits as positive values
                        t.getCategory(),
                        t.getDescription()
                }));
        JTable table = new JTable(debitsTableModel);
        table.setRowSorter(new TableRowSorter<>(debitsTableModel));
        return new JScrollPane(table);
    }
}
