/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

public class Prescription {

    int id, dose, time, camperid;
    String name, dose_unit;

    public Prescription(int id, String name, int dose, String dose_unit, int time, int camperid) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.dose_unit = dose_unit;
        this.time = time;
        this.camperid = camperid;
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

    public void setDose(String dose_unit) {
        this.dose_unit = dose_unit;
    }

    public String getDose_unit() {
        return dose_unit;
    }

    public void setDose_unit(int dose) {
        this.dose = dose;
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
}