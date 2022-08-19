package Game.States;

import Game.Handler.GameHandler;
import Game.gfx.Draw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Menu extends State {
    int width;
    int height;
    GameHandler game;
    int transition = 255;
    boolean transitioning = false;
    int tran_to = 1;
    int stage = 1;
    String ip = "localhost";
    String name = "Default";
    String port = "6969";
    Font font = new Font("Roboto Light", 0, 32);

    public Menu(GameHandler game, int width, int height) {
        this.game = game;
        this.width = width;
        this.height = height;
    }

    public void tick() {
    }

    public void after_tick() {
        if (this.transitioning) {
            if (this.transition < 255) {
                this.transition += 50;
                if (this.transition >= 255) {
                    this.transition = 255;
                    if (this.tran_to == 1) {
                        if (this.stage == 1) {
                            this.stage = 2;
                            this.transitioning = false;
                        } else if (this.stage == 2) {
                            this.stage = 3;
                            this.transitioning = false;
                        } else if (this.stage == 3) {
                            try {
                                this.game.connect(this.ip, Integer.parseInt(this.port), this.name);
                            } catch (NumberFormatException var4) {
                                var4.printStackTrace();
                                try {
                                    this.game.stop();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.exit(1);
                            } catch (IOException var5) {
                                var5.printStackTrace();
                            }

                            try {
                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException var2) {
                                    var2.printStackTrace();
                                }
                                this.game.game();
                            } catch (IOException var3) {
                                System.out.println("L no work");
                            }
                        }
                    }
                }
            }
        } else if (this.transition > 0) {
            this.transition -= 50;
            if (this.transition < 0) {
                this.transition = 0;
            }
        }

    }

    public void render(Graphics2D g) {
        g.setFont(this.font);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.width, this.height);
        g.setColor(Color.WHITE);
        Draw.rounded_rect(g, 150, 100, 500, 300, 10);
        g.setColor(Color.LIGHT_GRAY);
        Draw.rounded_rect(g, 200, 200, 400, 50, 10);
        Draw.rounded_rect(g, 300, 300, 200, 50, 10);
        g.setColor(Color.BLACK);
        if (this.stage == 1) {
            Draw.centered_text(g, this.font, "NEXT", 400, 325);
            Draw.centered_text(g, this.font, "Enter Server IP:", 400, 150);
            Draw.centered_text(g, this.font, this.ip, 400, 225);
        } else if (this.stage == 2) {
            Draw.centered_text(g, this.font, "NEXT", 400, 325);
            Draw.centered_text(g, this.font, "Enter Server Port:", 400, 150);
            Draw.centered_text(g, this.font, this.port, 400, 225);
        } else if (this.stage == 3) {
            Draw.centered_text(g, this.font, "CONNECT", 400, 325);
            Draw.centered_text(g, this.font, "Enter Name:", 400, 150);
            Draw.centered_text(g, this.font, this.name, 400, 225);
        }

        g.setColor(new Color(0, 0, 0, this.transition));
        g.fillRect(0, 0, this.width, this.height);
    }

    public void key_press(KeyEvent e) {
        if (e.getKeyCode() == 8 && (ip.length() > 0 || port.length() > 0 || name.length() > 0)) {
            if(stage == 1){
                char[] chars = this.ip.toCharArray();
                char[] newip = new char[chars.length - 1];
                System.arraycopy(chars, 0, newip, 0, chars.length - 1);
                this.ip = new String(newip);
            }else if(stage == 2){
                char[] chars = this.port.toCharArray();
                char[] newip = new char[chars.length - 1];
                System.arraycopy(chars, 0, newip, 0, chars.length - 1);
                this.port = new String(newip);
            }else if(stage == 3){
                char[] chars = this.name.toCharArray();
                char[] newip = new char[chars.length - 1];
                System.arraycopy(chars, 0, newip, 0, chars.length - 1);
                this.name = new String(newip);
            }


        } else if (e.getKeyCode() == 10) {
            this.tran_to = 1;
            this.transitioning = true;
        } else if (e.getKeyCode() == 27) {
            this.tran_to = 0;
            this.transitioning = true;
        } else {
            char c = e.getKeyChar();
            boolean valid = false;
            char[] var7;
            int var6 = (var7 = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.'}).length;

            for(int var5 = 0; var5 < var6; ++var5) {
                char ch = var7[var5];
                if (c == ch) {
                    valid = true;
                    break;
                }
            }

            if (valid) {
                if (this.stage == 1) {
                    this.ip = this.ip.concat(Character.toString(e.getKeyChar()));
                } else if (this.stage == 2) {
                    this.port = this.port.concat(Character.toString(e.getKeyChar()));
                }
            }
            if (this.stage == 3) {
                this.name = this.name.concat(Character.toString(e.getKeyChar()));
            }
        }
    }

    public void key_release(KeyEvent e) {
    }

    public void click(MouseEvent e) throws IOException {
        int x = e.getX();
        int y = e.getY();
        if (300 <= x && x <= 500 && 300 <= y && y <= 350) {
            this.tran_to = 1;
            this.transitioning = true;
        }
    }
}
