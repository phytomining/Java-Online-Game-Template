package Game.Entities;

import Game.Handler.GameHandler;
import Game.gfx.Assets;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Player {
    public static double velocity;
    public GameHandler game;
    public double x;
    public double y;
    public double xmove;
    public double ymove;
    public boolean jump;
    public int health = 100;
    public final double GROUNDHEIGHT = 300.0D;
    public final double SPEED = 3.0D;
    public final double JUMP = 20.0D;
    public final double FRICTION = 0.8D;
    public final double GRAVITY = 2.0D;
    public final int WIDTH = 32;
    public final int HEIGHT = 96;

    public Player(GameHandler game, double x, double y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.init();
    }

    public void init() {
        this.jump = true;
    }

    public void tick() {
        if (this.game.keymanager.left) {
            this.xmove -= SPEED;
        }

        if (this.game.keymanager.right) {
            this.xmove += SPEED;
        }

        if (this.game.keymanager.up && this.jump) {
            this.ymove -= JUMP;
            this.jump = false;
        }

        this.ymove += GRAVITY;
        this.xmove *= FRICTION;
        this.x += this.xmove;
        this.y += this.ymove;
        if (this.y > GROUNDHEIGHT) {
            this.y = GROUNDHEIGHT;
            this.ymove = 0;
            this.jump = true;
        }

        this.x = Math.max(this.x, 0);
        this.x = Math.min(this.x, (this.game.width - WIDTH));

        this.y = Math.min(this.y, game.height - HEIGHT);
    }

    public void render(Graphics g) {
        g.drawImage(Assets.player, (int)this.x, (int)this.y, (ImageObserver)null);
    }
}
