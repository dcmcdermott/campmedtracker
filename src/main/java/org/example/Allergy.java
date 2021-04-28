package org.example;

public class Allergy {

    String id, name, reaction, camperid;

    public Allergy(String id, String name, String reaction, String camperid) {
        this.id = id;
        this.name = name;
        this.reaction = reaction;
        this.camperid = camperid;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCamperid() {
        return camperid;
    }

    public void setCamperid(String camperid) {
        this.camperid = camperid;
    }
}
