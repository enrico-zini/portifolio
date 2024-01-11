import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;

public class ClickTracker extends JFrame {
    private Robot r;
    private JPanel mainPanel;
    private LinkedList<MyPoint> points = new LinkedList<>();
    private JFileChooser save = new JFileChooser();
    private PointsTM tm = new PointsTM(points);
    private JTable table = new JTable(tm);
    private JPanel tableP = new JPanel();

    public ClickTracker() {
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        mainPanel = new JPanel();
        this.add(mainPanel);
        this.setJMenuBar(bar());
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JMenuBar bar() {
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(s -> {
            try {
                saveAs();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        JMenuItem read = new JMenuItem("Read");
        read.addActionListener(r -> {
            try {
                this.setState(JFrame.ICONIFIED);// minimiza o programa
                read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        JMenuItem run = new JMenuItem("Run");
        run.addActionListener(r -> {
            try {
                this.setState(JFrame.ICONIFIED);// minimiza o programa
                run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        JMenuItem loop = new JMenuItem("Loop");
        loop.addActionListener(r -> {
            int n = Integer.parseInt(JOptionPane.showInputDialog("Number of loops"));
            try {
                this.setState(JFrame.ICONIFIED);// minimiza o programa
                loop(n);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        JMenuItem clear = new JMenuItem("Restart");
        clear.addActionListener(c -> restart());
        JMenuItem back = new JMenuItem("Back");
        back.addActionListener(c -> back());

        JMenu menu = new JMenu("File");
        menu.add(save);
        menu.add(read);
        menu.add(run);
        menu.add(loop);
        menu.add(clear);
        menu.add(back);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        return menuBar;
    }

    public void restart() {
        dispose();
        ClickTracker a = new ClickTracker();
        a.addKeyListener(a.kewAdapter());
        a.setVisible(true);
    }

    public void back() {
        points.removeLast();
        try {
            setContentPane(table(points));
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void read() throws FileNotFoundException, InterruptedException, AWTException {
        save.showOpenDialog(null);
        Scanner s = new Scanner(save.getSelectedFile());
        while (s.hasNextLine()) {
            String[] coordinates = s.nextLine().split(", ");
            sleep(1200);
            if (coordinates[0].equals("Type")) {
                typeText(r, coordinates[1]);
            } else if (coordinates[0].equals("Press")) {
                hold();
            } else if (coordinates[0].equals("Release")) {
                release();
            } else if (coordinates[0].equals("Click")) {
                click();
            } else {
                move(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            }
        }
    }

    public void run() throws InterruptedException, AWTException {
        for (MyPoint p : points) {
            sleep(1200);
            if (p.getX().equals("Press")) {
                hold();
            } else if (p.getX().equals("Release")) {
                release();
            } else if (p.getX().equals("Type")) {
                typeText(r, (String) p.getY());
            } else if (p.getX().equals("Click")) {
                click();
            } else {
                move((int) p.getX(), (int) p.getY());
            }
        }
    }

    public void hold() {
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void release() {
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void loop(int n) throws FileNotFoundException, InterruptedException, AWTException {
        save.showOpenDialog(null);
        Scanner s = new Scanner(save.getSelectedFile());
        LinkedList<MyPoint> list = new LinkedList<>();
        while (s.hasNextLine()) {
            String[] coorinates = s.nextLine().split(", ");
            String x = coorinates[0];
            String y = coorinates[1];
            list.add(new MyPoint(x, y));
        }
        for (int i = 0; i < n; i++) {// loop using queue
            for (MyPoint p : list) {
                sleep(1200);
                if (p.getX().equals("Press")) {
                    hold();
                } else if (p.getX().equals("Release")) {
                    release();
                } else if (p.getX().equals("Type")) {
                    typeText(r, (String) p.getY());
                } else if (p.getX().equals("Click")) {
                    click();
                } else {
                    move(Integer.parseInt((String) p.getX()), Integer.parseInt((String) p.getY()));
                }
            }
        }
    }

    public void saveAs() throws IOException {
        save.showOpenDialog(null);
        FileWriter fr = new FileWriter(save.getSelectedFile());

        for (MyPoint p : points) {
            fr.append(p.getX() + ", " + p.getY() + "\n");
        }
        fr.close();
    }

    public JPanel table(LinkedList<MyPoint> l) throws IOException {
        tableP.setLayout(new BoxLayout(tableP, BoxLayout.PAGE_AXIS));
        tableP.add(table);
        tableP.setVisible(true);
        return tableP;
    }

    public void sleep(int n) throws InterruptedException {
        Thread.sleep(n);
    }

    public void click() throws AWTException {
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void move(int x, int y) {
        r.mouseMove(x, y);
    }

    public KeyAdapter kewAdapter() {
        KeyAdapter k = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Point aux = MouseInfo.getPointerInfo().getLocation();
                    MyPoint mp = new MyPoint((int) aux.getX(), (int) aux.getY());
                    points.add(mp);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String s = JOptionPane.showInputDialog("Type text");
                    points.add(new MyPoint("Type", s));
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    points.add(new MyPoint("Press", "Press"));
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    points.add(new MyPoint("Release", "Release"));
                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    points.add(new MyPoint("Click", "Click"));
                }

                try {
                    setContentPane(table(points));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
        return k;
    }

    public static void typeText(Robot robot, String text) {
        // Loop through each character in the text
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Type the character using key presses
            if (Character.isLetterOrDigit(c)) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            } else if (c == ' ') {
                // Type space
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } else if (c == ',') {
                // Type comma
                robot.keyPress(KeyEvent.VK_COMMA);
                robot.keyRelease(KeyEvent.VK_COMMA);
            } else if (c == '!') {
                // Type exclamation mark
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            // Add a short delay between key presses (adjust as needed)
            robot.delay(30);
        }
    }

    public static void main(String[] args) throws IOException {
        ClickTracker a = new ClickTracker();
        a.addKeyListener(a.kewAdapter());
        a.setVisible(true);
    }
}
