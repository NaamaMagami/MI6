package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.RealseAgentsEvent;
import bgu.spl.mics.application.messages.TimeOutBroadcast;
import bgu.spl.mics.application.passiveObjects.Squad;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private int id;
	private Squad agentsSquad=Squad.getInstance();

	public Moneypenny(int _id) {
		super("MoneyPenny");
		id=_id;

	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TimeOutBroadcast.class, c -> {
			terminate();
			agentsSquad.releaseAgents(null);
		});
		if (id % 2 == 0) {
			subscribeEvent(AgentsAvailableEvent.class, c -> {
				c.getReport().setMoneypenny(id);
				c.getReport().setAgentsNames(agentsSquad.getAgentsNames(c.getAgentsList()));
				if (agentsSquad.getAgents(c.getAgentsList())) {
					complete(c, "Completed");
					agentsSquad.sendAgents(c.getAgentsList(), c.getDuration());
				} else {
					complete(c, "Not completed");

				}
			});

		} else {

			subscribeEvent(RealseAgentsEvent.class, c -> {
				agentsSquad.releaseAgents(c.getAgentsList());
				complete(c, "Completed");
			});


		}
	}

}
