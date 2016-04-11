package com.qtd.muabannhadat.model;

import java.util.ArrayList;

/**
 * Created by Dell on 4/9/2016.
 */
public class HomeCategory {
    private String name;
    private ArrayList<Home> homes;

    public HomeCategory(String name, ArrayList<Home> homes) {
        this.name = name;
        this.homes = homes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Home> getHomes() {
        return homes;
    }

    public void setHomes(ArrayList<Home> homes) {
        this.homes = homes;
    }
}
