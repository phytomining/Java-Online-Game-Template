package Game.States;

import Game.Handler.GameHandler;
import Game.gfx.Draw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Title extends State {
    int width;
    int height;
    GameHandler handler;
    Font font = new Font("Engravers Old English", 0, 72);
    int transition = 0;
    boolean transitioning = false;

    public Title(int width, int height, GameHandler handler) {
        this.width = width;
        this.height = height;
        this.handler = handler;
    }

    public void tick() {
    }

    public void after_tick() {
        if (this.transitioning) {
            if (this.transition < 255) {
                this.transition += 50;
                if (this.transition >= 255) {
                    this.handler.select();
                    this.transition = 255;
                    this.transitioning = false;
                }
            }
        } else if (this.transition > 0) {
            this.transition -= 50;
            if (this.transition <= 0) {
                this.transition = 0;
            }
        }

    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.setFont(this.font);
        Draw.centered_text(g, this.font, "Fancy Tic Tac Toe", 400, 100);
        g.fillRect(200, 200, 400, 100);
        g.fillRect(200, 350, 400, 100);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Roboto Light", 0, 72));
        Draw.centered_text(g, this.font, "START", 400, 250);
        Draw.centered_text(g, this.font, "EXIT", 400, 400);
        g.setColor(new Color(0, 0, 0, this.transition));
        g.fillRect(0, 0, this.width, this.height);
    }

    public void key_press(KeyEvent e) {
    }

    public void key_release(KeyEvent e) {
    }

    public void click(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (200 < x && x < 600) {
            if (200 < y && y < 300) {
                this.transitioning = true;
            } else if (350 < y && y < 450) {
                System.exit(0);
            }
        }
    }
}
