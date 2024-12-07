import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends Canvas implements Runnable, KeyListener {
    public static int largura = 450;
    public static int altura = 400;
    public static Jogador jogador;
    public static Inimigo inimigo;
    public static Bola bola;

    public Pong() {
        this.setPreferredSize(new Dimension(largura, altura));
        this.addKeyListener(this);
    }

    public static void main(String[] args) throws Exception {
        Pong pong = new Pong();
        jogador = new Jogador(15, 150);
        inimigo = new Inimigo(425, 150);
        bola = new Bola(255, 200);

        JFrame jframe = new JFrame("Pong Game");
        jframe.setVisible(true);
        jframe.add(pong);
        pong.requestFocus();
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new Thread(pong).start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                desenhar();
                atualizar();
                Thread.sleep(1000 / 60); // 60 FPS
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void desenhar() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics grafico = bs.getDrawGraphics();
        grafico.setColor(new Color(0, 0, 0));
        grafico.fillRect(0, 0, largura, altura);

        // Desenhar bordas
        grafico.setColor(Color.WHITE);
        grafico.drawRect(0, 0, largura - 1, altura - 1);

        // Desenhar objetos
        jogador.desenhar(grafico);
        inimigo.desenhar(grafico);
        bola.desenhar(grafico);

        grafico.dispose();
        bs.show();
    }

    private void atualizar() {
        jogador.atualizar();
        inimigo.atualizar();
        bola.atualizar();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            jogador.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            jogador.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            jogador.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            jogador.down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
