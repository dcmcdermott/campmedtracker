package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class GivenMedTest {

    GivenMed g = new GivenMed(123, "Aspirin",345, 15, "mg", "2200", 20);

    @Test
    public void getId() {
        assertEquals(123, g.id);
    }

    @Test
    public void setId() {
        g.setId(456);
        assertEquals(456, g.id);
    }

    @Test
    public void getName() {
        assertEquals("Aspirin", g.name);
    }

    @Test
    public void setName() {
        g.setName("lorazepam");
        assertEquals("lorazepam", g.name);
    }

    @Test
    public void getMedid() {
        assertEquals(345, g.medid);
    }

    @Test
    public void getDose() {
        assertEquals(15, g.dose);
    }

    @Test
    public void setDose() {
        g.setDose(25);
        assertEquals(25, g.dose);
    }

    @Test
    public void getDose_unit() {
        assertEquals("mg", g.dose_unit);
    }

    @Test
    public void setDose_unit() {
        g.setDose_unit("g");
        assertEquals("g", g.dose_unit);
    }

    @Test
    public void getTime_given() {
        assertEquals("2200", g.time_given);
    }

    @Test
    public void setTime_given() {
        g.setTime_given("0600");
        assertEquals("0600", g.time_given);
    }

    @Test
    public void getCamperid() {
        assertEquals(20, g.camperid);
    }

    @Test
    public void setCamperid() {
        g.setCamperid(30);
        assertEquals(30, g.camperid);
    }
}