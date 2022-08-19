package Game.States;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public abstract class State {
    public State() {
    }

    public abstract void tick() throws IOException;

    public abstract void after_tick();

    public abstract void render(Graphics2D var1);

    public abstract void key_press(KeyEvent var1) throws IOException;

    public abstract void key_release(KeyEvent var1);

    public abstract void click(MouseEvent var1) throws IOException;
}