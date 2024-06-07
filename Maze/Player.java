import java.awt.event.KeyEvent;

public class Player {
    Point position;
    Player(int _x, int _y) {
        this.position = new Point(_x, _y);
    }

    void move(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                position.y--;
                break;
            case KeyEvent.VK_DOWN:
                position.y++;
                break;
            case KeyEvent.VK_LEFT:
                position.x--;
                break;
            case KeyEvent.VK_RIGHT:
                position.x++;
                break;
        }
    }
}
