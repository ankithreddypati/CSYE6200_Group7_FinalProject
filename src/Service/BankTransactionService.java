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


        System.out.println(this.transactions);
    }

    public List<BankTransaction> getTransactions() {
        return transactions;
    }

    public double calculateCurrentBalance() {
        return transactions.stream()
                .mapToDouble(t -> "Credit".equals(t.getType()) ? t.getAmount() : -t.getAmount())
                .sum();
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
}
