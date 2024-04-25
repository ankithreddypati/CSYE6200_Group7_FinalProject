package Service;

import Model.InvestmentTransaction;
import Util.CSVReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InvestmentTransactionService {
    private List<InvestmentTransaction> transactions;

    public InvestmentTransactionService(String filePath) {
        this.transactions = CSVReader.readInvestmentTransaction(filePath);
    }

    public List<InvestmentTransaction> getTransactions() {
        return transactions;
    }

    public double calculateTotalInvestment() {
        return transactions.stream()
                .mapToDouble(InvestmentTransaction::getAmountInvested)
                .sum();
    }

    public List<InvestmentTransaction> getSortedTransactionsByDate() {
        return transactions.stream()
                .sorted(Comparator.comparing(InvestmentTransaction::getDate))
                .collect(Collectors.toList());
    }

    public List<InvestmentTransaction> getSortedTransactionsByAmountInvested() {
        return transactions.stream()
                .sorted(Comparator.comparingDouble(InvestmentTransaction::getAmountInvested))
                .collect(Collectors.toList());
    }

    public List<InvestmentTransaction> getSortedTransactionsByCurrentValue() {
        return transactions.stream()
                .sorted(Comparator.comparingDouble(InvestmentTransaction::getCurrentValue))
                .collect(Collectors.toList());
    }

    public List<InvestmentTransaction> getSortedTransactionsBySubcategory() {
        return transactions.stream()
                .sorted(Comparator.comparing(InvestmentTransaction::getSubcategory))
                .collect(Collectors.toList());
    }
}

