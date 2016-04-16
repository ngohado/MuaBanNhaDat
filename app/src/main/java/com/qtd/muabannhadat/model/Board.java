package com.qtd.muabannhadat.model;

/**
 * Created by Dell on 4/15/2016.
 */
public class Board {
    private int id;
    private String name;
    private int numberOfApartment;
    private String imageFirst;

    public Board(int id, String name, int numberOfApartment, String imageFirst) {
        this.id = id;
        this.name = name;
        this.numberOfApartment = numberOfApartment;
        this.imageFirst = imageFirst;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfApartment() {
        return numberOfApartment;
    }

    public String getName() {
        return name;
    }

    public String getImageFirst() {
        return imageFirst;
    }
}
