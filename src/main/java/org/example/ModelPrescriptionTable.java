package org.example;

public class ModelPrescriptionTable {

    String id, med_name, dose, admin_time, camper_id;

    public ModelPrescriptionTable(String id, String med_name, String dose, String admin_time, String camper_id) {
        this.id = id;
        this.med_name = med_name;
        this.dose = dose;
        this.admin_time = admin_time;
        this.camper_id = camper_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getAdmin_time() {
        return admin_time;
    }

    public void setAdmin_time(String admin_time) {
        this.admin_time = admin_time;
    }

    public String getCamper_id() {
        return camper_id;
    }

    public void setCamper_id(String camper_id) {
        this.camper_id = camper_id;
    }
}