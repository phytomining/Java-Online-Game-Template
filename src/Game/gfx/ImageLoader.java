package Game.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public ImageLoader() {
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException var2) {
            var2.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}