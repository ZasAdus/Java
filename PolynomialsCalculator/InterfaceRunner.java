import javax.swing.SwingUtilities;

public class InterfaceRunner implements Runnable {
    public void run() {
        new Interface();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new InterfaceRunner());
    }
}