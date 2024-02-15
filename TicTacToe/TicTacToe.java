import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {
    private final int length;
    private final int width;
    private final int amountOfWinningSymbols;
    private boolean firstPlayerTurn = true;
    private static String firstPlayerName = "Player1";
    private static String secondPlayerName = "Player2";
    private JButton[][] buttons;
    private double amountOfWinsPlayerFirst = 0.0;
    private double amountOfWinsPlayerSecond = 0.0;
    private int amountOfElements = 0;
    private JLabel amountOfWinsPlayerFirstLabel;
    private JLabel amountOfWinsPlayerSecondLabel;
    private JLabel yourTurnFirstPlayer;
    private JLabel yourTurnSecondPlayer;

    private static void Parameters(){
        var lengthInput = JOptionPane.showInputDialog(null, "Length of the board?", "Question 1", JOptionPane.QUESTION_MESSAGE);
        int length = ConvertToInt(lengthInput);
        var widthInput = JOptionPane.showInputDialog(null, "Width of the board?", "Question 2", JOptionPane.QUESTION_MESSAGE);
        int width = ConvertToInt(widthInput);
        var tokensInput = JOptionPane.showInputDialog(null, "Number of winning tokens?", "Question 3", JOptionPane.QUESTION_MESSAGE);
        int tokens = ConvertToInt(tokensInput);
        if(tokens < 3 || tokens > length || tokens > width){
            JOptionPane.showMessageDialog(null, "Valid input winning tokens must be at least equal 3, length and width must be bigger than number of winning tokens!", "Game Information", JOptionPane.INFORMATION_MESSAGE);
            System.exit(-1);
        }else {
            JOptionPane.showMessageDialog(null, "Parameters:\n1. Length: " + length + "\n2. Width: " + width + "\n3. Winning tokens: " + tokens, "Game Information", JOptionPane.INFORMATION_MESSAGE);
        }
        firstPlayerName = JOptionPane.showInputDialog(null, "First player name: ", "Question 4", JOptionPane.QUESTION_MESSAGE);
        secondPlayerName = JOptionPane.showInputDialog(null, "Second player name: ", "Question 5", JOptionPane.QUESTION_MESSAGE);
        Runnable runnable = new TicTacToeRunner(length, width, tokens);
        SwingUtilities.invokeLater(runnable);

    }
    private static int ConvertToInt(String input) {
        int result = 0;
        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input, it must be Integer", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return result;
    }
    TicTacToe(int a, int b, int c) {
        this.length = a;
        this.width = b;
        this.amountOfWinningSymbols = c;
        initialize();
    }

    private void initialize() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.add(Box.createHorizontalGlue());
        JLabel game = new JLabel("Tic Tac Toe");
        game.setFont(new Font("algerian", Font.BOLD, 60));
        northPanel.add(game);
        northPanel.add(Box.createHorizontalGlue());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.add(Box.createHorizontalGlue());
        JButton boardReset = new JButton("Board Reset");
        boardReset.addActionListener(e -> resetBoardDuringGame());
        southPanel.add(boardReset);
        JButton winsReset = new JButton("Score Reset");
        winsReset.addActionListener(e -> resetScore());
        southPanel.add(winsReset);
        southPanel.add(Box.createHorizontalGlue());

        JPanel centerPanel = new JPanel(new GridLayout(length, width));
        buttons = new JButton[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setText("");
                buttons[i][j].setPreferredSize(new Dimension(100, 100));
                buttons[i][j].addActionListener(this);
                centerPanel.add(buttons[i][j]);
            }
        }

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.add(Box.createVerticalGlue());
        JLabel firstPlayer = new JLabel(firstPlayerName + "('X') ");
        firstPlayer.setFont(new Font("Arial", Font.BOLD, 40));
        westPanel.add(firstPlayer);
        amountOfWinsPlayerFirstLabel = new JLabel("Score: " + amountOfWinsPlayerFirst);
        amountOfWinsPlayerFirstLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        westPanel.add(amountOfWinsPlayerFirstLabel);
        yourTurnFirstPlayer = new JLabel("Your Turn!");
        yourTurnFirstPlayer.setFont(new Font("Arial",Font.ITALIC, 20));
        westPanel.add(yourTurnFirstPlayer);
        westPanel.add(Box.createVerticalGlue());

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(Box.createVerticalGlue());
        JLabel secondPlayer = new JLabel(secondPlayerName + "('O') ");
        secondPlayer.setFont(new Font("Arial", Font.BOLD, 40));
        eastPanel.add(secondPlayer);
        amountOfWinsPlayerSecondLabel = new JLabel("Score: " + amountOfWinsPlayerSecond);
        amountOfWinsPlayerSecondLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        eastPanel.add(amountOfWinsPlayerSecondLabel);
        yourTurnSecondPlayer = new JLabel("Wait!");
        yourTurnSecondPlayer.setFont(new Font("Arial",Font.ITALIC, 20));
        eastPanel.add(yourTurnSecondPlayer);
        eastPanel.add(Box.createVerticalGlue());

        add(northPanel, BorderLayout.NORTH);
        add(westPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resetScore() {
        amountOfWinsPlayerSecond = 0;
        amountOfWinsPlayerFirst = 0;
        amountOfWinsPlayerSecondLabel.setText("Score: " + amountOfWinsPlayerSecond);
        amountOfWinsPlayerFirstLabel.setText("Score: " +amountOfWinsPlayerFirst);
    }

    private void resetBoardDuringGame() {
        amountOfElements = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                buttons[i][j].setText("");
            }
        }
    }
    private void resetBoardAfterGame() {
        amountOfElements = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                buttons[i][j].setText("");
            }
        }
        //Kto przegrał ten będzie zaczynał
        firstPlayerTurn = !firstPlayerTurn;
        if (firstPlayerTurn) {
            yourTurnFirstPlayer.setText("Your Turn!");
            yourTurnSecondPlayer.setText("Wait!");
        }else{
            yourTurnFirstPlayer.setText("Wait!");
            yourTurnSecondPlayer.setText("Your Turn!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        if(!button.getText().isEmpty()){
            return;
        }
        button.setFont(new Font("Arial", Font.BOLD, 40));
        amountOfElements++;
        if(firstPlayerTurn){
           button.setText("X");
           if(checkingForWin()){
               amountOfWinsPlayerFirst++;
               amountOfWinsPlayerFirstLabel.setText("Score: " + amountOfWinsPlayerFirst);
               showWinnerMessage(firstPlayerName);
           }else {
               firstPlayerTurn = false;
               yourTurnFirstPlayer.setText("Wait!");
               yourTurnSecondPlayer.setText("Your Turn!");
           }
        }else{
           button.setText("O");
           if(checkingForWin()){
               amountOfWinsPlayerSecond++;
               amountOfWinsPlayerSecondLabel.setText("Score: " + amountOfWinsPlayerSecond);
               showWinnerMessage(secondPlayerName);
           }else {
               firstPlayerTurn = true;
               yourTurnSecondPlayer.setText("Wait!");
               yourTurnFirstPlayer.setText("Your Turn!");
           }
        }
        if(amountOfElements == length * width){
            amountOfWinsPlayerFirst += 0.5;
            amountOfWinsPlayerSecond += 0.5;
            amountOfWinsPlayerSecondLabel.setText("Score: " + amountOfWinsPlayerSecond);
            amountOfWinsPlayerFirstLabel.setText("Score: " + amountOfWinsPlayerFirst);
            showDrawMessage();
        }
    }


    private boolean checkingForWin() {
        String znak;
        if(firstPlayerTurn){
            znak = "X";
        }else{
            znak = "O";
        }

        return checkingRows(znak) || checkingColumns(znak) || checkingDiagonal(znak);
    }
    private boolean checkingRows(String znak) {
        for (int i = 0; i < length; ++i) {
            int j = 0;
            int count = 0;
            while (j < width && count < amountOfWinningSymbols) {
                if (buttons[i][j].getText().equals(znak)) {
                    count++;
                    if (count == amountOfWinningSymbols) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                j++;
            }
        }
        return false;
    }

    private boolean checkingColumns(String znak) {
        for (int i = 0; i < width; ++i) {
            int j = 0;
            int count = 0;
            while (j < length && count < amountOfWinningSymbols) {
                if (buttons[j][i].getText().equals(znak)) {
                    count++;
                    if (count == amountOfWinningSymbols) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                j++;
            }
        }
        return false;
    }
    private boolean checkingDiagonal(String znak) {
        for (int i = 0; i <= length - amountOfWinningSymbols; i++) {
            for (int j = 0; j <= width - amountOfWinningSymbols; j++) {
                int count = 0;
                int row = i;
                int col = j;
                while (count < amountOfWinningSymbols) {
                    if (buttons[row][col].getText().equals(znak)) {
                        count++;
                        if (count == amountOfWinningSymbols) {
                            return true;
                        }
                    } else {
                        break;
                    }
                    row++;
                    col++;
                }
            }
        }
        for (int i = amountOfWinningSymbols - 1; i < length; i++) {
            for (int j = 0; j <= width - amountOfWinningSymbols; j++) {
                int count = 0;
                int row = i;
                int col = j;
                while (count < amountOfWinningSymbols) {
                    if (buttons[row][col].getText().equals(znak)) {
                        count++;
                        if (count == amountOfWinningSymbols) {
                            return true;
                        }
                    } else {
                        break;
                    }
                    row--;
                    col++;
                }
            }
        }
        return false;
    }
    private void showWinnerMessage(String winner) {
        Object[] options = {"New Game", "Close"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Congratulations! Winner is " + winner + "!",
                "Game ended",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) {
            resetBoardAfterGame();
        } else {
            System.exit(0);
        }
    }
    private void showDrawMessage() {
        Object[] options = {"New Game", "Close"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Draw!",
                "Game ended",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) {
            resetBoardAfterGame();
        } else {
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        Parameters();
    }
}