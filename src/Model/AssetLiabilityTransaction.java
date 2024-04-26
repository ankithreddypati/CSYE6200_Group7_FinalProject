package Model;

import java.util.Date;

public class AssetLiabilityTransaction extends Transaction {
    private double value;
    private String category; // Asset or Liability
    private String subcategory; // e.g., House, Mortgage
    private String description;

    public AssetLiabilityTransaction(int id, Date date, double value, String category, String subcategory, String description) {
        super(id, date);
        this.value = value;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
    }

    // Getters
    public double getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setValue(double value) {
        this.value = value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void displayTransactionDetails() {
        String transactionType = (value >= 0) ? "Asset" : "Liability";
        System.out.println(transactionType + " - " + subcategory + ": " + value + ", Description: " + description);
    }
}
