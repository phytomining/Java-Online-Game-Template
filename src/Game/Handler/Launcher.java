package Game.Handler;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        GameHandler game = new GameHandler("Online Game", 800, 500);
        System.out.println("Launching Game");
        game.start();
    }
}