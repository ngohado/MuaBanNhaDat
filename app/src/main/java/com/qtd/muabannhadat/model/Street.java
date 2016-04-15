package com.qtd.muabannhadat.model;

/**
 * Created by HaiTran on 4/12/2016.
 */
public class Street {
    private int idStreet;
    private int idDistrict;
    private String streetName;

    public Street(int idStreet,int idDistrict,  String streetName) {
        this.idDistrict = idDistrict;
        this.idStreet = idStreet;
        this.streetName = streetName;
    }

    public int getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(int idDistrict) {
        this.idDistrict = idDistrict;
    }

    public int getIdStreet() {
        return idStreet;
    }

    public void setIdStreet(int idStreet) {
        this.idStreet = idStreet;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return streetName;
    }
}
