package Server;

import ServerGUI.Players;
import ServerGUI.ServerHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

public class Connection extends Thread {
    Socket s;
    DataOutputStream out;
    DataInputStream in;
    public String name;
    Server server;
    public ServerHandler handler;
    public int number;

    public Connection(Server server, Socket s, ServerHandler handler) throws IOException {
        System.out.println("Client Connected.");
        this.s = s;
        this.server = server;
        this.out = new DataOutputStream(s.getOutputStream());
        this.in = new DataInputStream(s.getInputStream());
        this.handler = handler;
    }

    public void run() {
        try {
            this.obtain_name();
            double[] pos = this.handler.load(this.name);
            if (pos == null) {
                this.handler.players.add(new Players(this.name, 300.0, 300.0));
                double[] posNew = new double[]{300.0, 300.0};
                this.server.broadcast(posNew, this.name);
            } else {
                this.handler.players.add(new Players(this.name, pos[0], pos[1]));
                this.server.broadcast(pos, this.name);
            }

            for(int i = 0; i < this.handler.players.size(); ++i) {
                if (!(this.handler.players.get(i)).name.equals(this.name)) {
                    double[] spos = new double[]{(this.handler.players.get(i)).x, (this.handler.players.get(i)).y};
                    this.server.broadcast(spos, (this.handler.players.get(i)).name);
                }
            }

            this.number = this.handler.players.size();

            while(true) {
                String msg;
                do {
                    String str;
                    do {
                        str = this.in.readUTF();
                    } while(!str.contains("MESSAGE"));

                    msg = str.substring(8);
                    this.server.message(this.name, msg);
                    Players players = this.handler.players.get(this.number - 1);
                    if (msg.equals("A")) {
                        players.xmove -= 3.0;
                    }

                    if (msg.equals("D")) {
                        players.xmove += 3.0;
                    }

                    if (msg.equals("^") && (this.handler.players.get(this.number - 1)).jump) {
                        players.ymove -= 20.0;
                        (this.handler.players.get(this.number - 1)).jump = false;
                    }

                    System.out.println("<" + name + "> " + msg);
                } while(!msg.equals("C"));

                String x = this.in.readUTF();
                String y = this.in.readUTF();
                System.out.println("Click recived: at coords: " + x + ", " + y);
                Iterator var7 = this.handler.players.iterator();

                while(var7.hasNext()) {
                    Players p = (Players) var7.next();
                    if ((double)Integer.parseInt(x) >= p.x) {
                        double var12 = Integer.parseInt(x);
                        double var10001 = p.x;
                        p.getClass();
                        if (var12 <= var10001 + 32.0 && (double)Integer.parseInt(y) >= p.y) {
                            var12 = Integer.parseInt(y);
                            var10001 = p.y;
                            p.getClass();
                            if (var12 <= var10001 + 96.0 && !p.name.equals(this.handler.players.get(this.number - 1))) {
                                System.out.println("Player: " + p.name + " is hit!!!");
                                if ((this.handler.players.get(this.number - 1)).x < p.x) {
                                    this.server.message2(p.name, "h", "left");
                                    p.xmove += 10.0;
                                    p.ymove -= 5.0;
                                } else {
                                    this.server.message2(p.name, "h", "right");
                                    p.xmove -= 10.0;
                                    p.ymove -= 5.0;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException var8) {
        }
    }

    public void message(String name, String msg) {
        try {
            this.out.writeUTF("MESSAGE" + name + msg);
        } catch (IOException var7) {
        }

        try {
            this.out.flush();
        } catch (IOException var4) {
        }

    }

    public void message2(String name, String msg, String msg2) {
        try {
            this.out.writeUTF("MESSAGE"+name+msg);
        } catch (IOException var9) {
        }

        try {
            this.out.writeUTF(msg2);
        } catch (IOException var6) {
        }

        try {
            this.out.flush();
        } catch (IOException var5) {
        }

    }

    public void broadcast(double[] pos, String target) {
        try {
            this.out.writeUTF("BROADCAST");
        } catch (IOException var8) {
        }

        try {
            this.out.writeUTF(target);
        } catch (IOException var7) {
        }

        try {
            this.out.writeInt((int)pos[0]);
        } catch (IOException var6) {
        }

        try {
            this.out.writeInt((int)pos[1]);
        } catch (IOException var5) {
        }

        try {
            this.out.flush();
        } catch (IOException var4) {
        }

    }

    private void obtain_name() throws IOException {
        String str;
        do {
            str = this.in.readUTF();
        } while(!str.equalsIgnoreCase("PLAYER"));
        this.name = this.in.readUTF();
    }
}
