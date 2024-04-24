package Model;
import java.util.Date;

public class InvestmentTransaction {
    private int id;
    private Date date;
    private double amountInvested;
    private double currentValue;
    private String subcategory;
    private String description;

    public InvestmentTransaction(int id, Date date, double amountInvested, double currentValue, String subcategory, String description) {
        this.id = id;
        this.date = date;
        this.amountInvested = amountInvested;
        this.currentValue = currentValue;
        this.subcategory = subcategory;
        this.description = description;
    }

    // Getters and Setters for all attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public double getCurrentValue() {
        return currentValue;
    }

   
}
