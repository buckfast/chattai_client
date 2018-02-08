package app.best.chattai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by buckfast on 9.10.2016.
 */
public class FirstActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        Button button2 = (Button) findViewById(R.id.enterbutton);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(getBaseContext(), MainActivity.class);

                EditText ip = (EditText) findViewById(R.id.ip);
                in.putExtra("ip_parameter", ip.getText().toString());

                EditText port = (EditText) findViewById(R.id.port);
                in.putExtra("port_parameter", port.getText().toString());

                startActivity(in);
                finish();
            }
        });
    }
}
