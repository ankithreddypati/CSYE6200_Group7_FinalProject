package View;

import Model.User;
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

    public DashboardFrame(User user) {
        this.currentUser = user;
        setTitle("User Dashboard: " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(currentUser.getBankTransactionsPath());
        // Initialize the service with the path to the bank transactions CSV
        this.bankTransactionService = new BankTransactionService(currentUser.getBankTransactionsPath());

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

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        JButton bankTransactionsButton = new JButton("Bank Transactions");
        bankTransactionsButton.addActionListener(e -> cardLayout.show(rightPanel, "BankTransactions"));

        JButton assetsLiabilitiesButton = new JButton("Assets & Liabilities");
        assetsLiabilitiesButton.addActionListener(e -> cardLayout.show(rightPanel, "AssetsLiabilities"));

        JButton investmentsButton = new JButton("Investments");
        investmentsButton.addActionListener(e -> cardLayout.show(rightPanel, "Investments"));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new LandingPageFrame().setVisible(true);
        });

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

        // Pass the service to the panel
        BankTransactionsPanel bankTransactionsPanel = new BankTransactionsPanel(bankTransactionService);

        InvestmentTransactionService invServ = new InvestmentTransactionService(currentUser.investmentsPath());
        InvestmentTransactionPanel invPanel = new InvestmentTransactionPanel(invServ);
        JPanel assetsLiabilitiesPanel = new JPanel();
        assetsLiabilitiesPanel.add(new JLabel("Assets and liabilities details displayed here."));
        JPanel investmentsPanel = new JPanel();
        investmentsPanel.add(new JLabel("Investment details displayed here."));

        rightPanel.add(bankTransactionsPanel, "BankTransactions");
        rightPanel.add(assetsLiabilitiesPanel, "AssetsLiabilities");
        rightPanel.add(invPanel, "Investments");

        splitPane.setRightComponent(rightPanel);
    }
}
