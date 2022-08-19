package Game.Handler;

import Game.Client.Client;
import Game.Display.Display;
import Game.Input.KeyManager;
import Game.States.Menu;
import Game.States.*;
import Game.gfx.Assets;
import ServerGUI.Players;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;

public class GameHandler implements Runnable {
    Display display;
    public int width;
    public int height;
    String window_title;
    public Game game;
    public Select select;
    public Title title;
    public State current;
    public Menu menu;
    public Client client;
    public KeyManager keymanager;
    public int fps;
    public ArrayList<Players> players = new ArrayList();
    Thread thread;
    boolean running = false;

    public GameHandler(String title, int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        this.window_title = title;
        this.keymanager = new KeyManager(this);
    }

    private void init() throws IOException {
        display = new Display(this.window_title, this.width, this.height, this);
        display.canvas.addKeyListener(this.keymanager);
        Assets.init();
        menu = new Menu(this, this.width, this.height);
        title = new Title(this.width, this.height, this);
        select = new Select(this.width, this.height, this);
        game = new Game(this.width,this.height,this,300, 100);
        current = this.title;
    }

    private void render(int fps) {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            Graphics2D g = (Graphics2D)bs.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, this.width, this.height);
            current.render(g);
            g.setColor(Color.RED);
            g.drawString("FPS: " + fps, 20, 20);
            bs.show();
            g.dispose();
        }
    }

    public void key_press(KeyEvent e) throws IOException {
        current.key_press(e);
    }

    public void key_release(KeyEvent e) {
        current.key_release(e);
    }

    public void click(MouseEvent e) throws IOException {
        current.click(e);
    }

    public void run() {
        try {
            init();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

        float fps = 30.0F;
        double timePerTick = (double)(1.0E9F / fps);
        double delta = 0.0D;
        long lastTime = System.nanoTime();

        while(running) {
            long now = System.nanoTime();
            delta += (double)(now - lastTime) / timePerTick;
            lastTime = now;
            if (delta >= 1.0D) {
                ++this.fps;
                try {
                    current.tick();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }

                render(this.fps);
                current.after_tick();
                --delta;
                if (fps == 1.0F) {
                    try {
                        client.packet(" ");
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }
                }
            }
        }

        try {
            this.stop();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

    }

    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() throws IOException {
        if (running) {
            running = false;

            try {
                thread.join();
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }

        }
    }

    public void game() throws IOException {
        current = game;
    }

    public void title() {
        current = title;
    }

    public void select() {
        current = select;
    }

    public void menu() {
        current = menu;
    }

    public void connect(String ip, int port, String name) throws IOException {
        client = new Client(ip, port, name, this);
        client.main();
    }
}