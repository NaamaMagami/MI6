package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.concurrent.atomic.AtomicInteger;

public class MissionReceivedEvent implements Event {
    private MissionInfo missionInfo;
    private AtomicInteger timeSent;

    public MissionReceivedEvent(MissionInfo _missionInfo, AtomicInteger t){
        missionInfo=_missionInfo;
        timeSent=t;
    }
    public MissionInfo getMissionInfo(){
        return missionInfo;
    }
    public AtomicInteger timeSent(){
        return timeSent;
    }


}
