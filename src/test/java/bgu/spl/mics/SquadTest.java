package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SquadTest {
    private Squad squad;

    @BeforeEach
    public void setUp() {
        squad = squad.getInstance();
    }


    @Test
    public void getInstanceTest (){
        Squad squad2 = Squad.getInstance();
        assertEquals(squad,squad2);
    }

    /**
     * should return a positive outcome
     */
    @Test
    public void GetInstanceTest () {
        assertTrue (squad!=null);
    }

    /**
     * should return a positive outcome
     */
    @Test
    public void loadAndGetAgentTest () {
        Agent agent1 = new Agent();
        agent1.setSerialNumber("1234");
        agent1.setName("top secret neta");
        Agent agent2 = new Agent();
        agent2.setSerialNumber("5678");
        agent2.setName("super top secret naama");
        Agent [] agentsArray = {agent1};

        List <String> firstAgentsList = new LinkedList<String>() ;
        firstAgentsList.add("top secret neta");

        assertFalse(squad.getAgents(firstAgentsList));

        squad.load(agentsArray);

        assertTrue(squad.getAgents(firstAgentsList));
        firstAgentsList.add("super top secret naama");

        assertFalse(squad.getAgents(firstAgentsList));
        Agent [] secondAgentsArray = {agent2};
        squad.load(secondAgentsArray);

        assertTrue(squad.getAgents(firstAgentsList));
    }

    /**
     * should return a positive outcome
     */
    @Test
    public void releaseAgentTest(){
        Agent agent1 = new Agent();
        agent1.setSerialNumber("1234");
        agent1.setName("top secret neta");
        Agent agent2 = new Agent();
        agent2.setSerialNumber("5678");
        agent2.setName("super top secret naama");

        Agent [] agentsArray = {agent1,agent2};
        squad.load(agentsArray);

        List <String> firstAgentsList = new LinkedList<String>() ;
        firstAgentsList.add("top secret neta");
        firstAgentsList.add("super top secret naama");

        squad.releaseAgents(firstAgentsList);

        assertFalse(squad.getAgents(firstAgentsList));
    }

    /**
     * should return a positive outcome
     */
    @Test
    public void getAgentsNamesTest(){
        Agent agent1 = new Agent();
        agent1.setSerialNumber("1234");
        agent1.setName("top secret neta");
        Agent agent2 = new Agent();
        agent2.setSerialNumber("5678");
        agent2.setName("super top secret naama");
        Agent [] agentsArray = {agent1,agent2};
        squad.load(agentsArray);

        List <String> serialNumbers = new LinkedList<String>() ;
        serialNumbers.add("1234");
        serialNumbers.add("5678");

        List <String> agents = squad.getAgentsNames(serialNumbers);
        assertTrue(agents.contains("top secret neta"));
        assertTrue(agents.contains("super top secret naama"));
        assertFalse(agents.contains(""));

    }

}
