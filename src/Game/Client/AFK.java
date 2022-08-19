package Game.Client;

import java.io.IOException;

public class AFK implements Runnable {
    Client client;
    Thread thread;
    boolean running = false;

    public AFK(Client client) {
        this.client = client;
    }

    public void run() {
        double fps = 0.5D;
        double timePerTick = 1.0E9D / fps;
        double delta = 0.0D;
        long lastTime = System.nanoTime();

        while(this.running) {
            long now = System.nanoTime();
            delta += (double)(now - lastTime) / timePerTick;
            lastTime = now;
            if (delta >= 1.0D) {
                try {
                    this.client.packet(" ");
                } catch (IOException var13) {
                }

                --delta;
            }
        }

        try {
            this.stop();
        } catch (InterruptedException var12) {
            var12.printStackTrace();
        }

    }

    public synchronized void start() {
        this.thread = new Thread(this);
        this.running = true;
        this.thread.start();
    }

    public synchronized void stop() throws InterruptedException {
        this.running = false;
        this.thread.join();
    }
}
