package app.best.chattai;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerWriter implements Runnable{
    private Socket s;
    private String line;
    private PrintStream pw;

    public  ServerWriter(Socket s, String msg) {
        this.s = s;
        this.line = msg;
    }

    @Override
    public  void run() {

        try {
            pw = new PrintStream(s.getOutputStream(), true);
            pw.println(line.trim());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
