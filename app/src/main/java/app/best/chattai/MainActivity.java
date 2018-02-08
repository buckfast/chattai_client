package app.best.chattai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer {

    private ChatHistory ch = ChatHistory.getInstance();

    private Socket s;
    private String ip;
    private int port;
    private String message;
    private ListView listView;

    private ArrayAdapter<Message> adapter;
    private ArrayList<Message> messageItems = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.main_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int option = item.getItemId();
        switch (option) {
            case R.id.menuid1:
                namechange();
                break;
            case R.id.menuid2:
                list();
                break;
            case R.id.menuid3:
                history();
                break;
            case R.id.menuid4:
                exit();
                break;
            case R.id.menuid5:
                help();
                break;

        }
        return false;
    }

    private void namechange() {
        popup();
    }

    private void history() {
        sendMessage("/history");
    }

    private void exit() {
        sendMessage("/quit");
        System.exit(0);
    }

    private void list() {
        sendMessage("/list");
    }

    private void help() {
        sendMessage(":)");

    }

    private void sendMessage(final String m) {
        new Thread(new ServerWriter(s, m)).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Bundle extras = getIntent().getExtras();
        ip = extras.getString("ip_parameter");
        port = Integer.parseInt(extras.getString("port_parameter"));


        ch.register(this);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);

        adapter = new MessageArrayAdapter(this, 0, messageItems);

        listView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(this, R.string.toast,Toast.LENGTH_SHORT);

                runOnUiThread(new Runnable() {
                    @Override

                    public void run() {
                        message = (editText.getText().toString());
                        editText.setText("");
                    }
                });
                new Thread(new ServerWriter(s, message)).start();
            }
        });


        new Thread(new Runnable() {


            @Override
            public void run() {
                try {
                    s = new Socket(ip, port);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                new Thread(new ServerReader(s, ch)).start();

            }
        }).start();


    }

    @Override
    public void update(final Message m) {
        //lisaa listaan
        //Date date=new Date(m.getTimestamp());

        runOnUiThread(new Runnable() {
            @Override

            public void run() {
                if (m.getStuff().charAt(0) == '#') {
                    //items.add(m.getStuff());
                    Message msg = m;
                    msg.setInfoFlag();
                    messageItems.add(msg);
                } else {
                    messageItems.add(m);
                }

                adapter.notifyDataSetChanged();
                scrollToBottom();
            }
        });
    }


    private void scrollToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(adapter.getCount() - 1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        sendMessage("/quit");
        super.onDestroy();
    }


    private void popup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setView(view);

        final EditText input = (EditText) view.findViewById(R.id.editname);

        dialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendMessage("/nick "+input.getText());
                    }
                });
        dialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =dialogBuilder.create();
        alertDialog.show();
    }


}


