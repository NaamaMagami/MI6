package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {
    Future future;

    @BeforeEach
    public void setUp(){
        future = new Future();
    }

    @Test
    public void getAndResolveTest() {
        Future <String> future =new Future<>();
        assertFalse(future.isDone());
        future.resolve("completed");
        assertTrue(future.get()=="completed");
        assertEquals ("completed",future.get());
    }

    @Test
    public void isDoneTest() {
        Future <String> neta =new Future<>();
        assertFalse(neta.isDone());
        neta.resolve("completed");
        assertTrue(neta.isDone());
    }

    @Test
    public void getTest() {
        Future <String> neta =new Future<>();
        neta.resolve("completed");

        assertTrue(neta.get(10, TimeUnit.NANOSECONDS)=="completed");
    }

}
