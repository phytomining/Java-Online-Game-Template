package Game.States;

import Game.Handler.GameHandler;
import Game.gfx.Draw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Select extends State {
    int width;
    int height;
    GameHandler handler;
    int transition = 255;
    boolean transitioning = false;
    int tran_to = 1;
    int selected = 0;
    Font font = new Font("Roboto Light", 0, 56);

    public Select(int width, int height, GameHandler handler) {
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
                    this.transition = 255;
                    if (this.tran_to == 1) {
                        this.handler.menu();
                    } else {
                        this.handler.title();
                    }

                    this.transitioning = false;
                }
            }
        } else if (this.transition > 0) {
            this.transition -= 50;
            if (this.transition < 0) {
                this.transition = 0;
            }
        }

    }

    public void render(Graphics2D g) {
        g.setFont(this.font);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
        g.setColor(Color.WHITE);
        if (this.selected == 1) {
            g.setColor(new Color(128, 192, 255));
        } else {
            g.setColor(Color.WHITE);
        }

        Draw.rounded_rect(g, 150, 50, 500, 100, 10);
        if (this.selected == 2) {
            g.setColor(new Color(128, 192, 255));
        } else {
            g.setColor(Color.WHITE);
        }

        Draw.rounded_rect(g, 150, 200, 500, 100, 10);
        if (this.selected == 0) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.WHITE);
        }

        Draw.rounded_rect(g, 300, 350, 200, 100, 10);
        g.setColor(Color.BLACK);
        Draw.centered_text(g, this.font, "OK", 400, 400);
        Draw.centered_text(g, this.font, "Local", 400, 100);
        Draw.centered_text(g, this.font, "Global", 400, 250);
        g.setColor(new Color(0, 0, 0, this.transition));
        g.fillRect(0, 0, this.width, this.height);
    }

    public void key_press(KeyEvent e) {
        if (e.getKeyCode() == 27) {
            this.tran_to = 0;
            this.transitioning = true;
        }

    }

    public void key_release(KeyEvent e) {
    }

    public void click(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (150 <= x && x <= 650) {
            if (50 <= y && y <= 150) {
                this.selected = 1;
            } else if (200 <= y && y <= 300) {
                this.selected = 2;
            }

            if (300 <= x && x <= 500 && 350 <= y && y <= 450 && this.selected != 0) {
                this.tran_to = 1;
                this.transitioning = true;
            }
        }

    }
}