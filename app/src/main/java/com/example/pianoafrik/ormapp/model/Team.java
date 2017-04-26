package com.example.pianoafrik.ormapp.model;

import java.util.List;

/**
 * Created by pianoafrik on 4/26/17.
 */

public class Team {

    String name;

    List<Object> list;

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Team(String name, List<Object> list) {
        this.name = name;
        this.list = list;
    }

    public Team(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
