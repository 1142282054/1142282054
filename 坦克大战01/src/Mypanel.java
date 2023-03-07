import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author MinZhi
 * @version 1.0
 */
public class Mypanel extends JPanel implements KeyListener,Runnable{

    public Mypanel() {
        //
        boolean b = checkNewGame();
        if (!b) {
            Recorder recorder = new Recorder(enemyTanks);
            recorder.recoverRecord();
        }
        //初始化我方坦克
        hero = new Hero(200,200,2);
        //创建坦克对象
        if (enemyTanks.size() == 0) {
            for (int i = 1; i < 5; i++) {
                EnemyTank enemyTank = new EnemyTank(100 * i, 100);
                new Thread(enemyTank).start();
                enemyTanks.add(enemyTank);
            }
        }
    }

    Hero hero;
    //子弹集合
    public static Vector<Bullet> bullets = new Vector();
    //敌方坦克集合
    public static Vector<EnemyTank> enemyTanks = new Vector();

    //画图
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //画背景板
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1000,800);
        //
        showInfo(g);
        //画出我方坦克
        drawTank(g,hero,Color.red);
        //画出敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            drawTank(g,enemyTanks.get(i),Color.gray);
        }

        //画出子弹
        for (int i = 0; i < bullets.size(); i++) {
            g.fillOval(bullets.get(i).x,bullets.get(i).y,5,5);
        }

        //遍历坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //遍历坦克里面的子弹集合
            for (int j = 0; j < enemyTanks.get(i).bullets.size(); j++) {
                g.fillOval(enemyTanks.get(i).bullets.get(j).x,
                        enemyTanks.get(i).bullets.get(j).y,5,5);
            }
        }
    }

    public boolean checkNewGame(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Y开启新游戏，N继续游戏");
        String next = scanner.next();
        if (next.equalsIgnoreCase("Y")){
            return true;
        }else if (next.equalsIgnoreCase("N")){
            return false;
        }else {
            System.out.println("请重新输入");
            return checkNewGame();
        }
    }
    private void drawTank(Graphics g,Tank tank,Color color){
        g.setColor(color);
        switch (tank.direction){
            case 0://up
                g.fillRect(tank.x-20,tank.y-20,10,40);
                g.fillRect(tank.x+10,tank.y-20,10,40);
                g.fill3DRect(tank.x-10,tank.y-10,20,20,true);
                g.fillOval(tank.x-10,tank.y-10,20,20);
                g.fillRect(tank.x-1,tank.y-25,2,15);
                break;
            case 1://down
                g.fillRect(tank.x-20,tank.y-20,10,40);
                g.fillRect(tank.x+10,tank.y-20,10,40);
                g.fill3DRect(tank.x-10,tank.y-10,20,20,true);
                g.fillOval(tank.x-10,tank.y-10,20,20);
                g.fillRect(tank.x-1,tank.y + 10,2,15);
                break;
            case 2://right
                g.fillRect(tank.x-20,tank.y-20,40,10);
                g.fillRect(tank.x-20,tank.y+10,40,10);
                g.fill3DRect(tank.x-10,tank.y-10,20,20,true);
                g.fillOval(tank.x-10,tank.y-10,20,20);
                g.fillRect(tank.x + 10,tank.y-1,15,2);
                break;
            case 3://left
                g.fillRect(tank.x-20,tank.y-20,40,10);
                g.fillRect(tank.x-20,tank.y+10,40,10);
                g.fill3DRect(tank.x-10,tank.y-10,20,20,true);
                g.fillOval(tank.x-10,tank.y-10,20,20);
                g.fillRect(tank.x -25,tank.y -1,15,2);
                break;
        }
    }

    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("击毁敌方坦克数",1100,50);
        drawTank(g,new EnemyTank(1100,100),Color.gray);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.count + "",1150,110);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                hero.moveUp();
                break;
            case KeyEvent.VK_S:
                hero.moveDown();
                break;
            case KeyEvent.VK_D:
                hero.moveRight();
                break;
            case KeyEvent.VK_A:
                hero.moveLeft();
                break;
            case KeyEvent.VK_J:
                Bullet.shot(hero.x,hero.y,hero.direction);
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void isHit(Tank tank,Vector<Bullet> bullets){
        Bullet bullet;
        for (int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if (bullet.x < tank.x+20 && bullet.x> tank.x-20
                    && bullet.y < tank.y+20 && bullet.y > tank.y-20){
                tank.isLive = false;
                bullet.isLive = false;
                if (tank instanceof EnemyTank){
                    Recorder.count++;
                }
            }
        }
    }

    @Override
    public void run() {
        while (true){
            this.repaint();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            //判断是否击中
            for (int i = 0; i <enemyTanks.size() ; i++) {
                //敌方击中我方
                this.isHit(hero, enemyTanks.get(i).bullets);
                //我方击中敌方
                this.isHit(enemyTanks.get(i),bullets);
            }
            //判断游戏结束
            if(enemyTanks.size() == 0 && Recorder.count == 4){
                System.out.println("游戏胜利");
            }
        }
    }
}
