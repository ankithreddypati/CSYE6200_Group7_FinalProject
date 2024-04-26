### README.md

```markdown
# Personal Finance Management Application

## Overview
This application helps users manage their personal finances by tracking bank transactions, assets, liabilities, and investments. It provides a comprehensive overview of the user's financial health through interactive charts and detailed tables.

## Features
- **Bank Transactions**: View and manage all bank transactions, categorized into credits and debits.
- **Assets and Liabilities**: Track total assets and liabilities to calculate net worth.
- **Investments**: Monitor investment portfolios including stocks, bonds, and cryptocurrencies.
- **Overview Panel**: Get a consolidated view of total bank balance, net worth, and investment values, represented graphically and numerically.

## Installation

### Prerequisites
- Java JDK 11 or higher
- MySQL 8.0 or higher

### Database Setup
1. Database and Tables are programmatically created from DatabaseConnectionClass .

### Application Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/finance-app.git
   cd finance-app
   ```
2. Open the project in your Java IDE (e.g., IntelliJ IDEA, Eclipse).
3. Update the MySQL connection settings in `src/Util/DatabaseConnection.java` to match your local setup:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/finance_db";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "your_mysql_password";
   ```

## Running the Application
1. In your IDE, build the project to resolve dependencies.
2. Run the `Driver.java` file to start the application.

## Contributions

### Team Members
- **AnkithReddyPati**:
    - Developed the Bank Transactions Panel.
    - Integrated MySQL database for transaction management.
- **BhuvanaRavikumar**:
    - Implemented the investments Panel .
    - Contributed to database schema design.
- **Sijoy**:
    - Created the Overview Panel .
    - Handled data fetching and processing for investment data.
- **XU**:
    - Assembled the Assets and Liabilities Panel.
    - Managed the application's main dashboard and navigation.

### Future Updates
Predictive Analytics:
Implement predictive analytics algorithms to forecast future financial trends based on historical data.
Provide users with insights into potential investment opportunities and risk assessment.
Send Report:
Sending the overview Report to the user mail 
Mobile App Version:
Develop a mobile application for iOS and Android platforms, offering users convenient access to their financial data on-the-go.
Ensure seamless synchronization with the desktop version for a unified user experience across devices.
Integration with Financial Institutions:
Establish connections with banks and financial institutions with Like Plaid  APIs to enable automatic retrieval of transaction data.
Streamline the process of updating user accounts with real-time transaction information, reducing manual data entry efforts

```

