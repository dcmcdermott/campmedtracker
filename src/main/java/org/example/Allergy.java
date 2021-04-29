package org.example;

public class Allergy {

    int id, camperid;
    String name, reaction;

    public Allergy(int id, String name, String reaction, int camperid) {
        this.id = id;
        this.name = name;
        this.reaction = reaction;
        this.camperid = camperid;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public int getCamperid() {
        return camperid;
    }

    public void setCamperid(int camperid) {
        this.camperid = camperid;
    }
}
