package app.best.chattai;

public interface Observable {
    public void register(MainActivity ma);
    public void deregister(MainActivity ma);
    void notifyObservers(Message m);
}