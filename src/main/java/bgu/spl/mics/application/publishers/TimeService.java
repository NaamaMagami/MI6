package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeOutBroadcast;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
	int duration;
	AtomicInteger timeTotal=new AtomicInteger(0);


	public TimeService(int _duration) {
		super("TimeService");
		duration=_duration;
	}

	@Override
	protected void initialize() {
	}

	@Override
	public void run() {
		initialize();
		while (timeTotal.intValue()<duration){
			try {
				Thread.sleep(100);
				Broadcast b = new TickBroadcast(timeTotal);
				getSimplePublisher().sendBroadcast(b);
				timeTotal.compareAndSet(timeTotal.intValue(), timeTotal.incrementAndGet());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		Broadcast end = new TimeOutBroadcast();
		getSimplePublisher().sendBroadcast(end);
	}

}
