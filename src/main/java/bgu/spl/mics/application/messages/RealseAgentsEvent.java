package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.LinkedList;

public class RealseAgentsEvent implements Event<String> {
    private LinkedList<String> agentsList;

    public RealseAgentsEvent(LinkedList<String> _agents){
        agentsList=_agents;
    }
    public LinkedList<String> getAgentsList(){
        return agentsList;
    }

}

