package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

import java.util.concurrent.atomic.AtomicInteger;

public class TickBroadcast implements Broadcast {
    private AtomicInteger time;

    public TickBroadcast(AtomicInteger _time){
        time=_time;
    }
    public AtomicInteger getTime(){
        return time;
    }
}
