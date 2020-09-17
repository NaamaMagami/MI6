package bgu.spl.mics.application.subscribers;


import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeOutBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {
	private Inventory inventory=Inventory.getInstance();
	private AtomicInteger Time=new AtomicInteger(0);

	private static class SingleHolder {
		private static Q instance = new Q();
	}
	public static Q getInstance(){
		return SingleHolder.instance;
	}

	public Q() {
		super("Q");
		inventory= Inventory.getInstance();
	}



	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, c ->{Time=c.getTime();});
		subscribeBroadcast(TimeOutBroadcast.class, c -> {terminate(); });
		subscribeEvent(GadgetAvailableEvent.class,c ->{
			c.getReport().setQTime(Time.intValue());
			if(inventory.getItem(c.getGadget())){
				complete(c,"Completed");
			}
			else{
				complete(c,"Not completed");
			}
		} );
		
	}

}
