import java.util.Vector;

/**
 * @author MinZhi
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable{
    public EnemyTank(int x, int y) {
        super(x, y);
    }
    Vector<Bullet> bullets = new Vector();

    public EnemyTank(int x, int y, int direction) {
        super(x, y, direction);
    }

    private void shot(){
        Bullet bullet = new Bullet(x, y, direction);
        new Thread(bullet).start();
        if (bullets.size() < 6){
            bullets.add(bullet);
        }
    }
    @Override
    public void run() {
        while (isLive){
            direction = (int)((3 + 1)*Math.random());
            for (int i = 0; i < 5; i++) {
                switch (direction){
                    case 0:
                        if (y > 20 && y < 800)
                            y-=5;
                        break;
                    case 1:
                        if (y > 20 && y < 800)
                            y+=5;
                        break;
                    case 2:
                        if (x > 20 && x < 1000)
                            x+=5;
                        break;
                    case 3:
                        if (x > 20 && x < 1000)
                            x-=5;
                        break;
                }
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //发射子弹
            this.shot();
        }
        Mypanel.enemyTanks.remove(this);
    }
}
