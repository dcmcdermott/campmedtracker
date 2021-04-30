/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

public class GivenMed {

    int id, medid, dose, camperid;
    String name, dose_unit, time_given;

    public GivenMed(int id, String name, int medid, int dose, String dose_unit, String time_given, int camperid) {
        this.id = id;
        this.medid = medid;
        this.dose = dose;
        this.camperid = camperid;
        this.name = name;
        this.dose_unit = dose_unit;
        this.time_given = time_given;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedid() { return id; }

    public void setMedid(int id) {
        this.id = id;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getCamperid() {
        return camperid;
    }

    public void setCamperid(int camperid) {
        this.camperid = camperid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose_unit() {
        return dose_unit;
    }

    public void setDose_unit(String dose_unit) {
        this.dose_unit = dose_unit;
    }

    public String getTime_given() {
        return time_given;
    }

    public void setTime_given(String time_given) {
        this.time_given = time_given;
    }
}
