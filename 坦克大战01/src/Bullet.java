/**
 * @author MinZhi
 * @version 1.0
 */
public class Bullet extends Thread{
    int x;
    int y;
    int direction;
    Boolean isLive  = true;

    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public static void shot(int x,int y,int direction){
        if (Mypanel.bullets.size() < 5){
            Bullet bullet = new Bullet(x, y, direction);
            bullet.start();
            Mypanel.bullets.add(bullet);
        }
    }
    @Override
    public void run() {
        while (isLive && x<1000 && x>10 && y<800 && y>10){
            switch (direction){
                case 0:
                    y-=5;
                    break;
                case 1:
                    y+=5;
                    break;
                case 2:
                    x+=5;
                    break;
                case 3:
                    x-=5;
                    break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    //删除子弹
        Mypanel.bullets.remove(this);

        //遍历坦克
        for (int i = 0; i < Mypanel.enemyTanks.size(); i++) {
            //遍历坦克里面的子弹集合
            for (int j = 0; j < Mypanel.enemyTanks.get(i).bullets.size(); j++) {
                Mypanel.enemyTanks.get(i).bullets.remove(this);
            }
        }
    }
}
