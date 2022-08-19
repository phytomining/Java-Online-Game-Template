package Game.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    public static BufferedImage bg;
    public static BufferedImage player;

    public Assets() {
    }

    public static void init() {
        bg = ImageLoader.loadImage("/textures/bg.png");
        player = ImageLoader.loadImage("/textures/player.png");
    }
}