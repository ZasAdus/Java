import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Maze");
        Maze maze = new Maze(20, 20);
        frame.setLayout(new BorderLayout());

        frame.add(maze, BorderLayout.CENTER);

        JPanel buttonPanel = getjPanel(maze);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel getjPanel(Maze maze) {
        JPanel buttonPanel = new JPanel();
        JButton newMazeButton = new JButton("New Maze");
        newMazeButton.addActionListener(_ -> {
            maze.generateMaze();
            maze.repaint();
            maze.requestFocusInWindow();
        });

        JButton shortestPathButton = new JButton("Show Shortest Path");
        shortestPathButton.addActionListener(_ -> maze.setDrawShortestPath(!maze.drawShortestPath));
        buttonPanel.add(newMazeButton);
        buttonPanel.add(shortestPathButton);
        return buttonPanel;
    }
}