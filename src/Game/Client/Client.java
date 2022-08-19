package Game.Client;

import Game.Handler.GameHandler;
import ServerGUI.Players;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;

public class Client {
    String ip;
    String name;
    int port;
    GameHandler game;
    DataOutputStream output;

    public Client(String ip, int port, String name, GameHandler game) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.game = game;
        this.init();
    }

    public void main() throws IOException {
        new BufferedReader(new InputStreamReader(System.in));
        Socket s = new Socket(this.ip, this.port);
        Connection c = new Connection(s, this.name, this.game);
        this.output = new DataOutputStream(s.getOutputStream());
        c.start();
        this.output.writeUTF("PLAYER");
        this.output.writeUTF(this.name);
        this.output.flush();
    }

    public void packet(String str) throws IOException {
        this.output.writeUTF("MESSAGE " + str);
        //this.output.writeUTF(str);
        this.output.flush();
    }

    public void packet(String str, String str1) throws IOException {
        this.output.writeUTF("MESSAGE");
        this.output.writeUTF(str);
        this.output.writeUTF(str1);
        this.output.flush();
    }

    public void packetExist(String str, double x, double y) throws IOException {
        this.output.writeUTF("MESSAGE");
        this.output.writeUTF(str);
        this.output.writeDouble(x);
        this.output.writeDouble(y);
        this.output.flush();
    }

    public void clickpacket(MouseEvent e) throws IOException {
        this.output.writeUTF("MESSAGE C");
        //this.output.writeUTF("C");
        this.output.writeUTF(Integer.toString(e.getX()));
        this.output.writeUTF(Integer.toString(e.getY()));
        this.output.flush();
    }

    public void clickpackethack() throws IOException {
        Iterator var2 = this.game.players.iterator();

        while(var2.hasNext()) {
            Players p = (Players)var2.next();
                this.output.writeUTF("MESSAGE C");
                //this.output.writeUTF("C");
                this.output.writeUTF(Integer.toString((int)p.x + 1));
                this.output.writeUTF(Integer.toString((int)p.y + 1));
                this.output.flush();
        }
    }

    public void init() {
    }
}
