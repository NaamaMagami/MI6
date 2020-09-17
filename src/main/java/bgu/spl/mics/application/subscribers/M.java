package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
    private int id;
    private AtomicInteger Time=new AtomicInteger(0);

    public M(int _id) {
        super("M");
        id=_id;
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class,c ->{
            Time=c.getTime(); });
        subscribeBroadcast(TimeOutBroadcast.class, c -> {terminate();});
        subscribeEvent(MissionReceivedEvent.class, c -> {
            Diary.getInstance().incrementTotal();
            LinkedList newL = new LinkedList(c.getMissionInfo().getSerialAgentsNumbers());
            Report report = new Report();
            report.setTimeCreated(Time.intValue());
            report.setMissionName(c.getMissionInfo().getMissionName());
            report.setM(id);
            report.setAgentsSerialNumbers(newL);
            report.setTimeIssued(c.timeSent().intValue());
            Event checkAgents = new AgentsAvailableEvent(newL, report, c.getMissionInfo().getDuration());
            Future futureAg = getSimplePublisher().sendEvent(checkAgents);

            if (futureAg == null) {
                terminate();
            } else {
                if (futureAg.get().equals("Completed")) {
                    Event checkGadget = new GadgetAvailableEvent(c.getMissionInfo().getGadget(), report);
                    Future futureGa = getSimplePublisher().sendEvent(checkGadget);
                    if (futureGa == null) {
                        terminate();
                    } else {
                        if (futureGa.get().equals("Completed")) {
                            if (Time.intValue() < c.getMissionInfo().getTimeExpired()) {
                                report.setTimeCreated(Time.intValue());
                                complete(c, "Completed");
                                Diary.getInstance().addReport(report);
                            } else {
                                complete(c, "Not completed");
                                getSimplePublisher().sendEvent(new RealseAgentsEvent(newL));

                            }

                        } else {
                            complete(c, "Not completed");
                            getSimplePublisher().sendEvent(new RealseAgentsEvent(newL));
                        }

                    }
                } else {
                    complete(c, "Not completed");
                    getSimplePublisher().sendEvent(new RealseAgentsEvent(newL));

                }


            }
        });


    }

}
