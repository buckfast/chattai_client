package app.best.chattai;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String text;
    private String name;
    private long timestamp;
    private String raw;
    private boolean isInfoMessage = false;

    public Message(String msg) {
        raw = msg;
        if (msg.charAt(0) != '#') {

            String tmp[] = msg.split(":", 3);
            name = tmp[0];
            timestamp = Long.parseLong(tmp[1]);
            text = tmp[2];
        }

    }
public String getStuff() {
    return raw;
}
    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        Date date = new Date(getTimestamp());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }
    public void setInfoFlag() {
        isInfoMessage = true;
    }
    public boolean getInfoFlag() {
        return isInfoMessage;
    }
}

