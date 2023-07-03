import javax.swing.*;

public class Main{
    /* New Runnable for CLIMenu on other thread (method reference) */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CLIMenu::new);
    }
}
