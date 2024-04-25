package Util;

import Model.BankTransaction;
import Model.InvestmentTransaction;
import Model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    // Method tailored for reading transactions using TransactionFactory
    public static List<BankTransaction> readBankTransactions(String filePath) {
        List<BankTransaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(line);
                // Use TransactionFactory to create BankTransaction
                Transaction transaction = TransactionFactory.createTransaction("Bank", data);
                if (transaction instanceof BankTransaction) {
                    transactions.add((BankTransaction) transaction);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return transactions;
    }

    public static List<InvestmentTransaction> readInvestmentTransaction(String filePath) {
        List<InvestmentTransaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(line);
                // Use TransactionFactory to create BankTransaction
                Transaction transaction = TransactionFactory.createTransaction("Investment", data);
                if (transaction instanceof InvestmentTransaction) {
                    transactions.add((InvestmentTransaction) transaction);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return transactions;
    }
}
