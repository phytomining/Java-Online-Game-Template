package Game.Display;

import Game.Handler.GameHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;

public class Display {
    JFrame frame;
    public Canvas canvas;
    GameHandler game;
    String title;
    int width;
    int height;
    public static Socket socket;

    public Display(String title, int width, int height, GameHandler game) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.game = game;
        socket = socket;
        this.createDisplay();
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
        this.canvas.addMouseListener(new Mouse(this.game));
        this.canvas.addKeyListener(new Keyboard(this.game));
        this.frame.add(this.canvas);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public static class Keyboard extends KeyAdapter {
        GameHandler game;

        public Keyboard(GameHandler g) {
            this.game = g;
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
        GameHandler game;

        public Mouse(GameHandler game) {
            this.game = game;
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