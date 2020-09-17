package bgu.spl.mics;


import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageBrokerTest {
    private Subscriber M;
    private Subscriber subscriber2 ;
    private MessageBroker broker;
    private ExampleEvent event;
    private ExampleBroadcast broadcast;

    @BeforeEach
    public void setUp() {
        M = new M(1);
        broker = MessageBrokerImpl.getInstance();
        subscriber2 = new M(2);
        event = new ExampleEvent("M");
        broadcast = new ExampleBroadcast("subscriber2");
    }

    @Test
    public void getInstanceTest (){
        MessageBroker broker2 = MessageBrokerImpl.getInstance();
        assertEquals(broker2,broker);
        assertFalse(broker2==null);
    }



    @Test
    public void subscribeEventTest (){
        broker.subscribeEvent(event.getClass() ,M);
        broker.sendEvent(event);
        try {
            assertEquals(broker.awaitMessage(M), event);
        }catch(InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void subscribeBroadcastTest (){
        broker.subscribeBroadcast(broadcast.getClass() ,M);
        broker.sendBroadcast(broadcast);
        try {
            assertEquals(broker.awaitMessage(M), broadcast);
        }catch(InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void completeTest (){
        String s = "completed";
        Future<String> future = broker.sendEvent(event);
        broker.complete(event,s);
        assertTrue(future.isDone());
    }

    @Test
    public void sendBroadcastTest (){
        broker.subscribeBroadcast(broadcast.getClass() ,M);
        broker.sendBroadcast(broadcast);
        try {
            assertEquals(broker.awaitMessage(M), broadcast);
        }catch(InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void sendEventTest (){
        broker.subscribeEvent(event.getClass() ,M);
        broker.sendEvent(event);
        try {
            assertEquals(broker.awaitMessage(M), event);
        }catch(InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void registerAndUnRegisterTest (){
        broker.register(M);
        broker.subscribeEvent(event.getClass(),subscriber2);
        broker.sendEvent(event);
        try{
            assertEquals(broker.awaitMessage(M),event);
        }catch(InterruptedException exc) {
            exc.printStackTrace();
        }
        broker.unregister(M);
        broker.sendEvent(event);
        try{
            assertFalse(broker.awaitMessage(M)==event);
        }catch(InterruptedException exc) {
            exc.printStackTrace();
        }

    }

    @Test
    public void awaitMessageTest (){
        broker.subscribeEvent(event.getClass(),subscriber2);
        broker.sendEvent(event);
        try{
            assertEquals(broker.awaitMessage(subscriber2),event);
        }catch(InterruptedException exc) {
            exc.printStackTrace();
        }
    }
}
