package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Event;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeOutBroadcast;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private int id;
	private LinkedList<MissionInfo> missionsList;
	private AtomicInteger time;
	public Intelligence(int _id, LinkedList<MissionInfo> _missionsList) {
		super("Intelligence");
		id=_id;
		missionsList=_missionsList;
		missionsList.sort(Comparator.comparing(MissionInfo::getTimeIssued));
	}


	@Override
	protected void initialize() {
		subscribeBroadcast(TimeOutBroadcast.class, c->{terminate(); });
		subscribeBroadcast(TickBroadcast.class , c-> {
			time=c.getTime();
			synchronized (missionsList){
			if (missionsList.size()!=0 && missionsList.getFirst()!=null){
				if (missionsList.get(0).getTimeIssued()<=time.intValue()){
					Event ev = new MissionReceivedEvent(missionsList.get(0),time);
					getSimplePublisher().sendEvent(ev);
					missionsList.remove(0);
				}
			}
		}});
	}


}
