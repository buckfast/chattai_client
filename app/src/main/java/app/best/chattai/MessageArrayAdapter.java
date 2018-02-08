package app.best.chattai;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MessageArrayAdapter extends ArrayAdapter<Message> {
    private Context context;
    private List<Message> messages;

    public MessageArrayAdapter(Context context, int resource, ArrayList<Message> objects) {
        super(context, resource, objects);

        this.context = context;
        this.messages = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = messages.get(position);
        View view;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (message.getInfoFlag()) {
            view = inflater.inflate(R.layout.list_item_info, null);
            TextView msg = (TextView) view.findViewById(R.id.msg);
            msg.setText(message.getStuff());

        } else {
            view = inflater.inflate(R.layout.list_item, null);

            TextView name = (TextView) view.findViewById(R.id.name);
            TextView msg = (TextView) view.findViewById(R.id.msg);
            TextView date = (TextView) view.findViewById(R.id.date);

            msg.setText(message.getText());
            name.setText(message.getName());
            date.setText(message.getDate());
        }

        return view;
    }
}

