/**
 * @author MinZhi
 * @version 1.0
 */
public class Tank {
    int x;
    int y;
    int direction;
    Boolean isLive = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
