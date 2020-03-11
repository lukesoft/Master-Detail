package com.lukemadzedze.zapperdisplay.persons.data.model;

import java.io.Serializable;

public class Team implements Serializable {
    private String person;
    private String team;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
