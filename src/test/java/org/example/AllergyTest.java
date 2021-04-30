package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class AllergyTest {

    Allergy a = new Allergy(123, "Aspirin", "Death", 55);

    @Test
    public void getId() {
        assertEquals(123, a.id);
    }

    @Test
    public void setId() {
        a.setId(456);
        assertEquals(456, a.id);
    }

    @Test
    public void getName() {
        assertEquals("Aspirin", a.name);
    }

    @Test
    public void setName() {
        a.setName("Tylenol");
        assertEquals("Tylenol", a.name);
    }

    @Test
    public void getReaction() {
        assertEquals("Death", a.reaction);
    }

    @Test
    public void setReaction() {
        a.setReaction("Swelling");
        assertEquals("Swelling", a.reaction);
    }

    @Test
    public void getCamperid() {
        assertEquals(55, a.camperid);
    }

    @Test
    public void setCamperid() {
        a.setCamperid(22);
        assertEquals(22, a.camperid);
    }
}