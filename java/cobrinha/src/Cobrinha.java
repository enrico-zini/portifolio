//https://stackoverflow.com/questions/8976874/show-two-dialogs-on-top-of-each-other-using-java-swing
//https://youtu.be/Y62MJny9LHg?si=wVoosmIfhDGl2Cba
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Cobrinha extends JPanel implements KeyListener {
    private class Quadrado {
        int x;
        int y;

        Quadrado(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    Random r;
    Timer t;
    Quadrado corpo;
    ArrayList<Quadrado> corpoCobra;
    Quadrado cabeca;
    Quadrado maca;
    int espacoX;
    int espacoY;
    boolean gameOver;
    Integer recorde = 0;
    int option;

    public Cobrinha() throws IOException {
        Object[] options = {"Death Walls", "Teleport Walls"};
        option = JOptionPane.showOptionDialog(null, "Choose limits", "Limits", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        this.setBackground(Color.getHSBColor(80 / 360.0f, 0.9f, 1.0f));
        this.setPreferredSize(new Dimension(400, 420));// para pack() funcionar
        this.addKeyListener(this);
        this.setFocusable(true);
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader("recorde.txt"))) {
                String aux = reader.readLine();
                if(aux!=null){
                    recorde = Integer.parseInt(aux);
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        r = new Random();
        corpoCobra = new ArrayList<>();
        cabeca = new Quadrado(r.nextInt(20), r.nextInt(19) + 1);
        maca = new Quadrado(r.nextInt(20), r.nextInt(19) + 1);
        t = new Timer(300, a -> move());//chama o metodo move a cada 300 milisegundos
        t.start();
    }

    public void zerar(){
        recorde = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("recorde.txt")));){
            writer.write(String.valueOf(recorde));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void paintComponent(Graphics g) {// pegando um Graphics g
        super.paintComponent(g);
        this.grafico(g);
    }

    public void comeu() {
        if (cabeca.x == maca.x && cabeca.y == maca.y) {
            corpoCobra.add(new Quadrado(maca.x, maca.y));
            if (corpoCobra.size() > recorde) {
                recorde++;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("recorde.txt")));){
                    writer.append(String.valueOf(recorde));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            spawnMaca();
        }
        for (int i = corpoCobra.size() - 1; i >= 0; i--) {
            Quadrado parte = corpoCobra.get(i);
            if (i == 0) {// a primeira parte segue a cabeca
                parte.x = cabeca.x;
                parte.y = cabeca.y;
            } else {// as de depois seguem as que de antes --> [2][1][0][cabeca]
                Quadrado antes = corpoCobra.get(i - 1);
                parte.x = antes.x;
                parte.y = antes.y;
            }
        }
    }

    public void cabecaCorpo() {//cabeca toca no corpo
        for (Quadrado parte : corpoCobra) {
            if (parte.x == cabeca.x && parte.y == cabeca.y) {
                gameOver();
            }
        }
    }

    public void gameOver(){
        t.stop();
        gameOver = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("recorde.txt")));){
            writer.write(String.valueOf(recorde));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void teleportWalls() {
        if (cabeca.x < 1 && espacoX == -1) {
            cabeca.x = 20;
        }
        if (cabeca.x > 18 && espacoX == 1) {
            cabeca.x = -1;
        }
        if (cabeca.y < 2 && espacoY == -1) {
            cabeca.y = 21;
        }
        if (cabeca.y > 19 && espacoY == 1) {
            cabeca.y = 0;
        }
    }
    public void deathWalls(){
        if (cabeca.x < 1 && espacoX == -1) {
            gameOver();
        }
        if (cabeca.x > 18 && espacoX == 1) {
            gameOver();
        }
        if (cabeca.y < 2 && espacoY == -1) {
            gameOver();
        }
        if (cabeca.y > 19 && espacoY == 1) {
            gameOver();
        }
    }

    public void grafico(Graphics g) {
        for (int i = 0; i <= 400 / 20; i++) {
            g.drawLine(i * 20, 20, i * 20, 420);
            g.drawLine(0, i * 20, 400, i * 20);
        }
        g.setColor(Color.red);
        g.fillRect(maca.x * 20, maca.y * 20, 20, 20);

        g.setColor(Color.black);
        g.fill3DRect(cabeca.x * 20, cabeca.y * 20, 20, 20, true);
        g.setColor(Color.getHSBColor(100 / 360.0f, 1.0f, 0.5f));
        for (int i = 0; i < corpoCobra.size(); i++) {
            Quadrado novo = corpoCobra.get(i);
            g.fill3DRect(novo.x * 20, novo.y * 20, 20, 20, true);
        }

        g.setColor(Color.black);
        g.drawString("Score: " + corpoCobra.size(), 0, 15);
        g.drawString("Record: " + recorde, 335, 15);

        if (gameOver) {
            g.drawString("GAME OVER", 160, 15);
        }
    }

    public void spawnMaca() {
        maca.x = r.nextInt(20);
        maca.y = r.nextInt(20) + 1;
    }

    public void move() {
        comeu();//verifica se cabeca esta em cima da maca
        if(option == 0){
            deathWalls();
        }else{
            teleportWalls();//verifica se cabeca passou do limite
        }
        //move a cabeca
        cabeca.x += espacoX;
        cabeca.y += espacoY;
        cabecaCorpo();//verifica se cabeca esta em cima de alguma parte do corpo
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && espacoX != -1) {// se pressionar a direita E nao estiver indo a esquerda
            espacoX = 1;
            espacoY = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && espacoX != 1) {
            espacoX = -1;
            espacoY = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && espacoY != 1) {
            espacoY = -1;
            espacoX = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && espacoY != -1) {
            espacoY = 1;
            espacoX = 0;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
