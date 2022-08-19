package Server;

import ServerGUI.ServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {
    public ArrayList<Connection> connections = new ArrayList();
    ServerHandler handler;

    public Server() {
    }

    public void main() throws IOException {
        System.out.println("Starting server on port 6969");
        ServerSocket server = new ServerSocket(6969);
        this.init();

        while(true) {
            Socket s = server.accept();
            Connection c = new Connection(this, s, this.handler);
            this.connections.add(c);
            c.start();
        }
    }

    public void message(String name, String msg) {
        Iterator var3 = this.connections.iterator();

        while(var3.hasNext()) {
            Connection c = (Connection)var3.next();
            c.message(name, msg);
        }

    }

    public void message2(String name, String msg, String msg2) {
        Iterator var3 = this.connections.iterator();

        while(var3.hasNext()) {
            Connection c = (Connection)var3.next();
            c.message2(name, msg, msg2);
        }

    }

    public void broadcast(double[] pos, String name) {
        Iterator var3 = this.connections.iterator();

        while(var3.hasNext()) {
            Connection c = (Connection)var3.next();
            c.broadcast(pos, name);
        }

    }

    public void init() {
        this.handler = new ServerHandler("Server GUI", 800, 500, this);
        this.handler.start();
    }
}
