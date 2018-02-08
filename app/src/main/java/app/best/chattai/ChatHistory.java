package app.best.chattai;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatHistory implements Observable {

    private List<Message> history;
    private Set<Observer> observers;

    private static ChatHistory ourInstance = new ChatHistory();

    public synchronized static ChatHistory getInstance() {
        return ourInstance;
    }

    private ChatHistory() {
        history = new ArrayList<>();
        observers = new HashSet<>();
    }

    public synchronized void insert(String s) {
        Message msg = new Message(s.trim());
            history.add(msg);
        notifyObservers(msg);
    }

    @Override
    public synchronized void register(MainActivity ma) {
        observers.add(ma);
    }

    @Override
    public synchronized void deregister(MainActivity ma) {
        observers.remove(ma);
    }

    @Override
    public synchronized void  notifyObservers(Message m) {
        for(Observer o: this.observers) {
            o.update(m);
        }
    }
}
