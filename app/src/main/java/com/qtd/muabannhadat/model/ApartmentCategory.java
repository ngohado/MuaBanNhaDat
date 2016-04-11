package com.qtd.muabannhadat.model;

import java.util.ArrayList;

/**
 * Created by Dell on 4/9/2016.
 */
public class ApartmentCategory {
    private String name;
    private ArrayList<Apartment> apartments;

    public ApartmentCategory(String name, ArrayList<Apartment> apartments) {
        this.name = name;
        this.apartments = apartments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
    }
}
