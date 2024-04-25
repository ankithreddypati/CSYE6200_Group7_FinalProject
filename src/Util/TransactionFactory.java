package Util;

import Model.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionFactory {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Transaction createTransaction(String csvType, String[] data) {
        try {
            int id = safeParseInt(data[0]);
            Date date = parseDate(data[1]);
            double amount;
            String description;
            String category;
            String subcategory;

            switch (csvType) {
                case "Bank":
                    amount = safeParseDouble(data[2]);
                    String type = data[3];
                    category = data[4];
                    description = data[5];
                    return new BankTransaction(id, date, amount, type, category, description);
                case "AssetLiability":
                    amount = safeParseDouble(data[2]);
                    category = data[3];
                    subcategory = data[4];
                    description = data[5];
                    return new AssetLiabilityTransaction(id, date, amount, category, subcategory, description);
                case "Investment":
                    date = parseDate(data[3]);
                    amount = safeParseDouble(data[4]);
                    double currentValue = safeParseDouble(data[5]);
                    subcategory = data[2];
                    description = data[6];
                    return new InvestmentTransaction(id, date, amount, currentValue, subcategory, description);
                default:
                    throw new IllegalArgumentException("Unknown CSV type: " + csvType);
            }
        } catch (Exception e) {
            System.err.println("Error creating transaction: " + e.getMessage());
            return null;  // Return null or throw an exception as appropriate for your application
        }
    }

    private static Date parseDate(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            System.err.println("Date parsing error: " + e.getMessage());
            return new Date();  // default to current date if parsing fails
        }
    }

    private static int safeParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.err.println("Integer parsing error: " + e.getMessage());
            return 0;  // default value or consider throwing
        }
    }

    private static double safeParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            System.err.println("Double parsing error: " + e.getMessage());
            return 0.0;  // default value or consider throwing
        }
    }
}
