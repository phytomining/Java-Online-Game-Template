package Game.States;

import Game.Entities.Player;
import Game.Handler.GameHandler;
import Game.gfx.Assets;
import ServerGUI.Players;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends State {
    int width;
    int height;
    GameHandler game;
    public Player player;
    public boolean hak;

    public Game(int width, int height, GameHandler game, int x, int y) throws IOException {
        this.width = width;
        this.height = height;
        this.game = game;
        this.init(x, y);
    }

    public void tick() throws IOException {
        game.keymanager.tick();
        player.tick();

        for (Players p : game.players) {
            p.tick();
        }

    }

    public void after_tick() {
    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.bg, 0, 0, width, height, null);
        player.render(g);
        g.setColor(Color.BLACK);

        for (Players p : game.players) {
            p.render(g);
        }

    }

    public void key_press(KeyEvent e) throws IOException {
    }

    public void key_release(KeyEvent e) {
    }

    public void click(MouseEvent e) throws IOException {
        if (hak) {
            game.client.clickpackethack();
        } else {
            game.client.clickpacket(e);
        }

    }

    private void init(int x, int y) throws IOException {
        player = new Player(this.game, (double)x, (double)y);
        hak = true;
    }
}
