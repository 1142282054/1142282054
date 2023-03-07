import java.io.*;
import java.util.Vector;

/**
 * @author MinZhi
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class Recorder {
    public static int count = 0;
    private static String adress = "src\\mydate.txt";
    //把Mypanel的enemytanks关联起来，同一个对象
    private static Vector<EnemyTank> enemyTanks= null;

    public Recorder(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public void recoverRecord() {
        BufferedReader bufferedReader = null;
        File file = new File(adress);
        if (!file.isFile()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(adress));
            String buf = null;
            //恢复击败数据
            if ((buf = bufferedReader.readLine()) != null){
                count = Integer.parseInt(buf);
            }
            //恢复坦克数据
            while ((buf = bufferedReader.readLine()) != null){
                String[] s = buf.split(" ");
                EnemyTank enemyTank = new EnemyTank(Integer.parseInt(s[0]),
                        Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                new Thread(enemyTank).start();
                enemyTanks.add(enemyTank);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void keepRecord() throws IOException {
        BufferedWriter bufferedWriter = null;
        File file = new File(adress);
        if (!file.isFile()){
            file.createNewFile();
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(count + "");
            bufferedWriter.newLine();
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    bufferedWriter.write(enemyTank.x + " " + enemyTank.y + " " + enemyTank.direction);
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bufferedWriter.close();
        }
    }
}
