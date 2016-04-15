package com.qtd.muabannhadat.model;

import java.util.ArrayList;

/**
 * Created by Dell on 4/15/2016.
 */
public class Board {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Apartment> apartments;

    public Board(String name, ArrayList<User> users, ArrayList<Apartment> apartments) {
        this.name = name;
        this.users = users;
        this.apartments = apartments;
    }

    public ArrayList<Apartment> getApartments() {
        return apartments;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }
}
