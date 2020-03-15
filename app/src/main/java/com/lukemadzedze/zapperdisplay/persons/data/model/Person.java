package com.lukemadzedze.zapperdisplay.persons.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "persons")
public class Person implements Serializable {
    @PrimaryKey
    private int id;
    private String name;

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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Person)) {
            return false;
        }
        Person that = (Person) other;
        return this.getId() == that.getId() && this.name.equals(that.name);
    }

}
