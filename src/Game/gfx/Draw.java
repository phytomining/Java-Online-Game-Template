package Game.gfx;

import java.awt.*;

public class Draw {
    public Draw() {
    }

    public static void centered_text(Graphics2D g, Font font, String text, int xval, int yval) {
        FontMetrics metric = g.getFontMetrics(font);
        int x = xval - metric.stringWidth(text) / 2;
        int y = (int)((long)(yval + metric.getHeight() / 2) - Math.round((double)font.getSize() / 5.0D));
        g.drawString(text, x, y);
    }

    public static void rounded_rect(Graphics2D g, int x, int y, int width, int height, int round) {
        int d = round * 2;
        int w = width - d;
        int h = height - d;
        g.fillRect(x + round, y, w, height);
        g.fillRect(x, y + round, width, h);
        g.fillOval(x, y, d, d);
        g.fillOval(x + w, y, d, d);
        g.fillOval(x, y + h, d, d);
        g.fillOval(x + w, y + h, d, d);
    }
}
