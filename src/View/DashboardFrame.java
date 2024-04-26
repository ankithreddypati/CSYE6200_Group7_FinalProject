package View;

import Model.User;
import Service.AssetLiabilityTransactionService;
import Service.BankTransactionService;
import Service.InvestmentTransactionService;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private JSplitPane splitPane;
    private JTabbedPane leftTabbedPane;
    private JPanel rightPanel;
    private CardLayout cardLayout;

    private User currentUser;
    private BankTransactionService bankTransactionService;
    private AssetLiabilityTransactionService assetLiabilityTransactionService;

    public DashboardFrame(User user) {
        this.currentUser = user;
        setTitle("User Dashboard: " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(currentUser.getBankTransactionsPath());
        this.bankTransactionService = new BankTransactionService(currentUser.getBankTransactionsPath());
        this.assetLiabilityTransactionService = new AssetLiabilityTransactionService(currentUser.getAssetsLiabilitiesPath());
        System.out.println(currentUser.getAssetsLiabilitiesPath());
        initUI();
    }

    private void initUI() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);
        splitPane.setContinuousLayout(true);

        setupLeftTabbedPane();
        setupRightPanel();

        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private void setupLeftTabbedPane() {
        leftTabbedPane = new JTabbedPane();
        leftTabbedPane.setBackground(new Color(0, 153, 76));

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1)); // Increase grid rows to 5 for the new button
        JButton overviewButton = new JButton("Overview");
        JButton bankTransactionsButton = new JButton("Bank Transactions");
        JButton assetsLiabilitiesButton = new JButton("Assets & Liabilities");
        JButton investmentsButton = new JButton("Investments");
        JButton logoutButton = new JButton("Logout");

        overviewButton.addActionListener(e -> cardLayout.show(rightPanel, "Overview"));
        bankTransactionsButton.addActionListener(e -> cardLayout.show(rightPanel, "BankTransactions"));
        assetsLiabilitiesButton.addActionListener(e -> cardLayout.show(rightPanel, "AssetsLiabilities"));
        investmentsButton.addActionListener(e -> cardLayout.show(rightPanel, "Investments"));
        logoutButton.addActionListener(e -> {
            this.dispose();
            new LandingPageFrame().setVisible(true);
        });

        buttonPanel.add(overviewButton);
        buttonPanel.add(bankTransactionsButton);
        buttonPanel.add(assetsLiabilitiesButton);
        buttonPanel.add(investmentsButton);
        buttonPanel.add(logoutButton);

        leftTabbedPane.addTab("Dashboard", buttonPanel);
        splitPane.setLeftComponent(leftTabbedPane);
    }

    private void setupRightPanel() {
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);

        JPanel overviewPanel = new JPanel();
        overviewPanel.add(new JLabel("Overview details displayed here."));

        BankTransactionsPanel bankTransactionsPanel = new BankTransactionsPanel(bankTransactionService);
        AssetsAndLiabilitiesTransactionPanel assetsLiabilitiesPanel = new AssetsAndLiabilitiesTransactionPanel(assetLiabilityTransactionService);
        InvestmentTransactionService invServ = new InvestmentTransactionService(currentUser.getAssetsLiabilitiesPath());
        InvestmentTransactionPanel invPanel = new InvestmentTransactionPanel(invServ);
        JPanel investmentsPanel = new JPanel();
        investmentsPanel.add(new JLabel("Investment details displayed here."));

        rightPanel.add(overviewPanel, "Overview");
        rightPanel.add(bankTransactionsPanel, "BankTransactions");
        rightPanel.add(assetsLiabilitiesPanel, "AssetsLiabilities");
        rightPanel.add(invPanel, "Investments");

        splitPane.setRightComponent(rightPanel);
    }
}
