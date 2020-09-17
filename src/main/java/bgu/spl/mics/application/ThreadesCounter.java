package bgu.spl.mics.application;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadesCounter {
    AtomicInteger counter;
    private static class SingleHolder {
        private static ThreadesCounter instance = new ThreadesCounter();
    }
    private ThreadesCounter() {
        counter = new AtomicInteger(0);
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static ThreadesCounter getInstance() {
        return SingleHolder.instance;
    }

    public int getIntThreadesCounter (){
        return counter.intValue();
    }

    public void IncreaseCounter (){
        counter.compareAndSet(counter.intValue(),counter.incrementAndGet());
    }
}
