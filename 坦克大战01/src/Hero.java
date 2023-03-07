/**
 * @author MinZhi
 * @version 1.0
 */
public class Hero extends Tank{

    public Hero(int x, int y) {
        super(x, y);
    }

    public Hero(int x, int y, int direction) {
        super(x, y, direction);
    }

    public void moveUp(){
        if (this.y < 800 && this.y >20) {
            this.y -= 5;
            this.direction = 0;
        }
    }
    public void moveDown(){
        if (this.y < 800 && this.y >20) {
            this.y += 5;
            this.direction = 1;
        }
    }
    public void moveRight(){
        if (this.x < 1000 && this.x > 20) {
            this.x += 5;
            this.direction = 2;
        }
    }
    public void moveLeft(){
        if (this.x < 1000 && this.x > 20) {
            this.x -= 5;
            this.direction = 3;
        }
    }
}
