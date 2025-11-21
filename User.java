import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<Transaction> transactions;

    public User(String name) {
        this.name = name;
        this.transactions = new ArrayList<>();
    }

    public String getName() { return name; }

    public void addTransaction(Transaction t) { transactions.add(t); }

    public ArrayList<Transaction> getTransactions() { return transactions; }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Income"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBalance() { return getTotalIncome() - getTotalExpense(); }
}
