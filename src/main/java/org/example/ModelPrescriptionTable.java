/*
 * Author: Devin McDermott
 * Adventure Camp Med Tracker
 * April 2021
 */

package org.example;

public class ModelPrescriptionTable {

    String id, medname, dose, admintime, userid;

    public ModelPrescriptionTable(String id, String medname, String dose, String admintime, String userid) {
        this.id = id;
        this.medname = medname;
        this.dose = dose;
        this.admintime = admintime;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedname() {
        return medname;
    }

    public void setMedname(String medname) {
        this.medname = medname;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getAdmintime() {
        return admintime;
    }

    public void setAdmintime(String admintime) {
        this.admintime = admintime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}