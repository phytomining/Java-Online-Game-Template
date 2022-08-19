package ServerGUI;

import Game.Input.KeyManager;
import Game.States.State;
import Game.gfx.Assets;
import Server.Connection;
import Server.Server;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ServerHandler implements Runnable {
    Display display;
    int width;
    int height;
    String window_title;
    public State current;
    public ServerState ss;
    public Server server;
    public KeyManager keymanager;
    Thread thread;
    boolean running = false;
    public ArrayList<Players> players = new ArrayList();

    public ServerHandler(String title, int width, int height, Server server) {
        this.width = width;
        this.height = height;
        this.window_title = title;
        this.server = server;
    }

    private void init() throws IOException {
        this.display = new Display(this.window_title, this.width, this.height, this);
        this.ss = new ServerState(this, 800, 500);
        this.display.frame.addKeyListener(this.keymanager);
        this.current = this.ss;
        Assets.init();
    }

    private void render() {
        BufferStrategy bs = this.display.getCanvas().getBufferStrategy();
        if (bs == null) {
            this.display.getCanvas().createBufferStrategy(3);
        } else {
            Graphics2D g = (Graphics2D)bs.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, this.width, this.height);
            g.drawImage(Assets.bg, 0, 0, this.width, this.height, (ImageObserver)null);
            g.setColor(Color.BLACK);
            g.drawString("Players: " + this.server.connections.size(), 20, 20);
            Iterator var4 = this.players.iterator();

            while(var4.hasNext()) {
                Players i = (Players)var4.next();
                i.render(g);
            }

            bs.show();
            g.dispose();
        }
    }

    public void tick() {
        Iterator var2 = this.players.iterator();

        while(var2.hasNext()) {
            Players i = (Players)var2.next();
            i.tick();
        }

    }

    public void key_press(KeyEvent e) throws IOException {
        this.current.key_press(e);
    }

    public void key_release(KeyEvent e) {
        this.current.key_release(e);
    }

    public void click(MouseEvent e) throws IOException {
        this.current.click(e);
    }

    public void run() {
        try {
            this.init();
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        float fps = 30.0F;
        double timePerTick = (double)(1.0E9F / fps);
        double delta = 0.0D;
        long lastTime = System.nanoTime();

        while(this.running) {
            long now = System.nanoTime();
            delta += (double)(now - lastTime) / timePerTick;
            lastTime = now;
            if (delta >= 1.0D) {
                this.tick();
                this.render();
                --delta;
            }
        }

        this.stop();
    }

    public synchronized void start() {
        if (!this.running) {
            this.running = true;
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public synchronized void stop() {
        try {
            this.save();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        if (this.running) {
            this.running = false;

            try {
                this.thread.join();
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }

        }
    }

    public void save() throws IOException {
        System.out.println("Saving player data!");
        File file = new File("api.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        Iterator var5 = this.server.connections.iterator();

        while(var5.hasNext()) {
            Connection c = (Connection)var5.next();
            pw.println(c.name);
            pw.println((int)((Players)this.players.get(c.number - 1)).x);
            pw.println((int)((Players)this.players.get(c.number - 1)).y);
        }

        pw.close();
        System.out.println("Saved!!");
    }

    public double[] load(String name) throws FileNotFoundException {
        File file = new File("api.txt");
        Scanner scan = new Scanner(file);
        boolean var4 = false;

        while(scan.hasNextLine()) {
            if (scan.nextLine().equals(name)) {
                double[] pos = new double[]{(double)Integer.parseInt(scan.nextLine()), (double)Integer.parseInt(scan.nextLine())};
                return pos;
            }
        }

        return null;
    }
}