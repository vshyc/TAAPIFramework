package customer.stake.db;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class ObjectsPool<T> {
    private long deadTime;
    private Hashtable<T, Long> lock;
    private Hashtable<T, Long> unlock;

    ObjectsPool() {
        deadTime = 60000;
        lock = new Hashtable();
        unlock = new Hashtable();
    }

    abstract T create();

    abstract boolean validate(T o);

    abstract void close(T o);

    synchronized T takeOut() {
        long now = System.currentTimeMillis();
        T t;
        if (unlock.size() > 0) {
            Enumeration<T> e = unlock.keys();
            while (e.hasMoreElements()) {
                t = e.nextElement();
                if ((now - unlock.get(t)) > deadTime) {
                    unlock.remove(t);
                    close(t);
                    t = null;
                } else {
                    if (validate(t)) {
                        unlock.remove(t);
                        lock.put(t, now);
                        return (t);
                    } else {
                        unlock.remove(t);
                        close(t);
                        t = null;
                    }
                }
            }
        }
        t = create();
        lock.put(t, now);
        return (t);
    }

    synchronized void takeIn(T t) {
        lock.remove(t);
        unlock.put(t, System.currentTimeMillis());
    }
}