package com.lukemadzedze.zapperdisplay.persons.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "teams")
public class Team implements Serializable {
    @PrimaryKey
    @NonNull
    private int id;
    private String person;
    private String team;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Team)) {
            return false;
        }
        Team that = (Team) other;
        return this.getId() == that.getId() && this.person.equals(that.person);
    }
}
