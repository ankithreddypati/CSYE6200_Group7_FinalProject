package View;

import Model.User;
import Service.BankTransactionService;
import Service.AssetLiabilityTransactionService;
import Service.InvestmentTransactionService;

import javax.swing.*;
import java.awt.*;

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
        setLayout(new GridLayout(3, 1)); // Using GridLayout to organize the labels vertically

        // Fetch data
        double totalBalance = bankTransactionService.getTotalBalance();
        double netWorth = assetLiabilityTransactionService.getNetWorth();
        double investmentPerformance = investmentTransactionService.getInvestmentPerformance();

        // Create labels to display the fetched data
        JLabel balanceLabel = new JLabel("Total Bank Balance: $" + formatMoney(totalBalance));
        JLabel netWorthLabel = new JLabel("Net Worth: $" + formatMoney(netWorth));
        JLabel performanceLabel = new JLabel("Investment Performance: " + investmentPerformance + "%");

        // Adding labels to the panel
        add(balanceLabel);
        add(netWorthLabel);
        add(performanceLabel);
    }

    private String formatMoney(double amount) {
        return String.format("%,.2f", amount);
    }
}
