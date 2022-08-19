package ServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Display {
    public JFrame frame;
    Canvas canvas;
    ServerHandler serverHandler;
    String title;
    int width;
    int height;
    public static boolean[] keys;
    public static boolean up;
    public boolean down;
    public static boolean left;
    public static boolean right;
    public boolean spawn;

    public Display(String title, int width, int height, ServerHandler serverHandler) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.serverHandler = serverHandler;
        this.createDisplay();
        keys = new boolean[600];
    }

    private void createDisplay() {
        this.frame = new JFrame(this.title);
        this.frame.setSize(this.width, this.height);
        this.frame.setDefaultCloseOperation(3);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo((Component)null);
        this.canvas = new Canvas();
        this.canvas.setMaximumSize(new Dimension(this.width, this.height));
        this.canvas.setMinimumSize(new Dimension(this.width, this.height));
        this.canvas.setPreferredSize(new Dimension(this.width, this.height));
        this.canvas.addMouseListener(new Mouse(this.serverHandler));
        this.canvas.addKeyListener(new Keyboard(this.serverHandler));
        this.frame.add(this.canvas);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public static class Keyboard extends KeyAdapter {
        ServerHandler game;

        public Keyboard(ServerHandler serverHandler) {
            this.game = serverHandler;
        }

        public void keyPressed(KeyEvent e) {
            try {
                this.game.key_press(e);
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        }

        public void keyReleased(KeyEvent e) {
            this.game.key_release(e);
        }
    }

    public static class Mouse implements MouseListener {
        ServerHandler game;

        public Mouse(ServerHandler serverHandler) {
            this.game = serverHandler;
        }

        public void mouseClicked(MouseEvent mouseEvent) {
        }

        public void mousePressed(MouseEvent mouseEvent) {
            try {
                this.game.click(mouseEvent);
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        }

        public void mouseReleased(MouseEvent mouseEvent) {
        }

        public void mouseEntered(MouseEvent mouseEvent) {
        }

        public void mouseExited(MouseEvent mouseEvent) {
        }
    }
}
