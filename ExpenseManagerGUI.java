import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ExpenseManagerGUI {
    private User user;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel summaryLabel;

    public ExpenseManagerGUI(String userName) {
        user = new User(userName);
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Personal Expense Tracker");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Date", "Type", "Category", "Amount"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addIncomeBtn = new JButton("Add Income");
        JButton addExpenseBtn = new JButton("Add Expense");
        JButton showSummaryBtn = new JButton("Show Summary");
        buttonPanel.add(addIncomeBtn);
        buttonPanel.add(addExpenseBtn);
        buttonPanel.add(showSummaryBtn);
        frame.add(buttonPanel, BorderLayout.NORTH);

        summaryLabel = new JLabel("Balance: 0.0 | Income: 0.0 | Expense: 0.0");
        summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(summaryLabel, BorderLayout.SOUTH);

        addIncomeBtn.addActionListener(e -> addTransaction("Income"));
        addExpenseBtn.addActionListener(e -> addTransaction("Expense"));
        showSummaryBtn.addActionListener(e -> updateSummary());

        frame.setVisible(true);
    }

    private void addTransaction(String type) {
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();
        Object[] message = {
                "Category:", categoryField,
                "Amount:", amountField
        };
        int option = JOptionPane.showConfirmDialog(frame, message, "Add " + type, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                Transaction t = new Transaction(type, category, amount, LocalDate.now());
                user.addTransaction(t);
                tableModel.addRow(new Object[]{t.getDate(), t.getType(), t.getCategory(), t.getAmount()});
                updateSummary();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount entered!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateSummary() {
        summaryLabel.setText("Balance: " + user.getBalance() + 
                             " | Income: " + user.getTotalIncome() + 
                             " | Expense: " + user.getTotalExpense());
    }
}
