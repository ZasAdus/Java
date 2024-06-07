import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

class Maze extends JPanel implements KeyListener {
    private final int width;
    private final int height;
    private final boolean[][][] walls;
    private final boolean[][] visited;
    private final Random random;
    private Point start;
    private Point goal;
    private final Player player;
    boolean drawShortestPath = false;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        walls = new boolean[width][height][4];
        visited = new boolean[width][height];
        random = new Random();
        player = new Player(0, 0);
        generateMaze();

        setFocusable(true);
        addKeyListener(this);
    }

    void generateMaze() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Arrays.fill(walls[x][y], true);
                visited[x][y] = false;
            }
        }

        player.position.x = 0;
        player.position.y = 0;
        start = new Point(0, 0);
        goal = new Point(width - 1, height - 1);

        Stack<Point> stack = new Stack<>();
        stack.push(start);
        visited[0][0] = true;

        while (!stack.isEmpty()) {
            Point current = stack.peek();
            List<Point> neighbors = getUnvisitedNeighbours(current);

            if (neighbors.isEmpty()) {
                stack.pop();
            } else {
                Point next = neighbors.get(random.nextInt(neighbors.size()));
                removeWall(current, next);
                visited[next.x][next.y] = true;
                stack.push(next);
            }
        }
        List<Point> shortestPath = findShortestPath();
        removeAdditionalWalls(shortestPath);
    }

    private List<Point> getUnvisitedNeighbours(Point p) {
        List<Point> neighbors = new ArrayList<>();
        if (p.x > 0 && !visited[p.x - 1][p.y]) neighbors.add(new Point(p.x - 1, p.y));
        if (p.y > 0 && !visited[p.x][p.y - 1]) neighbors.add(new Point(p.x, p.y - 1));
        if (p.x < width - 1 && !visited[p.x + 1][p.y]) neighbors.add(new Point(p.x + 1, p.y));
        if (p.y < height - 1 && !visited[p.x][p.y + 1]) neighbors.add(new Point(p.x, p.y + 1));
        return neighbors;
    }

    private List<Point> getNeighbours(Point p) {
        List<Point> neighbors = new ArrayList<>();
        if (p.x > 0) neighbors.add(new Point(p.x - 1, p.y));
        if (p.y > 0) neighbors.add(new Point(p.x, p.y - 1));
        if (p.x < width - 1) neighbors.add(new Point(p.x + 1, p.y));
        if (p.y < height - 1) neighbors.add(new Point(p.x, p.y + 1));
        return neighbors;
    }

    private void removeAdditionalWalls(List<Point> path) {
        Set<Point> pathSet = new HashSet<>(path);
        for (Point p : path) {
            List<Point> neighbours = getNeighbours(p);
            for (Point neighbour : neighbours) {
                if (!pathSet.contains(neighbour) && random.nextDouble() < 0.2) {
                    removeWall(p, neighbour);
                }
            }

        }
    }

    private void removeWall(Point current, Point next) {
        Direction direction = current.getDirection(next);
        switch (direction) {
            case RIGHT:
                walls[current.x][current.y][1] = false;
                walls[next.x][next.y][3] = false;
                break;
            case LEFT:
                walls[current.x][current.y][3] = false;
                walls[next.x][next.y][1] = false;
                break;
            case DOWN:
                walls[current.x][current.y][2] = false;
                walls[next.x][next.y][0] = false;
                break;
            case UP:
                walls[current.x][current.y][0] = false;
                walls[next.x][next.y][2] = false;
                break;
        }
    }

    private List<Point> findShortestPath() {
        boolean[][] visited = new boolean[width][height];
        Point[][] parent = new Point[width][height];
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        visited[start.x][start.y] = true;
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(goal)) {
                List<Point> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent[current.x][current.y];
                }
                Collections.reverse(path);
                return path;
            }
            for (Point neighbor : getNeighbours(current)) {
                if (!visited[neighbor.x][neighbor.y] && !hasWall(current, neighbor)) {
                    queue.add(neighbor);
                    visited[neighbor.x][neighbor.y] = true;
                    parent[neighbor.x][neighbor.y] = current;
                }
            }
        }
        return new ArrayList<>();
    }

    private boolean hasWall(Point p1, Point p2) {
        Direction direction = p1.getDirection(p2);
        return switch (direction) {
            case RIGHT -> walls[p1.x][p1.y][1];
            case LEFT -> walls[p1.x][p1.y][3];
            case DOWN -> walls[p1.x][p1.y][2];
            case UP -> walls[p1.x][p1.y][0];
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 20;

        Insets insets = getInsets();
        int offsetX = insets.left;
        int offsetY = insets.top;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (walls[x][y][0]) g.drawLine(offsetX + x * cellSize, offsetY + y * cellSize, offsetX + (x + 1) * cellSize, offsetY + y * cellSize);
                if (walls[x][y][1]) g.drawLine(offsetX + (x + 1) * cellSize, offsetY + y * cellSize, offsetX + (x + 1) * cellSize, offsetY + (y + 1) * cellSize);
                if (walls[x][y][2]) g.drawLine(offsetX + x * cellSize, offsetY + (y + 1) * cellSize, offsetX + (x + 1) * cellSize, offsetY + (y + 1) * cellSize);
                if (walls[x][y][3]) g.drawLine(offsetX + x * cellSize, offsetY + y * cellSize, offsetX + x * cellSize, offsetY + (y + 1) * cellSize);
            }
        }

        g.setColor(Color.GREEN);
        g.fillRect(offsetX + start.x * cellSize + cellSize / 4, offsetY + start.y * cellSize + cellSize / 4, cellSize / 2, cellSize / 2);
        g.setColor(Color.RED);
        g.fillRect(offsetX + goal.x * cellSize + cellSize / 4, offsetY + goal.y * cellSize + cellSize / 4, cellSize / 2, cellSize / 2);

        g.setColor(Color.BLUE);
        g.fillOval(offsetX + player.position.x * cellSize + cellSize / 4, offsetY + player.position.y * cellSize + cellSize / 4, cellSize / 2, cellSize / 2);

        if (drawShortestPath) {
            drawShortestPath(g, cellSize, offsetX, offsetY);
        }
    }
    @Override
    public Insets getInsets() {
        return new Insets(20, 20, 20, 20);
    }

    private void drawShortestPath(Graphics g, int cellSize, int offsetX, int offsetY) {
        List<Point> shortestPath = findShortestPath();
        g.setColor(Color.MAGENTA);
        Point previous = null;
        for (Point p : shortestPath) {
            if (previous != null) {
                g.drawLine(offsetX + previous.x * cellSize + cellSize / 2, offsetY + previous.y * cellSize + cellSize / 2,
                        offsetX + p.x * cellSize + cellSize / 2, offsetY + p.y * cellSize + cellSize / 2);
            }
            previous = p;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(width * 22, height * 22);
    }

    public void setDrawShortestPath(boolean drawShortestPath) {
        this.drawShortestPath = drawShortestPath;
        repaint();
        requestFocusInWindow();
    }

    public void checkVictory() {
        if (goal.equals(player.position)) {
            JOptionPane.showMessageDialog(this, "You won!");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int x = player.position.x;
        int y = player.position.y;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (y > 0 && !walls[x][y][0]) player.move(KeyEvent.VK_UP);
                break;
            case KeyEvent.VK_DOWN:
                if (y < height - 1 && !walls[x][y][2]) player.move(KeyEvent.VK_DOWN);
                break;
            case KeyEvent.VK_LEFT:
                if (x > 0 && !walls[x][y][3]) player.move(KeyEvent.VK_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                if (x < width - 1 && !walls[x][y][1]) player.move(KeyEvent.VK_RIGHT);
                break;
        }
        repaint();
        checkVictory();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

