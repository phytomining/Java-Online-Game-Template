package Game.Client;

import Game.Entities.Player;
import Game.Handler.GameHandler;
import Game.States.Game;
import ServerGUI.Players;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

public class Connection extends Thread {
    Socket s;
    DataOutputStream out;
    DataInputStream in;
    String name;
    GameHandler game;

    public Connection(Socket s, String name, GameHandler game) throws IOException {
        this.s = s;
        this.name = name;
        this.out = new DataOutputStream(s.getOutputStream());
        this.in = new DataInputStream(s.getInputStream());
        this.game = game;
    }

    public void run() {
        try {
            while(true) {
                String str = this.in.readUTF();
                String name;
                int p;
                if (str.contains("MESSAGE")) {
                    name = str.substring(7,str.length()-1);
                    String msg = str.substring(str.length()-1);
                    if (!name.equals(this.name)) {
                        if (this.game.players.size() != 0) {
                            System.out.println(name + " " + msg);
                            boolean valid = false;

                            for(p = 0; p < this.game.players.size(); ++p) {
                                if ((this.game.players.get(p)).name.equals(name)) {
                                    Players var14;
                                    if (msg.equals("A")) {
                                        var14 = this.game.players.get(p);
                                        var14.xmove -= 3.0D;
                                    }

                                    if (msg.equals("D")) {
                                        var14 = this.game.players.get(p);
                                        var14.xmove += 3.0D;
                                    }

                                    if (msg.equals("^") && (this.game.players.get(p)).jump) {
                                        var14 = this.game.players.get(p);
                                        var14.ymove -= 20.0D;
                                        (this.game.players.get(p)).jump = false;
                                    }

                                    if (msg.equals("h")) {
                                        String dir = this.in.readUTF();
                                        if (dir.equals("left")) {
                                            var14 = this.game.players.get(p);
                                            var14.xmove += 10.0D;
                                            var14 = this.game.players.get(p);
                                            var14.ymove -= 5.0D;
                                        } else {
                                            var14 = this.game.players.get(p);
                                            var14.xmove -= 10.0D;
                                            var14 = this.game.players.get(p);
                                            var14.ymove -= 5.0D;
                                        }
                                    }

                                    valid = true;
                                    break;
                                }
                            }
                        }
                    } else if (msg.equals("h")) {
                        String dir = this.in.readUTF();
                        Player var10000;
                        if (dir.equals("left")) {
                            var10000 = this.game.game.player;
                            var10000.xmove += 10.0D;
                            var10000 = this.game.game.player;
                            var10000.ymove -= 5.0D;
                        } else {
                            var10000 = this.game.game.player;
                            var10000.xmove -= 10.0D;
                            var10000 = this.game.game.player;
                            var10000.ymove -= 5.0D;
                        }
                    }
                } else if (str.equalsIgnoreCase("BROADCAST")) {
                    name = this.in.readUTF();
                    int x,y;
                    if (name.equals(this.name)) {
                        x = this.in.readInt();
                        y = this.in.readInt();
                        this.game.game = new Game(800, 500, this.game, x, y);
                    } else {
                        boolean valid = true;
                        x = this.in.readInt();
                        p = this.in.readInt();
                        Iterator var7 = this.game.players.iterator();

                        while(var7.hasNext()) {
                            Players pa = (Players) var7.next();
                            if (name.equals(pa.name)) {
                                valid = false;
                            }
                        }

                        if (valid) {
                            this.game.players.add(new Players(name, (double)x, (double)p));
                        }
                    }
                }
            }
        } catch (IOException var8) {
        }
    }
}