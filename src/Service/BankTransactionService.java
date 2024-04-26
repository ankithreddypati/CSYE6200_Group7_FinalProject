package Service;

import Model.BankTransaction;
import Util.CSVReader;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BankTransactionService {
    private List<BankTransaction> transactions;

    public BankTransactionService(String filePath) {
        this.transactions = CSVReader.readBankTransactions(filePath);
    }

    public List<BankTransaction> getTransactions() {
        return transactions;
    }

    public double calculateCurrentBalance() {
        double totalCredits = transactions.stream()
                .filter(t -> "Credit".equals(t.getType()))
                .mapToDouble(BankTransaction::getAmount)
                .sum();

        double totalDebits = transactions.stream()
                .filter(t -> "Debit".equals(t.getType()))
                .mapToDouble(BankTransaction::getAmount)
                .sum();

        return totalCredits - totalDebits;
    }

    public List<BankTransaction> getSortedTransactionsByDate() {
        return transactions.stream()
                .sorted(Comparator.comparing(BankTransaction::getDate))
                .collect(Collectors.toList());
    }

    public List<BankTransaction> getSortedTransactionsByAmount() {
        return transactions.stream()
                .sorted(Comparator.comparingDouble(BankTransaction::getAmount))
                .collect(Collectors.toList());
    }

    public List<BankTransaction> getSortedTransactionsByCategory() {
        return transactions.stream()
                .sorted(Comparator.comparing(BankTransaction::getCategory))
                .collect(Collectors.toList());
    }

    public List<BankTransaction> getSortedTransactionsByType() {
        return transactions.stream()
                .sorted(Comparator.comparing(BankTransaction::getType))
                .collect(Collectors.toList());
    }
}