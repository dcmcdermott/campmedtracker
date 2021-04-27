/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

public class ModelPrescriptionTable {

    String id, name, dose, dose_unit, time, camperid;

    public ModelPrescriptionTable(String id, String name, String dose, String dose_unit, String time, String camperid) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.dose_unit = dose_unit;
        this.time = time;
        this.camperid = camperid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose_unit) {
        this.dose_unit = dose_unit;
    }

    public String getDose_unit() {
        return dose_unit;
    }

    public void setDose_unit(String dose) {
        this.dose = dose;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCamperid() {
        return camperid;
    }

    public void setCamperid(String camperid) {
        this.camperid = camperid;
    }
}