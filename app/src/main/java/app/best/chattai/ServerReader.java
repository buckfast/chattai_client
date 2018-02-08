package app.best.chattai;

import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerReader implements Runnable {
    private Socket s;
    private Scanner sc;
    private String line;
    private ChatHistory ch;

    public ServerReader(Socket s, ChatHistory ch) {
        this.s = s;
        this.ch = ch;
    }

    @Override
    public void run() {
        try {
            sc = new Scanner(s.getInputStream());
            while (true) {

                line = sc.nextLine();

                    ch.insert(line);

                Log.d("uusi", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
