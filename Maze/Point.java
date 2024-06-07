import java.util.Objects;

public class Point {
    int x, y;

    Point(int a, int b) {
        this.x = a;
        this.y = b;
    }

    Direction getDirection(Point other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        if (dx == 1) return Direction.RIGHT;
        if (dx == -1) return Direction.LEFT;
        if (dy == 1) return Direction.DOWN;
        if (dy == -1) return Direction.UP;
        throw new IllegalArgumentException("Points are not adjacent");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
