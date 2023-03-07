import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author MinZhi
 * @version 1.0
 */
public class Main extends JFrame {
    public static void main(String[] args) {
        Main main = new Main();
        new Thread(main.mp).start();
    }

    Mypanel mp = null;

    public Main() throws HeadlessException {
        mp = new Mypanel();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1400,800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
