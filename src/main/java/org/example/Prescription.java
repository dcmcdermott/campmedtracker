/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

public class Prescription {

    int id, dose, time, camperid;
    String name, dose_unit;
    boolean given_today;

    public Prescription(int id, String name, int dose, String dose_unit, int time, int camperid, boolean given_today) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.dose_unit = dose_unit;
        this.time = time;
        this.camperid = camperid;
        this.given_today = given_today;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getDose_unit() {
        return dose_unit;
    }

    public void setDose_unit(String dose_unit) {
        this.dose_unit = dose_unit;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCamperid() {
        return camperid;
    }

    public void setCamperid(int camperid) {
        this.camperid = camperid;
    }

    public boolean getGiven_today() { return given_today; }

    public void setGiven_today(boolean given_today) { this.given_today = given_today; }
}