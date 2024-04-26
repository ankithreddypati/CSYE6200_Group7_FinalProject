package Service;

import Model.AssetLiabilityTransaction;
import Util.CSVReader;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AssetLiabilityTransactionService {
    private List<AssetLiabilityTransaction> transactions;

    public AssetLiabilityTransactionService(String filePath) {
        this.transactions = CSVReader.readAssetLiabilityTransactions(filePath);
        System.out.println("Loaded transactions: " + transactions.size());
    }

    public List<AssetLiabilityTransaction> getTransactions() {
        return transactions;
    }

    /*public double calculateCurrentNetWorth() {
        return transactions.stream()
                .mapToDouble(t -> {
                    if (t.getCategory().equals("Asset")) {
                        return t.getValue();
                    } else if (t.getCategory().equals("Liability")) {
                        return -t.getValue();
                    } else {
                        return 0.0;
                    }
                    switch(t.getCategory()){
                        case "Asset":
                            return t.getValue();
                        case "Liability":
                            return -t.getValue();
                        default:
                            return 0.0;
                    }
                })
                .sum();
    }*/

    public double getTotalValueByCategory(String category) {
        return transactions.stream()
                .filter(t -> category.equals(t.getCategory()))
                .mapToDouble(AssetLiabilityTransaction::getValue)
                .sum();
    }

    public List<AssetLiabilityTransaction> getSortedTransactionsByDate() {
        return transactions.stream()
                .sorted(Comparator.comparing(AssetLiabilityTransaction::getDate))
                .collect(Collectors.toList());
    }

    public List<AssetLiabilityTransaction> getSortedTransactionsByValue() {
        return transactions.stream()
                .sorted(Comparator.comparingDouble(AssetLiabilityTransaction::getValue))
                .collect(Collectors.toList());
    }

    public List<AssetLiabilityTransaction> getSortedTransactionsByCategory() {
        return transactions.stream()
                .sorted(Comparator.comparing(AssetLiabilityTransaction::getCategory))
                .collect(Collectors.toList());
    }
}