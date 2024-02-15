public class TicTacToeRunner implements Runnable {
    private final int length;
    private final int width;
    private final int tokens;

    public TicTacToeRunner(int length, int width, int tokens) {
        this.length = length;
        this.width = width;
        this.tokens = tokens;
    }

    @Override
    public void run() {
        new TicTacToe(length, width, tokens);
    }
}