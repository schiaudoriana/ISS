package service.utils;

public interface Observable<E extends ObsEvent> {
    void add(Observer<E> obs);
    void notifyObservers(E event);
}
