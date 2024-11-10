package main;

import org.hsqldb.Server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class HsqlServer {

    public static void main(String[] args) {
        Server server = new Server();
        server.setDatabasePath(0, "mem:db1;sql.syntax_pgs=true");
        server.setDatabaseName(0, "db1");
        server.start();
    }
}
