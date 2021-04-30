package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class CamperTest {
    Camper c = new Camper(3, "lname", "fname", "06-10-1987", "Mom", "8132223333", "Hello");

    @Test
    public void getId() {
        assertEquals(3, c.id);
    }

    @Test
    public void setId() {
        c.setId(5);
        assertEquals(5, c.id);
    }

    @Test
    public void getContact() {
        assertEquals("8132223333", c.contact);
    }

    @Test
    public void setContact() {
        c.setContact("1112223333");
        assertEquals("1112223333", c.contact);
    }

    @Test
    public void getLast_name() {
        assertEquals("lname", c.last_name);
    }

    @Test
    public void setLast_name() {
        c.setLast_name("Smith");
        assertEquals("Smith", c.last_name);
    }

    @Test
    public void getFirst_name() {
        assertEquals("fname", c.first_name);
    }

    @Test
    public void setFirst_name() {
        c.setFirst_name("Will");
        assertEquals("Will", c.first_name);
    }

    @Test
    public void getDob() {
        assertEquals("06-10-1987", c.dob);
    }

    @Test
    public void setDob() {
        c.setDob("08-09-2012");
        assertEquals("08-09-2012", c.dob);
    }

    @Test
    public void getGuardian() {
        assertEquals("Mom", c.guardian);
    }

    @Test
    public void setGuardian() {
        c.setGuardian("Dad");
        assertEquals("Dad", c.guardian);
    }

    @Test
    public void getNote() {
        assertEquals("Hello", c.note);
    }

    @Test
    public void setNote() {
        c.setNote("Bye");
        assertEquals("Bye", c.note);
    }
}