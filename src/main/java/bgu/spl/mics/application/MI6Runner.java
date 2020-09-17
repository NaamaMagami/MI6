package bgu.spl.mics.application;

import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.subscribers.Intelligence;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {

    public static <MoneyPenny> void main (String[] args) throws FileNotFoundException, InterruptedException {
        long start = System.currentTimeMillis();
        int numOfThreads=0;
        List <Thread> thredesList = new LinkedList<>();
        Gson gson= new Gson();
        JsonParser pars = new JsonParser();
        JsonObject jsonOb = new JsonObject();
        try {
            jsonOb = pars.parse(new FileReader(args[0])).getAsJsonObject();
        }catch (FileNotFoundException e){
        }

        //Importing the Squad from the json file and loading it into the squad object
        Squad squad = Squad.getInstance();
        JsonArray arrayOfSquad = jsonOb.getAsJsonArray("squad");
        Agent[] squadToArray = gson.fromJson(arrayOfSquad, Agent[].class);
        squad.load(squadToArray);

        //Importing the inventory from the json file and loading it into the inventory object
        Inventory inventory = Inventory.getInstance();
        JsonArray arrayOfInventory = jsonOb.getAsJsonArray("inventory");
        String[] inventoryToStrings = gson.fromJson(arrayOfInventory,String[].class);
        inventory.load(inventoryToStrings);

        JsonObject serv = jsonOb.getAsJsonObject("services");
        Services services = gson.fromJson(serv,Services.class);
        int Mnum = services.getM();
        int MoneyPennyNum = services.getMoneypenny();
        intel[] intelArray = services.getIntels();
        TimeService ts = new TimeService(services.getTime());
        Q q = new Q();

        List<M> Mlist = new LinkedList<>();
        List<Moneypenny> MoneyPennylist = new LinkedList<>();
        List<Intelligence> intelligencelist = new LinkedList<>();

        Diary.getInstance();
        MessageBrokerImpl.getInstance();

        //creating the M's
        for (int i = 0; i<Mnum;i++){
            M tempM = new M(i);
            Mlist.add(tempM);
        }

        //creating the MoneyPenny's
        for (int i = 0; i<MoneyPennyNum;i++){
            Moneypenny tempMoneyPenny=new Moneypenny(i);
            MoneyPennylist.add(tempMoneyPenny);
        }

        //creating the Intelligence's
        for (int i=0 ; i<intelArray.length; i++){
            LinkedList<MissionInfo> missionsInfoList= new LinkedList<>();
            for(MissionInfo missionInfo : intelArray[i].getMissions()){
                missionsInfoList.add(missionInfo);
            }
            Intelligence in = new Intelligence(i,missionsInfoList);
            intelligencelist.add(in);
        }

        for (Intelligence in : intelligencelist){
            Thread t = new Thread(in);
            thredesList.add(t);
            t.start();
        }
        Thread.currentThread().sleep(100);

        for (M m : Mlist){
            Thread t = new Thread(m);
            thredesList.add(t);
            t.start();
        }

        for (Moneypenny mp : MoneyPennylist){
            Thread t = new Thread(mp);
            thredesList.add(t);
            t.start();
        }


        Thread t1 = new Thread(q);
        thredesList.add(t1);
        numOfThreads=thredesList.size();
        t1.start();

        while ((ThreadesCounter.getInstance().getIntThreadesCounter()!=numOfThreads)){} //wait until all threads initialized
//        MessageBrokerImpl.getInstance().printQ();

        Thread t2 = new Thread(ts);
        thredesList.add(t2);


        t2.start();

        try {
            for (Thread t : thredesList)
                t.join();
        }catch (Exception e) {
            e.printStackTrace();
        }

        Inventory.getInstance().printToFile(args[1]);
        Diary.getInstance().printToFile(args[2]);

        long end = System.currentTimeMillis();
    }


}