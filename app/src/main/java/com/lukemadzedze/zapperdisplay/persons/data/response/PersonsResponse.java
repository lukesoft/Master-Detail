package com.lukemadzedze.zapperdisplay.persons.data.response;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;

import java.io.Serializable;
import java.util.List;

public class PersonsResponse implements Serializable {
    private String msg;
    private List<Person> persons;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
