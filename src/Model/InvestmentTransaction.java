package Model;
import java.util.Date;

public class InvestmentTransaction extends Transaction {
    private double amountInvested;
    private double currentValue;
    private String subcategory; // e.g., Stocks, Crypto
    private String description;

    public InvestmentTransaction(int id, Date date, double amountInvested, double currentValue, String subcategory, String description) {
        super(id, date);
        this.amountInvested = amountInvested;
        this.currentValue = currentValue;
        this.subcategory = subcategory;
        this.description = description;
    }

    // Getters and setters for amountInvested, currentValue, subcategory, description

    @Override
    public void displayTransactionDetails() {
        System.out.println("Investment in " + subcategory + " - Invested: " + amountInvested + ", Current Value: " + currentValue + ", Description: " + description);
    }
}

