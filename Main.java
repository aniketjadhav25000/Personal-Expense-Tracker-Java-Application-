import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Enter your name:");
        if (name != null && !name.isEmpty()) {
            new ExpenseManagerGUI(name);
        }
    }
}
