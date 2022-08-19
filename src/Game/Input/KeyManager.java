package Game.Input;

import Game.Handler.GameHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class KeyManager implements KeyListener {
    public boolean[] keys = new boolean[256];
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean spawn;
    GameHandler game;

    public KeyManager(GameHandler game) {
        this.game = game;
    }

    public void tick() {
        this.left = this.keys[65];
        this.right = this.keys[68];
        this.up = this.keys[32];
        if (this.left) {
            try {
                this.game.client.packet("A");
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }

        if (this.right) {
            try {
                this.game.client.packet("D");
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

        if (this.up) {
            try {
                this.game.client.packet("^");
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }

    public void keyPressed(KeyEvent e) {
        this.keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {
    }
}
