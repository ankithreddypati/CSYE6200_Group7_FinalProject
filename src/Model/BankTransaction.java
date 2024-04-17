package Model;

import java.util.Date;
import java.util.Comparator;

public class BankTransaction extends Transaction {
    private double amount;
    private String type; // Credit or Debit
    private String category; // e.g., Salary, Groceries
    private String description;

    public BankTransaction(int id, Date date, double amount, String type, String category, String description) {
        super(id, date);
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
    }

    // Getters
    public double getAmount() {
        return this.amount;
    }

    public String getType() {
        return this.type;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void displayTransactionDetails() {
        System.out.println("Bank Transaction - " + type + ": " + amount + ", Category: " + category + ", Description: " + description);
    }


}
