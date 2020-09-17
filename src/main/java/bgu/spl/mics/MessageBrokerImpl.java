package bgu.spl.mics;
import bgu.spl.mics.application.messages.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	private volatile ConcurrentHashMap <Event ,Future> eventsMap = new ConcurrentHashMap();
	//private Map <Subscriber , ConcurrentLinkedQueue<Class<? extends Event>>>  EventsBySubscribers= new HashMap<>();
	//private Map <Subscriber , ConcurrentLinkedQueue<Class<? extends Broadcast>>> BroadcastBySubscribers= new HashMap<>();
	private volatile ConcurrentHashMap <Class<? extends Event>, ConcurrentLinkedQueue<Subscriber>> subscribersByEventsType= new ConcurrentHashMap();
	private volatile ConcurrentHashMap <Class<? extends Broadcast>, ConcurrentLinkedQueue<Subscriber>> subscribersByBroadcast= new ConcurrentHashMap();
	private volatile ConcurrentHashMap<Subscriber, LinkedBlockingQueue<Message>> queusBySubscriber= new ConcurrentHashMap();

	private static class SingleHolder {
		private static MessageBroker instance = new MessageBrokerImpl ();
	}
	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		return MessageBrokerImpl.SingleHolder.instance;
	}

	@Override
	public  <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		subscribersByEventsType.putIfAbsent(type, new ConcurrentLinkedQueue<>());
		subscribersByEventsType.get(type).add(m);

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		subscribersByBroadcast.putIfAbsent(type,new ConcurrentLinkedQueue<>());
		subscribersByBroadcast.get(type).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
			eventsMap.get(e).resolve(result);
			eventsMap.remove(e);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if (subscribersByBroadcast.get(b.getClass())!=null && !subscribersByBroadcast.get(b.getClass()).isEmpty()){
			for (Subscriber s : subscribersByBroadcast.get(b.getClass()) ){
				queusBySubscriber.get(s).add(b);
			}

		}
	}


	@Override
	public <T> Future<T> sendEvent(Event<T> e) {


		if (subscribersByEventsType.get(e.getClass()) != null && !subscribersByEventsType.get(e.getClass()).isEmpty()) {

			synchronized (subscribersByEventsType.get(e.getClass())) {
				synchronized (subscribersByEventsType.get(e.getClass()).peek()) {
					Future<T> newF = new Future<>();
					eventsMap.put(e, newF);

					Subscriber current = subscribersByEventsType.get(e.getClass()).poll();
					if (!(queusBySubscriber.get(current)==null)) {
						synchronized (queusBySubscriber.get(current)) {
							queusBySubscriber.get(current).add(e);
						}
						subscribersByEventsType.get(e.getClass()).add(current);
					}
					return newF;
				}
			}
		}
		else {
			return null;
		}


	}


	@Override
	public void register(Subscriber m) {
		queusBySubscriber.putIfAbsent(m, new LinkedBlockingQueue<Message>());
	}

	@Override
	public void unregister(Subscriber m) {
		if (subscribersByEventsType.containsKey(AgentsAvailableEvent.class)) {
			if (subscribersByEventsType.get(AgentsAvailableEvent.class).contains(m)) {
				subscribersByEventsType.get(AgentsAvailableEvent.class).remove(m);
			}
		}
		if (subscribersByEventsType.containsKey(GadgetAvailableEvent.class)) {
			if (subscribersByEventsType.get(GadgetAvailableEvent.class).contains(m)) {
				subscribersByEventsType.get(GadgetAvailableEvent.class).remove(m);
			}
		}

		if (subscribersByEventsType.containsKey(MissionReceivedEvent.class)) {
			if (subscribersByEventsType.get(MissionReceivedEvent.class).contains(m)) {
				subscribersByEventsType.get(MissionReceivedEvent.class).remove(m);
			}
		}

		if (subscribersByEventsType.containsKey(RealseAgentsEvent.class)) {
			if (subscribersByEventsType.get(RealseAgentsEvent.class).contains(m)) {
				subscribersByEventsType.get(RealseAgentsEvent.class).remove(m);
			}
		}

		if (subscribersByBroadcast.containsKey(TickBroadcast.class)) {
			if (subscribersByBroadcast.get(TickBroadcast.class).contains(m)) {
				subscribersByBroadcast.get(TickBroadcast.class).remove(m);
			}
		}

		if (subscribersByBroadcast.containsKey(TimeOutBroadcast.class)) {
			if (subscribersByBroadcast.get(TimeOutBroadcast.class).contains(m)) {
				subscribersByBroadcast.get(TimeOutBroadcast.class).remove(m);
			}
		}

		synchronized (queusBySubscriber) {
			queusBySubscriber.remove(m);
		}

	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
			return queusBySubscriber.get(m).take();
	}

}