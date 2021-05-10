package service.utils;

public interface Observer<E extends ObsEvent> {
    void update(E e);
}
