package View;

import Model.User;
import Util.UserAuthentication;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class LandingPageFrame extends JFrame {
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private CardLayout cardLayout;

    public LandingPageFrame() {
        setTitle("Personal Finance Application");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        leftPanel = new JPanel();
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);

        setupLeftPanel();
        setupRightPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.5);

        getContentPane().add(splitPane);
        setVisible(true);
    }

    private void setupLeftPanel() {
        ImageIcon image = new ImageIcon("src/Assets/giphycash.gif");
        JLabel label = new JLabel(image);
        leftPanel.add(label);
    }

    private void setupRightPanel() {
        JPanel loginPanel = createLoginPanel();
        JPanel signupPanel = createSignupPanel();

        rightPanel.add(loginPanel, "Login");
        rightPanel.add(signupPanel, "Signup");

        cardLayout.show(rightPanel, "Login");
    }

    private JPanel createLoginPanel() {
        // Using BorderLayout to have more control over spacing and sizing
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        // Create a subpanel for form elements with GridLayout
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));  // Use GridLayout inside the subpanel

        // Add padding around the form
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // top, left, bottom, right padding

        // Username field
        formPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        formPanel.add(usernameField);

        // Password field
        formPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Adding the formPanel to the main panel
        panel.add(formPanel, BorderLayout.CENTER);

        // Button panel for login and switch buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(105, 190, 134));
        loginButton.setForeground(Color.black);
        loginButton.setPreferredSize(new Dimension(120, 45)); // Make the button smaller
        buttonPanel.add(loginButton);

        // ActionListener for loginButton
        loginButton.addActionListener(e -> {
            String username = usernameField.getText(); // Correctly captures username input
            String password = new String(passwordField.getPassword()); // Correctly captures password input

      //       Call to authenticate method, ensure this method returns a User object, not a boolean
            User cuser = UserAuthentication.authenticate(username, password);

            if (cuser != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                this.dispose(); // Correctly closes the current window

                // This part runs in the Event Dispatch Thread, ensuring thread safety for GUI updates
                EventQueue.invokeLater(() -> {
                    // Ensure the constructor for DashboardFrame is properly defined to accept a User object
                    DashboardFrame dashboard = new DashboardFrame(cuser);
                    dashboard.setVisible(true); // Correctly displays the dashboard
                });
            } else {
                JOptionPane.showMessageDialog(this, "Login Failed! Incorrect username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton switchToSignup = new JButton("Go to Signup");
        buttonPanel.add(switchToSignup);
        switchToSignup.setPreferredSize(new Dimension(120, 45));
        switchToSignup.addActionListener(e -> cardLayout.show(rightPanel, "Signup"));

        // Adding buttonPanel below the formPanel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));  // Updated grid layout for additional fields

        // Adding user information fields
        panel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField();
        panel.add(phoneField);

        // Adding fields for CSV file paths
        panel.add(new JLabel("Bank Transactions File:"));
        JButton bankFileChooserButton = createFileChooserButton();
        panel.add(bankFileChooserButton);

        panel.add(new JLabel("Assets and Liabilities File:"));
        JButton assetsFileChooserButton = createFileChooserButton();
        panel.add(assetsFileChooserButton);

        panel.add(new JLabel("Investments File:"));
        JButton investmentsFileChooserButton = createFileChooserButton();
        panel.add(investmentsFileChooserButton);

        // Signup button with action to register user
        JButton signupButton = new JButton("Sign Up");
        panel.add(signupButton);
        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            int phone = Integer.parseInt(phoneField.getText());
            String bankPath = bankFileChooserButton.getText();
            String assetsPath = assetsFileChooserButton.getText();
            String investmentsPath = investmentsFileChooserButton.getText();

            // Ensure the user registration method handles file paths
            if (UserAuthentication.registerUser(username, password, email, phone, bankPath, assetsPath, investmentsPath)) {
                JOptionPane.showMessageDialog(null, "Registration Successful!");
                // Assume User class constructor now handles file paths
                User newUser = new User(0, username, password, email, phone, bankPath, assetsPath, investmentsPath);
                cardLayout.show(rightPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(null, "Registration Failed!");
            }
        });

        // Button to switch back to login
        JButton switchToLogin = new JButton("Back to Login");
        panel.add(switchToLogin);
        switchToLogin.addActionListener(e -> cardLayout.show(rightPanel, "Login"));

        return panel;
    }

    private JButton createFileChooserButton() {
        JButton fileChooserButton = new JButton("Choose File");
        fileChooserButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileChooserButton.setText(selectedFile.getAbsolutePath());
            }
        });
        return fileChooserButton;
    }


}

