package ServerGUI;

import Game.States.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ServerState extends State {
    int width;
    int height;
    ServerHandler game;
    int transition = 255;
    boolean transitioning = false;
    int tran_to = 1;
    int stage = 1;
    String ip = "localhost";
    String name = "Default";
    String port = "6969";
    Font font = new Font("Roboto Light", 0, 32);

    public ServerState(ServerHandler game, int width, int height) {
        this.game = game;
        this.width = width;
        this.height = height;
    }

    public void tick() {
    }

    public void after_tick() {
    }

    public void render(Graphics2D g) {
    }

    public void key_press(KeyEvent e) {
        if (e.getKeyCode() == 8 && this.ip.length() > 0) {
            char[] chars = this.ip.toCharArray();
            char[] newip = new char[chars.length - 1];
            System.arraycopy(chars, 0, newip, 0, chars.length - 1);
            this.ip = new String(newip);
        } else if (e.getKeyCode() == 10) {
            this.tran_to = 1;
            this.transitioning = true;
        } else if (e.getKeyCode() == 27) {
            this.tran_to = 0;
            this.transitioning = true;
        } else {
            char c = e.getKeyChar();
            boolean valid = false;
            char[] var7;
            int var6 = (var7 = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.'}).length;

            for(int var5 = 0; var5 < var6; ++var5) {
                char ch = var7[var5];
                if (c == ch) {
                    valid = true;
                    break;
                }
            }

            if (valid) {
                if (this.stage == 1) {
                    this.ip = this.ip.concat(Character.toString(e.getKeyChar()));
                } else if (this.stage == 2) {
                    this.port = this.port.concat(Character.toString(e.getKeyChar()));
                } else if (this.stage == 3) {
                    this.name = this.name.concat(Character.toString(e.getKeyChar()));
                }
            }
        }

    }

    public void key_release(KeyEvent e) {
    }

    public void click(MouseEvent e) throws IOException {
        int x = e.getX();
        int y = e.getY();
        this.game.save();
    }
}
