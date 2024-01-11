import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class App {
    public JFrame frame = new JFrame("Cobrinha");
    Cobrinha c;

    public void jogo() {
        try {
            c = new Cobrinha();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setContentPane(c);
        c.requestFocusInWindow();
        frame.pack();
    }

    public void frame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(a -> {
            c.t.stop();
            jogo();
        });
        JMenuItem zerarRecorde = new JMenuItem("Zerar recorde");
        zerarRecorde.addActionListener(z -> {
            c.zerar();
            c.t.stop();
            jogo();
        });
        JMenu menu = new JMenu("Menu");
        menu.add(restart);
        menu.add(zerarRecorde);
        JMenuBar barra = new JMenuBar();
        barra.add(menu);
        frame.setJMenuBar(barra);
        jogo();
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws Exception {
        App a = new App();
        a.frame();
    }
}
