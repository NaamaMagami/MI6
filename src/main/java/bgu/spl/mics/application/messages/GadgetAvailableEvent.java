package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Report;

public class GadgetAvailableEvent implements Event<String> {
    private String gadget;
    private Report report;


    public GadgetAvailableEvent(String _gadget,Report _report){
        report=_report;
        gadget=_gadget;
    }

    public String getGadget(){
        return gadget;
    }
    public Report getReport(){
        return report;
    }

}
