package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.LinkedList;

public class AgentsAvailableEvent implements Event<String> {
    private LinkedList<String> agentsList;
    private Report report;
    private int duration;

    public AgentsAvailableEvent(LinkedList<String> _agents, Report _report,int d){
        agentsList=_agents;
        report=_report;
        duration=d;

    }
    public LinkedList<String> getAgentsList(){
        return agentsList;
    }
    public Report getReport(){
        return report;
    }
    public int getDuration(){
        return duration;
    }

}
