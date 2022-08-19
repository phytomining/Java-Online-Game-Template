package ServerGUI;

import Game.gfx.Assets;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Players {
    public double x;
    public double y;
    public double xmove;
    public double ymove;
    public boolean jump;
    public String name;
    public static final double GROUNDHEIGHT = 300.0D;
    public static final double SPEED = 3.0D;
    public static final double JUMP = 20.0D;
    public static final double FRICTION = 0.8D;
    public static final double GRAVITY = 2.0D;
    public final int WIDTH = 32;
    public final int HEIGHT = 96;

    public Players(String name, double x, double y) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.init();
    }

    public void init() {
        this.jump = true;
    }

    public void tick() {
        this.ymove += 2.0D;
        this.xmove *= 0.8D;
        this.x += this.xmove;
        this.y += this.ymove;
        if (this.y > 300.0D) {
            this.y = 300.0D;
            this.ymove = 0.0D;
            this.jump = true;
        }

        this.x = Math.max(this.x, 0.0D);
        this.x = Math.min(this.x, 768.0D);
    }

    public void render(Graphics g) {
        g.drawImage(Assets.player, (int)this.x, (int)this.y, (ImageObserver)null);
        g.drawString(this.name, (int)this.x - 20, (int)this.y - 20);
    }
}
