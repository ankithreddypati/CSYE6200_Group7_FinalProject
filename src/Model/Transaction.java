package Model;

import java.util.Date;

public abstract class Transaction {
    private int id;
    private Date date;

    public Transaction(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public abstract void displayTransactionDetails();


}



