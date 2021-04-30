package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrescriptionTest {

    Prescription p = new Prescription(3, "drugname", 5, "units", 2100, 4, false);

    @Test
    public void getId() {
        assertEquals(3, p.id);
    }

    @Test
    public void setId() {
        p.setId(6);
        assertEquals(6, p.id);
    }

    @Test
    public void getName() {
        assertEquals("drugname", p.name);
    }

    @Test
    public void setName() {
        p.setName("name");
        assertEquals("name", p.name);
    }

    @Test
    public void getDose() {
        assertEquals(5, p.dose);
    }

    @Test
    public void setDose() {
        p.setDose(6);
        assertEquals(6, p.dose);
    }

    @Test
    public void getDose_unit() {
        assertEquals("units", p.dose_unit);
    }

    @Test
    public void setDose_unit() {
        p.setDose_unit("mg");
        assertEquals("mg", p.dose_unit);
    }

    @Test
    public void getTime() {
        assertEquals(2100, p.time);
    }

    @Test
    public void setTime() {
        p.setTime(2200);
        assertEquals(2200, p.time);
    }

    @Test
    public void getCamperid() {
        assertEquals(4, p.camperid);
    }

    @Test
    public void setCamperid() {
        p.setCamperid(7);
        assertEquals(7, p.camperid);
    }

    @Test
    public void getGiven_today() {
        assertEquals(false, p.given_today);
    }

    @Test
    public void setGiven_today() {
        p.setGiven_today(true);
        assertEquals(true, p.given_today);
    }
}