package bgu.spl.mics;


import bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = inventory.getInstance();
    }

    @Test
    public void getInstanceTest() {
        Inventory inventory2 = Inventory.getInstance();
        assertEquals(inventory, inventory2);
    }

    /**
     * checks that instance is not null
     * should return a positive outcome
     */
    @Test
    public void GetInstanceTest() {
        assertTrue(inventory != null);
    }

    /**
     * checks that item inserts the Inventory
     * should return a positive outcome
     */
    @Test
    public void loadTest() {
        String gadget1 = "lasso";
        String gadget2 = "super knife";
        String gadget3 = "bow and arrow";
        String gadget4 = "shuriken";
        String[] gadgetsArray = {gadget1, gadget2, gadget3, gadget4};
        inventory.load(gadgetsArray);
        assertTrue(inventory.getItem(gadget1));
        assertTrue(inventory.getItem(gadget2));
        assertTrue(inventory.getItem(gadget3));
        assertTrue(inventory.getItem("shuriken"));
        String gadget5 = "invisible gadget";
        assertFalse(inventory.getItem(gadget5));
        String[] newGadgetArray = {gadget5};
        inventory.load(newGadgetArray);
        assertTrue(inventory.getItem(gadget5));
        assertTrue(inventory.getItem(gadget1));
    }

    /**
     * should return a positive outcome
     */
    @Test
    public void getItemTest() {
        String gadget1 = "lasso";
        String gadget2 = "super knife";
        String[] gadgetsArray = {gadget1, gadget2};
        inventory.load(gadgetsArray);
        assertTrue(inventory.getItem(gadget1));
        assertTrue(inventory.getItem(gadget2));
        assertFalse(inventory.getItem("neta rak"));
        assertFalse(inventory.getItem(""));
    }

    /**
     * CHECK - if we need to do a print test
     */
}