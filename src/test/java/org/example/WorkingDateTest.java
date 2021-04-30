package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkingDateTest {

    WorkingDate w = new WorkingDate("2021-04-22");

    @Test
    public void getWorking_date() {
        assertEquals("2021-04-22", w.working_date);
    }

    @Test
    public void setWorking_date() {
        w.setWorking_date("2020-05-21");
        assertEquals("2020-05-21", w.working_date);
    }
}